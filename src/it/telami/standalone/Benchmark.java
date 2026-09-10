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

    static String extractMessage (final Throwable ex) {
        final String m;
        if (ex instanceof NoClassDefFoundError) {
            final String t;
            int l;
            m = (t = ex
                    .getCause()
                    .getMessage())
                    .substring((l = t.indexOf(':')) < 0
                                    ? 0
                                    : l + 2,
                            (l = t.lastIndexOf('[')) < 0
                                    ? t.length()
                                    : l - 1);
        } else if (ex instanceof final ExceptionInInitializerError eie)
            m = eie.getException().getMessage();
        else m = ex.getMessage();
        return m;
    }
}
