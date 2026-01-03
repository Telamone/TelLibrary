package it.telami.standalone;

import it.telami.minecraft.commons.color.Color;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

final class ColorBenchmark implements Benchmark {
    ColorBenchmark () {}

    public String toString () {
        return "Color";
    }

    public StateHolder run (int wc, int mc) {
        final int warmupCycles = (wc & 0xfffffffe) + 2;
        final int measurementCycles = (mc & 0xfffffffe) + 2;
        final StateHolder state = new StateHolder();
        new Thread(() -> {
            final StringBuilder bob = new StringBuilder();
            final int totalCycles;
            final String[] hexCases;
            final String[] gradientCases;
            {
                final char[] c = {
                        '0', '1', '2', '3',
                        '4', '5', '6', '7',
                        '8', '9', 'a', 'b',
                        'c', 'd', 'e', 'f'
                };
                hexCases = IntStream
                        .range(0, totalCycles
                                = warmupCycles
                                + measurementCycles)
                        .parallel()
                        .mapToObj(_ -> {
                            final Random r;
                            final StringBuilder sb = new StringBuilder();
                            final int hexN = (r = ThreadLocalRandom
                                    .current())
                                    .nextInt(4) + 3;
                            for (int j = 0; j < hexN; j++) {
                                sb.append("&#");
                                sb.append(c[r.nextInt(16)]);
                                sb.append(c[r.nextInt(16)]);
                                sb.append(c[r.nextInt(16)]);
                                sb.append(c[r.nextInt(16)]);
                                sb.append(c[r.nextInt(16)]);
                                sb.append(c[r.nextInt(16)]);
                                int sn = r.nextInt(24) + 8;
                                for (int k = 0; k < sn; k++)
                                    sb.append(c[r.nextInt(16)]);
                            }
                            return sb.toString();
                        }).toArray(String[]::new);
                gradientCases = IntStream
                        .range(0, totalCycles)
                        .parallel()
                        .mapToObj(_ -> {
                            final Random r;
                            final StringBuilder sb = new StringBuilder();
                            sb.append("<#")
                                    .append(c[(r = ThreadLocalRandom
                                            .current())
                                            .nextInt(16)])
                                    .append(c[r.nextInt(16)])
                                    .append(c[r.nextInt(16)])
                                    .append(c[r.nextInt(16)])
                                    .append(c[r.nextInt(16)])
                                    .append(c[r.nextInt(16)])
                                    .append('>');
                            final int gn = r.nextInt(48) + 16;
                            for (int j = 0; j < gn; j++)
                                sb.append(c[r.nextInt(16)]);
                            return sb.append("<#")
                                    .append(c[r.nextInt(16)])
                                    .append(c[r.nextInt(16)])
                                    .append(c[r.nextInt(16)])
                                    .append(c[r.nextInt(16)])
                                    .append(c[r.nextInt(16)])
                                    .append(c[r.nextInt(16)])
                                    .append('>')
                                    .toString();
                        }).toArray(String[]::new);
            }
            final Color colorAPI = Color.commonInstance;
            //Operation-Discarding-Prevention
            long odp = 0, time;
            int i;
            for (i = 0; i < warmupCycles; state.counter.setRelease((i += 2) >> 1)) {
                odp += colorAPI.hex(hexCases[i]).length();
                odp += colorAPI.hex(hexCases[i + 1]).length();
            }
            time = System.nanoTime();
            for (; i < totalCycles; state.counter.setRelease((i += 2) >> 1)) {
                odp += colorAPI.hex(hexCases[i]).length();
                odp += colorAPI.hex(hexCases[i + 1]).length();
            }
            time = System.nanoTime() - time;
            bob.append("Hex time per cycle:      ")
                    .append(time / (float) measurementCycles)
                    .append(" ns");
            final int partialTotalCycles = totalCycles >> 1;
            for (i = 0; i < warmupCycles; state.counter.setRelease(partialTotalCycles + ((i += 2) >> 1))) {
                odp += colorAPI.gradient(gradientCases[i]).length();
                odp += colorAPI.gradient(gradientCases[i + 1]).length();
            }
            time = System.nanoTime();
            for (; i < totalCycles; state.counter.setRelease(partialTotalCycles + ((i += 2) >> 1))) {
                odp += colorAPI.gradient(gradientCases[i]).length();
                odp += colorAPI.gradient(gradientCases[i + 1]).length();
            }
            time = System.nanoTime() - time;
            state.result.setOpaque(bob
                    .append("\nGradient time per cycle: ")
                    .append(time / (float) measurementCycles)
                    .append(" ns\nODP value: ")
                    .append(odp)
                    .toString());
        }).start();
        return state;
    }
}
