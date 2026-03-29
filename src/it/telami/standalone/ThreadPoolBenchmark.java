package it.telami.standalone;

import it.telami.commons.concurrency.thread.ContentionHandler;
import it.telami.commons.concurrency.thread.QuickTaskPool;
import it.telami.commons.concurrency.thread.ThreadSecondarySeedHandler;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.VarHandle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ForkJoinPool;

final class ThreadPoolBenchmark implements Benchmark {
    ThreadPoolBenchmark () {}

    public String toString () {
        return "ThreadPool";
    }

    public StateHolder run (final int wc,
                            final int mc) {
        final int parallelism = Runtime.getRuntime().availableProcessors() << 2;
        //Divisible both for 'parallelism' and for '4'!
        final int warmupCycles = (wc - (wc % parallelism)) + parallelism;
        final int measurementCycles = (mc - (mc % parallelism)) + parallelism;
        final StateHolder state = new StateHolder();
        new Thread(() -> {
            final StringBuilder bob = new StringBuilder();
            benchmarkThreadPool(
                    "[ForkJoinPool]          ",
                    new ForkJoinPool(parallelism >>> 2),
                    warmupCycles,
                    measurementCycles,
                    state,
                    bob);
            bob.append('\n');
            benchmarkThreadPool(
                    "[TelLib's QuickTaskPool]",
                    new QuickTaskPool(
                            Thread::new,
                            parallelism >>> 2,
                            true),
                    warmupCycles,
                    measurementCycles,
                    state,
                    bob);
            state.result.setOpaque(bob
                    .append("\n* No need of any ODP value for this benchmark *")
                    .toString());
        }).start();
        return state;
    }

    @SuppressWarnings("unused")
    private static int sum;
    @SuppressWarnings("unused")
    private static int submissions;
    private static final VarHandle sumVar;
    private static final VarHandle subVar;
    private static final Runnable sumTask;
    static {
        try {
            final MethodHandles.Lookup lookup = MethodHandles.lookup();
            sumVar = lookup.findStaticVarHandle(
                    ThreadPoolBenchmark.class,
                    "sum",
                    int.class);
            subVar = lookup.findStaticVarHandle(
                    ThreadPoolBenchmark.class,
                    "submissions",
                    int.class);
        } catch (final NoSuchFieldException |
                       IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        sumTask = () -> sumVar.getAndAddRelease(1);
    }
    private static void benchmarkThreadPool (final String prefix,
                                             final ExecutorService threadPool,
                                             final int warmupCycles,
                                             final int measurementCycles,
                                             final StateHolder state,
                                             final StringBuilder bob) {
        try (threadPool) {
            long time, subTime;
            for (int i = 0; i < warmupCycles; ++i) {
                threadPool.execute(sumTask);
                if ((i & 3) == 0)
                    state.counter.getAndIncrement();
            }
            ThreadSecondarySeedHandler.spinUntil(
                    ContentionHandler.SMART,
                    () -> (int) sumVar.getAcquire() != warmupCycles);
            sumVar.setOpaque(0);
            VarHandle.fullFence();
            subTime = time = System.nanoTime();
            for (int i = 0; i < measurementCycles; i++) {
                threadPool.execute(sumTask);
                if ((i & 3) == 0)
                    state.counter.getAndIncrement();
            }
            subTime = System.nanoTime() - subTime;
            ThreadSecondarySeedHandler.spinUntil(
                    ContentionHandler.SMART,
                    () -> (int) sumVar.getAcquire() != measurementCycles);
            time = System.nanoTime() - time;
            sumVar.setOpaque(0);
            VarHandle.fullFence();
            final String threads = String.valueOf(Runtime.getRuntime().availableProcessors());
            bob.append(prefix)
                    .append(" Time per submission (1-thread):   ")
                    .repeat(' ', threads.length())
                    .append(subTime / (float) measurementCycles)
                    .append(" ns\n")
                    .append(prefix)
                    .append(" Time per execution:               ")
                    .repeat(' ', threads.length())
                    .append(time / (float) measurementCycles)
                    .append(" ns\n");
            final int parallelism = Runtime.getRuntime().availableProcessors();
            final int warmupSubTasks = warmupCycles / parallelism;
            for (int i = 0; i < parallelism; ++i)
                threadPool.execute(() -> {
                    for (int j = 0; j < warmupSubTasks; ++j) {
                        threadPool.execute(sumTask);
                        if ((j & 3) == 0)
                            state.counter.getAndIncrement();
                    }
                    subVar.getAndAddRelease(warmupSubTasks);
                });
            ThreadSecondarySeedHandler.spinUntil(
                    ContentionHandler.SMART,
                    () -> (int) subVar.getAcquire() != warmupCycles);
            subVar.setOpaque(0);
            ThreadSecondarySeedHandler.spinUntil(
                    ContentionHandler.SMART,
                    () -> (int) sumVar.getAcquire() != warmupCycles);
            sumVar.setOpaque(0);
            VarHandle.fullFence();
            final int measurementSubTasks = measurementCycles / parallelism;
            subTime = time = System.nanoTime();
            for (int i = 0; i < parallelism; ++i)
                threadPool.execute(() -> {
                    for (int j = 0; j < measurementSubTasks; ++j) {
                        threadPool.execute(sumTask);
                        if ((j & 3) == 0)
                            state.counter.getAndIncrement();
                    }
                    subVar.getAndAddRelease(measurementSubTasks);
                });
            ThreadSecondarySeedHandler.spinUntil(
                    ContentionHandler.SMART,
                    () -> (int) subVar.getAcquire() != measurementCycles);
            subTime = System.nanoTime() - subTime;
            ThreadSecondarySeedHandler.spinUntil(
                    ContentionHandler.SMART,
                    () -> (int) sumVar.getAcquire() != measurementCycles);
            time = System.nanoTime() - time;
            subVar.setOpaque(0);
            sumVar.setOpaque(0);
            VarHandle.fullFence();
            bob.append(prefix)
                    .append(" Time per submission (")
                    .append(threads)
                    .append("-threads):   ")
                    .append(subTime / (float) measurementCycles)
                    .append(" ns\n")
                    .append(prefix)
                    .append(" Time per execution:               ")
                    .repeat(' ', threads.length())
                    .append(time / (float) measurementCycles)
                    .append(" ns\n");
        }
    }
}
