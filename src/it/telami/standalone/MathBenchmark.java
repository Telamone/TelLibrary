package it.telami.standalone;

import it.telami.commons.math.UFMath;

import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

final class MathBenchmark implements Benchmark {
    MathBenchmark () {}

    public String toString () {
        return "Math";
    }

    public StateHolder run (final int warmupCycles,
                            final int measurementCycles) {
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
            //Operation-Discarding-Prevention
            long odp = 0, time;
            int i;
            for (i = 0; i < warmupCycles; state.counter.setRelease(++i))
                odp += UFMath.sum(cases[i]);
            time = System.nanoTime();
            for (; i < totalCycles; state.counter.setRelease(++i))
                odp += UFMath.sum(cases[i]);
            time = System.nanoTime() - time;
            state.result.setOpaque(bob
                    .append("Time per cycle: ")
                    .append(time / (float) measurementCycles)
                    .append(" ns")
                    .append("\nODP value: ")
                    .append(odp)
                    .toString());
        }).start();
        return state;
    }
}
