package it.telami.standalone;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.Scheduler;
import com.google.common.base.Ticker;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import it.telami.commons.concurrency.thread.ContentionHandler;
import it.telami.commons.concurrency.thread.ThreadSecondarySeedHandler;
import it.telami.commons.data_structure.cache.Cache;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.LockSupport;
import java.util.stream.IntStream;
import java.util.stream.Stream;

final class CacheBenchmark implements Benchmark {
    private static final int EL = 60;

    CacheBenchmark () {}

    public String toString () {
        return "Cache";
    }

    public StateHolder run (final int wc, final int mc) {
        final int warmupCycles = (wc - wc % 30) + 30;
        final int measurementCycles = (mc - mc % 30) + 30;
        final StateHolder state = new StateHolder();
        new Thread(() -> {
            final StringBuilder bob = new StringBuilder();
            int totalCycles;
            final String[] cases = Stream
                    .generate(() -> {
                        final Random r;
                        final byte[] b;
                        (r = ThreadLocalRandom
                                .current())
                                .nextBytes(b = new byte[r
                                        .nextInt(25) + 8]);
                        return b;
                    })
                    .parallel()
                    .map(String::new)
                    .limit(totalCycles = warmupCycles + measurementCycles)
                    .toArray(String[]::new);
            int odp = 0;
            final String[] randomInvalidations = Arrays
                    .stream(cases)
                    .parallel()
                    .filter(_ -> ThreadLocalRandom
                            .current()
                            .nextInt(10) > 6)
                    .toArray(String[]::new);
            final int[] randoms = IntStream
                    .generate(() -> ThreadLocalRandom.current().nextInt(10))
                    .parallel()
                    .limit(totalCycles)
                    .toArray();
            try {
                odp = benchmarkGoogle(warmupCycles, measurementCycles, state, bob, totalCycles, cases, odp, randomInvalidations, randoms, 0, TimeUnit.NANOSECONDS);
                odp = benchmarkGoogle(warmupCycles, measurementCycles, state, bob, totalCycles, cases, odp, randomInvalidations, randoms, 50, TimeUnit.MILLISECONDS);
                odp = benchmarkGoogle(warmupCycles, measurementCycles, state, bob, totalCycles, cases, odp, randomInvalidations, randoms, 1, TimeUnit.SECONDS);
                odp = benchmarkGoogle(warmupCycles, measurementCycles, state, bob, totalCycles, cases, odp, randomInvalidations, randoms, 1, TimeUnit.MINUTES);
                odp = benchmarkGoogle(warmupCycles, measurementCycles, state, bob, totalCycles, cases, odp, randomInvalidations, randoms, 1, TimeUnit.HOURS);
            } catch (final NoClassDefFoundError e) {
                bob.append("Cannot benchmark Google's Cache, try adding the missing libraries in TelLib/libs (Missing class: ")
                        .append(e.getMessage())
                        .append(")\n");
                state.counter.setRelease(totalCycles / 3);
            }
            bob.append('\n');
            try {
                odp = benchmarkCaffeine(warmupCycles, measurementCycles, state, bob, totalCycles, cases, odp, randomInvalidations, randoms, 0, TimeUnit.NANOSECONDS);
                odp = benchmarkCaffeine(warmupCycles, measurementCycles, state, bob, totalCycles, cases, odp, randomInvalidations, randoms, 50, TimeUnit.MILLISECONDS);
                odp = benchmarkCaffeine(warmupCycles, measurementCycles, state, bob, totalCycles, cases, odp, randomInvalidations, randoms, 1, TimeUnit.SECONDS);
                odp = benchmarkCaffeine(warmupCycles, measurementCycles, state, bob, totalCycles, cases, odp, randomInvalidations, randoms, 1, TimeUnit.MINUTES);
                odp = benchmarkCaffeine(warmupCycles, measurementCycles, state, bob, totalCycles, cases, odp, randomInvalidations, randoms, 1, TimeUnit.HOURS);
            } catch (final NoClassDefFoundError e) {
                bob.append("Cannot benchmark Caffeine's Cache, try adding the missing libraries in TelLib/libs (Missing class: ")
                        .append(e.getMessage())
                        .append(")\n");
                state.counter.setRelease(totalCycles / 3 * 2);
            }
            bob.append('\n');
            odp = benchmarkTelLib(warmupCycles, measurementCycles, state, bob, totalCycles, cases, odp, randomInvalidations, randoms, 0, TimeUnit.NANOSECONDS);
            odp = benchmarkTelLib(warmupCycles, measurementCycles, state, bob, totalCycles, cases, odp, randomInvalidations, randoms, 50, TimeUnit.MILLISECONDS);
            odp = benchmarkTelLib(warmupCycles, measurementCycles, state, bob, totalCycles, cases, odp, randomInvalidations, randoms, 1, TimeUnit.SECONDS);
            odp = benchmarkTelLib(warmupCycles, measurementCycles, state, bob, totalCycles, cases, odp, randomInvalidations, randoms, 1, TimeUnit.MINUTES);
            odp = benchmarkTelLib(warmupCycles, measurementCycles, state, bob, totalCycles, cases, odp, randomInvalidations, randoms, 1, TimeUnit.HOURS);
            state.result.setOpaque(bob
                    .append("\nODP value: ")
                    .append(odp)
                    .toString());
        }).start();
        return state;
    }

    private static int benchmarkGoogle (final int warmupCycles,
                                        final int measurementCycles,
                                        final StateHolder state,
                                        final StringBuilder bob,
                                        final int totalCycles,
                                        final String[] cases,
                                        int odp,
                                        final String[] randomInvalidations,
                                        final int[] randoms,
                                        final long time,
                                        final TimeUnit unit) {
        final AtomicInteger misses = new AtomicInteger();
        final LoadingCache<String, String> cache = CacheBuilder
                .newBuilder()
                .initialCapacity(measurementCycles)
                .maximumSize(measurementCycles)
                .expireAfterWrite(time, unit)
                .expireAfterAccess(time, unit)
                .concurrencyLevel(Runtime.getRuntime().availableProcessors())
                .ticker(Ticker.systemTicker())
                .build(CacheLoader.from(s -> {
                    misses.getAndIncrement();
                    return s.toUpperCase();
                }));
        //Operation-Discarding-Prevention
        int i;
        for (i = 0; i < warmupCycles; i++) {
            odp += cache.getUnchecked(cases[i]).hashCode();
            cache.invalidate(cases[i]);
            if (i % 30 == 0)
                state.counter.getAndIncrement();
        }
        for (int j = i; j < totalCycles; j++)
            odp += cache.getUnchecked(cases[j]).hashCode();
        for (final String randomInvalidation : randomInvalidations)
            cache.invalidate(randomInvalidation);
        misses.setPlain(0);
        long start = System.nanoTime();
        for (; i < totalCycles; i++) {
            odp += cache.getUnchecked(cases[i]).hashCode();
            if (i % 30 == 0)
                state.counter.getAndIncrement();
        }
        start = System.nanoTime() - start;
        final String threads = String.valueOf(Runtime.getRuntime().availableProcessors());
        StringBuilder subBob = new StringBuilder();
        subBob.append("[Google]   Time per cycle (1-thread):    ")
                .repeat(' ', threads.length())
                .append(start / (float) measurementCycles)
                .append(" ns   ");
        bob.append(subBob)
                .repeat(' ', EL - subBob.length())
                .append("|   Hit ration: ~")
                .append((int) (((measurementCycles - misses.get()) / (float) measurementCycles) * 100))
                .append("%\n");
        Arrays.stream(randomInvalidations)
                .parallel()
                .forEach(cache::invalidate);
        for (i = 0; i < warmupCycles; i++) {
            final int finalI = i;
            ForkJoinPool.commonPool().execute(() -> {
                if (randoms[finalI] < 8)
                    ThreadSecondarySeedHandler.setSecondarySeed(cache.getUnchecked(cases[finalI]).hashCode());
                else cache.invalidate(cases[finalI]);
                if (finalI % 30 == 0)
                    state.counter.getAndIncrement();
            });
        }
        //noinspection ResultOfMethodCallIgnored
        ForkJoinPool.commonPool().awaitQuiescence(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        misses.setPlain(0);
        start = System.nanoTime();
        for (; i < totalCycles; i++) {
            final int finalI = i;
            ForkJoinPool.commonPool().execute(() -> {
                if (randoms[finalI] < 8)
                    ThreadSecondarySeedHandler.setSecondarySeed(cache.getUnchecked(cases[finalI]).hashCode());
                else cache.invalidate(cases[finalI]);
                if (finalI % 30 == 0)
                    state.counter.getAndIncrement();
            });
        }
        //noinspection ResultOfMethodCallIgnored
        ForkJoinPool.commonPool().awaitQuiescence(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        start = System.nanoTime() - start;
        subBob = new StringBuilder();
        subBob.append("[Google]   Time per cycle (")
                .append(threads)
                .append("-threads):    ")
                .append(start / (float) measurementCycles)
                .append(" ns   ");
        bob.append(subBob)
                .repeat(' ', EL - subBob.length())
                .append("|   Hit ration: ~")
                .append((int) (((measurementCycles - misses.get()) / (float) measurementCycles) * 100))
                .append("%\n");
        cache.invalidateAll();
        System.gc();
        //Wait 100 milliseconds
        LockSupport.parkNanos(100_000_000L);
        return odp;
    }
    private static int benchmarkCaffeine (final int warmupCycles,
                                          final int measurementCycles,
                                          final StateHolder state,
                                          final StringBuilder bob,
                                          final int totalCycles,
                                          final String[] cases,
                                          int odp,
                                          final String[] randomInvalidations,
                                          final int[] randoms,
                                          final long time,
                                          final TimeUnit unit) {
        final AtomicInteger misses = new AtomicInteger();
        final com.github.benmanes.caffeine.cache.LoadingCache<String, String> cache = Caffeine
                .newBuilder()
                .initialCapacity(measurementCycles)
                .maximumSize(measurementCycles)
                .expireAfterWrite(time, unit)
                .expireAfterAccess(time, unit)
                .scheduler(Scheduler.systemScheduler())
                .ticker(com.github.benmanes.caffeine.cache.Ticker.systemTicker())
                .build(s -> {
                    misses.getAndIncrement();
                    return s.toUpperCase();
                });
        //Operation-Discarding-Prevention
        int i;
        for (i = 0; i < warmupCycles; i++) {
            odp += cache.get(cases[i]).hashCode();
            cache.invalidate(cases[i]);
            if (i % 30 == 0)
                state.counter.getAndIncrement();
        }
        for (int j = i; j < totalCycles; j++)
            odp += cache.get(cases[j]).hashCode();
        for (final String randomInvalidation : randomInvalidations)
            cache.invalidate(randomInvalidation);
        misses.setPlain(0);
        long start = System.nanoTime();
        for (; i < totalCycles; i++) {
            odp += cache.get(cases[i]).hashCode();
            if (i % 30 == 0)
                state.counter.getAndIncrement();
        }
        start = System.nanoTime() - start;
        final String threads = String.valueOf(Runtime.getRuntime().availableProcessors());
        StringBuilder subBob = new StringBuilder();
        subBob.append("[Caffeine] Time per cycle (1-thread):    ")
                .repeat(' ', threads.length())
                .append(start / (float) measurementCycles)
                .append(" ns   ");
        bob.append(subBob)
                .repeat(' ', EL - subBob.length())
                .append("|   Hit ration: ~")
                .append((int) (((measurementCycles - misses.get()) / (float) measurementCycles) * 100))
                .append("%\n");
        Arrays.stream(randomInvalidations)
                .parallel()
                .forEach(cache::invalidate);
        for (i = 0; i < warmupCycles; i++) {
            final int finalI = i;
            ForkJoinPool.commonPool().execute(() -> {
                if (randoms[finalI] < 8)
                    ThreadSecondarySeedHandler.setSecondarySeed(cache.get(cases[finalI]).hashCode());
                else cache.invalidate(cases[finalI]);
                if (finalI % 30 == 0)
                    state.counter.getAndIncrement();
            });
        }
        //noinspection ResultOfMethodCallIgnored
        ForkJoinPool.commonPool().awaitQuiescence(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        misses.setPlain(0);
        start = System.nanoTime();
        for (; i < totalCycles; i++) {
            final int finalI = i;
            ForkJoinPool.commonPool().execute(() -> {
                if (randoms[finalI] < 8)
                    ThreadSecondarySeedHandler.setSecondarySeed(cache.get(cases[finalI]).hashCode());
                else cache.invalidate(cases[finalI]);
                if (finalI % 30 == 0)
                    state.counter.getAndIncrement();
            });
        }
        //noinspection ResultOfMethodCallIgnored
        ForkJoinPool.commonPool().awaitQuiescence(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        start = System.nanoTime() - start;
        subBob = new StringBuilder();
        subBob.append("[Caffeine] Time per cycle (")
                .append(threads)
                .append("-threads):    ")
                .append(start / (float) measurementCycles)
                .append(" ns   ");
        bob.append(subBob)
                .repeat(' ', EL - subBob.length())
                .append("|   Hit ration: ~")
                .append((int) (((measurementCycles - misses.get()) / (float) measurementCycles) * 100))
                .append("%\n");
        cache.invalidateAll();
        System.gc();
        //Wait 100 milliseconds
        LockSupport.parkNanos(100_000_000L);
        return odp;
    }
    private static int benchmarkTelLib (final int warmupCycles,
                                        final int measurementCycles,
                                        final StateHolder state,
                                        final StringBuilder bob,
                                        final int totalCycles,
                                        final String[] cases,
                                        int odp,
                                        final String[] randomInvalidations,
                                        final int[] randoms,
                                        final long time,
                                        final TimeUnit unit) {
        final AtomicInteger misses = new AtomicInteger();
        try (final Cache<String, String, ?> cache = new Cache<>(
                measurementCycles,
                1f,
                Runtime.getRuntime().availableProcessors(),
                ContentionHandler.SMART,
                s -> {
                    misses.getAndIncrement();
                    return s.toUpperCase();
                },
                null,
                null,
                null)) {
            //Operation-Discarding-Prevention
            int i;
            for (i = 0; i < warmupCycles; i++) {
                odp += cache.load(cases[i], time, unit).hashCode();
                cache.invalidate(cases[i]);
                if (i % 30 == 0)
                    state.counter.getAndIncrement();
            }
            for (int j = i; j < totalCycles; j++)
                odp += cache.load(cases[j]).hashCode();
            for (final String randomInvalidation : randomInvalidations)
                cache.invalidate(randomInvalidation);
            misses.setPlain(0);
            long start = System.nanoTime();
            for (; i < totalCycles; i++) {
                odp += cache.load(cases[i], time, unit).hashCode();
                if (i % 30 == 0)
                    state.counter.getAndIncrement();
            }
            start = System.nanoTime() - start;
            final String threads = String.valueOf(Runtime.getRuntime().availableProcessors());
            StringBuilder subBob = new StringBuilder();
            subBob.append("[TelLib]   Time per cycle (1-thread):    ")
                    .repeat(' ', threads.length())
                    .append(start / (float) measurementCycles)
                    .append(" ns   ");
            bob.append(subBob)
                    .repeat(' ', EL - subBob.length())
                    .append("|   Hit ration: ~")
                    .append((int) (((measurementCycles - misses.get()) / (float) measurementCycles) * 100))
                    .append("%\n");
            Arrays.stream(randomInvalidations)
                    .parallel()
                    .forEach(cache::invalidate);
            for (i = 0; i < warmupCycles; i++) {
                final int finalI = i;
                ForkJoinPool.commonPool().execute(() -> {
                    if (randoms[finalI] < 8)
                        ThreadSecondarySeedHandler.setSecondarySeed(cache.load(cases[finalI], time, unit).hashCode());
                    else cache.invalidate(cases[finalI]);
                    if (finalI % 30 == 0)
                        state.counter.getAndIncrement();
                });
            }
            //noinspection ResultOfMethodCallIgnored
            ForkJoinPool.commonPool().awaitQuiescence(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
            misses.setPlain(0);
            start = System.nanoTime();
            for (; i < totalCycles; i++) {
                final int finalI = i;
                ForkJoinPool.commonPool().execute(() -> {
                    if (randoms[finalI] < 8)
                        ThreadSecondarySeedHandler.setSecondarySeed(cache.load(cases[finalI], time, unit).hashCode());
                    else cache.invalidate(cases[finalI]);
                    if (finalI % 30 == 0)
                        state.counter.getAndIncrement();
                });
            }
            //noinspection ResultOfMethodCallIgnored
            ForkJoinPool.commonPool().awaitQuiescence(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
            start = System.nanoTime() - start;
            subBob = new StringBuilder();
            subBob.append("[TelLib]   Time per cycle (")
                    .append(threads)
                    .append("-threads):    ")
                    .append(start / (float) measurementCycles)
                    .append(" ns   ");
            bob.append(subBob)
                    .repeat(' ', EL - subBob.length())
                    .append("|   Hit ration: ~")
                    .append((int) (((measurementCycles - misses.get()) / (float) measurementCycles) * 100))
                    .append("%\n");
        }
        System.gc();
        //Wait 100 milliseconds
        LockSupport.parkNanos(100_000_000L);
        return odp;
    }
}
