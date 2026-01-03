package it.telami.standalone;

import it.telami.license.License;
import it.telami.license.LicenseState;

import java.util.Scanner;
import java.util.concurrent.locks.LockSupport;

final class Main {
    private static final Benchmark[] benchmarks = new Benchmark[] {
            new MathBenchmark(),
            new ColorBenchmark(),
            new CacheBenchmark(),
            new QueueBenchmark()
    };

    static void main () {
        if (License.getByName("TelLib").getState().join() == LicenseState.ACTIVE) {
            System.out.println("Starting TelLib standalone version...");
            System.out.println("\nThis version is offered for providing the possibility to:");
            System.out.println("- Benchmark");
            System.out.println("- Check feature availability");
            System.out.println("\nSetting up...");
            System.out.println("\nEach operation will show a progression bar.");
            final Scanner scanner = new Scanner(System.in);
            boolean error;
            short updateMillis = 1000;
            do {
                error = false;
                System.out.print("Please enter the number of milliseconds for each update [from 1 to 1000]: ");
                try {
                    if ((updateMillis = scanner.nextShort()) < 1 || updateMillis > 1000) {
                        System.err.println("Input out of the given bound!");
                        error = true;
                    }
                } catch (final NumberFormatException _) {
                    System.err.println("Invalid input format!");
                    error = true;
                } catch (final IllegalStateException _) {
                    System.err.println("The scanner got closed!");
                    return;
                } catch (final Throwable _) {
                    System.err.println("Unexpected error occurred!");
                    return;
                }
            } while (error);
            System.out.println("\nWelcome to the standalone version");
            for (final long updateNanos = updateMillis * 1_000_000L;;) {
                System.out.println("\nChoose which feature you want to see in action (basing on the currently available benchmarks):");
                System.out.println("0) None, I want to quit!");
                for (int i = 0; i < benchmarks.length; i++)
                    System.out.println((i + 1) + ") " + benchmarks[i]);
                int choice = 0;
                do {
                    error = false;
                    System.out.print("Please enter your choice [from 0 to " + benchmarks.length + "]: ");
                    try {
                        if ((choice = scanner.nextInt()) < 0 || choice > benchmarks.length) {
                            System.err.println("Input out of the given bound!");
                            error = true;
                        }
                    } catch (final NumberFormatException _) {
                        System.err.println("Invalid input format!");
                        error = true;
                    } catch (final IllegalStateException _) {
                        System.err.println("The scanner got closed!");
                        return;
                    } catch (final Throwable _) {
                        System.err.println("Unexpected error occurred!");
                        return;
                    }
                } while (error);
                if (choice != 0) {
                    System.out.println("\nSetting up the benchmark...");
                    System.out.println("NOTE: Warm-up cycles are necessary for letting the JIT compile the code, these cycles are not measured");
                    int warmupCycles = 0;
                    do {
                        error = false;
                        System.out.print("Please enter the number of warm-up cycles [from 0 to 100000]: ");
                        try {
                            if ((warmupCycles = scanner.nextInt()) < 0 || warmupCycles > 100_000) {
                                System.err.println("Input out of the given bound!");
                                error = true;
                            }
                        } catch (final NumberFormatException _) {
                            System.err.println("Invalid input format!");
                            error = true;
                        } catch (final IllegalStateException _) {
                            System.err.println("The scanner got closed!");
                            return;
                        } catch (final Throwable _) {
                            System.err.println("Unexpected error occurred!");
                            return;
                        }
                    } while (error);
                    int measurementCycles = 0;
                    do {
                        error = false;
                        System.out.print("Please enter the number of measurement cycles [from 0 to 100000000]: ");
                        try {
                            if ((measurementCycles = scanner.nextInt()) < 0 || measurementCycles > 100_000_000) {
                                System.err.println("Input out of the given bound!");
                                error = true;
                            }
                        } catch (final NumberFormatException _) {
                            System.err.println("Invalid input format!");
                            error = true;
                        } catch (final IllegalStateException _) {
                            System.err.println("The scanner got closed!");
                            return;
                        } catch (final Throwable _) {
                            System.err.println("Unexpected error occurred!");
                            return;
                        }
                    } while (error);
                    System.out.println("\nStarting the benchmark...\n");
                    final int totalCycles;
                    final Benchmark.StateHolder bsh = benchmarks[choice - 1].run(warmupCycles, measurementCycles);
                    final double modifier = 1.0D / ((totalCycles = warmupCycles + measurementCycles) * 0.02D);
                    for (int currentCycle, res; (currentCycle = bsh.counter.getAcquire()) < totalCycles;) {
                        System.out.print(new StringBuilder(53)
                                .append("\r[")
                                .repeat('|', res = (int) (currentCycle * modifier))
                                .repeat(' ', 50 - res)
                                .append(']'));
                        LockSupport.parkNanos(updateNanos);
                    }
                    System.out.println("\r[||||||||||||||||||||||||||||||||||||||||||||||||||]");
                    System.out.println("\nPrinting benchmark result...\n");
                    String result;
                    while ((result = bsh.result.getAcquire()) == null)
                        LockSupport.parkNanos(1L);
                    System.out.println(result);
                    System.out.println("\nBenchmark terminated\n");
                } else {
                    System.out.println("\nThank you for choosing TelLib!");
                    return;
                }
            }
        } else System.err.println("Cannot load the very basic license!");
    }
}
