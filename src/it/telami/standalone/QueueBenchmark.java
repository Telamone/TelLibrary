package it.telami.standalone;

import it.telami.commons.concurrency.thread.ThreadSecondarySeedHandler;
import it.telami.commons.data_structure.queue.ConcurrentQueue;

import java.util.Queue;
import java.util.concurrent.*;
import java.util.stream.IntStream;

final class QueueBenchmark implements Benchmark {

    QueueBenchmark () {}

    public String toString () {
        return "Queue";
    }

    public StateHolder run (final int wc,
                            final int mc) {
        final int warmupCycles = (wc - wc % 18) + 18;
        final int measurementCycles = (mc - mc % 18) + 18;
        final StateHolder state = new StateHolder();
        new Thread(() -> {
            final StringBuilder bob = new StringBuilder();
            final int totalCycles;
            final long[] cases = IntStream
                    .range(0, totalCycles
                            = warmupCycles
                            + measurementCycles)
                    .mapToLong(_ -> ThreadLocalRandom
                            .current()
                            .nextLong())
                    .parallel()
                    .toArray();
            int odp = 0;
            final int[] randoms = IntStream
                    .generate(() -> ThreadLocalRandom.current().nextInt(10))
                    .parallel()
                    .limit(totalCycles)
                    .toArray();
            odp = benchmarkQueue(
                    "[ArrayBlockingQueue]      ",
                    new ArrayBlockingQueue<>(totalCycles),
                    warmupCycles,
                    measurementCycles,
                    state,
                    bob,
                    totalCycles,
                    cases,
                    odp,
                    randoms);
            bob.append('\n');
            odp = benchmarkQueue(
                    "[ConcurrentLinkedQueue]   ",
                    new ConcurrentLinkedQueue<>(),
                    warmupCycles,
                    measurementCycles,
                    state,
                    bob,
                    totalCycles,
                    cases,
                    odp,
                    randoms);
            bob.append('\n');
            odp = benchmarkQueue(
                    "[TelLib's ConcurrentQueue]",
                    new ConcurrentQueue<>(),
                    warmupCycles,
                    measurementCycles,
                    state,
                    bob,
                    totalCycles,
                    cases,
                    odp,
                    randoms);
            state.result.setOpaque(bob
                    .append("\nODP value: ")
                    .append(odp)
                    .toString());
        }).start();
        return state;
    }

    private static int benchmarkQueue (final String prefix,
                                       final Queue<Long> queue,
                                       final int warmupCycles,
                                       final int measurementCycles,
                                       final StateHolder state,
                                       final StringBuilder bob,
                                       final int totalCycles,
                                       final long[] cases,
                                       int odp,
                                       final int[] randoms) {
        int i;
        for (i = 0; i < warmupCycles; ++i) {
            queue.add(cases[i]);
            if (i % 18 == 0)
                state.counter.getAndIncrement();
        }
        long start = System.nanoTime();
        for (; i < totalCycles; ++i) {
            queue.add(cases[i]);
            if (i % 18 == 0)
                state.counter.getAndIncrement();
        }
        start = System.nanoTime() - start;
        final String threads = String.valueOf(Runtime.getRuntime().availableProcessors());
        bob.append(prefix)
                .append(" Time per cycle (1-thread)   [add(...)]:     ")
                .repeat(' ', threads.length())
                .append(start / (float) measurementCycles)
                .append(" ns\n");
        for (i = 0; i < warmupCycles; ++i) {
            odp += queue.remove();
            if (i % 18 == 0)
                state.counter.getAndIncrement();
        }
        start = System.nanoTime();
        for (; i < totalCycles; ++i) {
            odp += queue.remove();
            if (i % 18 == 0)
                state.counter.getAndIncrement();
        }
        start = System.nanoTime() - start;
        bob.append(prefix)
                .append(" Time per cycle (1-thread)   [remove(...)]:  ")
                .repeat(' ', threads.length())
                .append(start / (float) measurementCycles)
                .append(" ns\n");
        for (i = 0; i < warmupCycles; ++i) {
            final int finalI = i;
            ForkJoinPool.commonPool().execute(() -> {
                queue.add(cases[finalI]);
                if (finalI % 18 == 0)
                    state.counter.getAndIncrement();
            });
        }
        //noinspection ResultOfMethodCallIgnored
        ForkJoinPool.commonPool().awaitQuiescence(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        start = System.nanoTime();
        for (; i < totalCycles; ++i) {
            final int finalI = i;
            ForkJoinPool.commonPool().execute(() -> {
                queue.add(cases[finalI]);
                if (finalI % 18 == 0)
                    state.counter.getAndIncrement();
            });
        }
        //noinspection ResultOfMethodCallIgnored
        ForkJoinPool.commonPool().awaitQuiescence(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        start = System.nanoTime() - start;
        bob.append(prefix)
                .append(" Time per cycle (")
                .append(threads)
                .append("-threads) [add(...)]:       ")
                .append(start / (float) measurementCycles)
                .append(" ns\n");
        for (i = 0; i < warmupCycles; ++i) {
            final int finalI = i;
            ForkJoinPool.commonPool().execute(() -> {
                ThreadSecondarySeedHandler.setSecondarySeed(queue.remove().intValue());
                if (finalI % 18 == 0)
                    state.counter.getAndIncrement();
            });
        }
        //noinspection ResultOfMethodCallIgnored
        ForkJoinPool.commonPool().awaitQuiescence(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        start = System.nanoTime();
        for (; i < totalCycles; ++i) {
            final int finalI = i;
            ForkJoinPool.commonPool().execute(() -> {
                ThreadSecondarySeedHandler.setSecondarySeed(queue.remove().intValue());
                if (finalI % 18 == 0)
                    state.counter.getAndIncrement();
            });
        }
        //noinspection ResultOfMethodCallIgnored
        ForkJoinPool.commonPool().awaitQuiescence(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        start = System.nanoTime() - start;
        bob.append(prefix)
                .append(" Time per cycle (")
                .append(threads)
                .append("-threads) [remove(...)]:    ")
                .append(start / (float) measurementCycles)
                .append(" ns\n");
        for (i = 0; i < warmupCycles; ++i) {
            if (randoms[i] < 8)
                queue.add(cases[i]);
            else {
                final Long v;
                odp += (v = queue.poll()) != null ? v.intValue() : 0;
            }
            if (i % 18 == 0)
                state.counter.getAndIncrement();
        }
        start = System.nanoTime();
        for (; i < totalCycles; ++i) {
            if (randoms[i] < 8)
                queue.add(cases[i]);
            else {
                final Long v;
                odp += (v = queue.poll()) != null ? v.intValue() : 0;
            }
            if (i % 18 == 0)
                state.counter.getAndIncrement();
        }
        start = System.nanoTime() - start;
        bob.append(prefix)
                .append(" Time per cycle (1-thread)   [Mixed]:        ")
                .repeat(' ', threads.length())
                .append(start / (float) measurementCycles)
                .append(" ns\n");
        queue.clear();
        for (i = 0; i < warmupCycles; ++i) {
            final int finalI = i;
            ForkJoinPool.commonPool().execute(() -> {
                if (randoms[finalI] < 8)
                    queue.add(cases[finalI]);
                else {
                    final Long v;
                    ThreadSecondarySeedHandler.setSecondarySeed((v = queue.poll()) != null ? v.intValue() : 0);
                }
                if (finalI % 18 == 0)
                    state.counter.getAndIncrement();
            });
        }
        //noinspection ResultOfMethodCallIgnored
        ForkJoinPool.commonPool().awaitQuiescence(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        start = System.nanoTime();
        for (; i < totalCycles; ++i) {
            final int finalI = i;
            ForkJoinPool.commonPool().execute(() -> {
                if (randoms[finalI] < 8)
                    queue.add(cases[finalI]);
                else {
                    final Long v;
                    ThreadSecondarySeedHandler.setSecondarySeed((v = queue.poll()) != null ? v.intValue() : 0);
                }
                if (finalI % 18 == 0)
                    state.counter.getAndIncrement();
            });
        }
        //noinspection ResultOfMethodCallIgnored
        ForkJoinPool.commonPool().awaitQuiescence(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        start = System.nanoTime() - start;
        bob.append(prefix)
                .append(" Time per cycle (")
                .append(threads)
                .append("-threads) [Mixed]:          ")
                .append(start / (float) measurementCycles)
                .append(" ns\n");
        return odp;
    }
}
