package it.telami.standalone;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

interface Benchmark {
    StateHolder run (final int warmupCycles, final int measurementCycles);

    final class StateHolder {
        public final AtomicInteger counter = new AtomicInteger();
        public final AtomicReference<String> result = new AtomicReference<>();

        StateHolder () {}
    }
}
