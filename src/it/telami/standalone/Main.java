package it.telami.standalone;

import it.telami.commons.concurrency.thread.ContentionHandler;
import it.telami.commons.concurrency.thread.ThreadSecondarySeedHandler;
import it.telami.commons.util.Logging;
import it.telami.license.License;
import it.telami.license.LicenseState;
import it.telami.minecraft.commons.color.Color;

import java.util.Scanner;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.LockSupport;
import java.util.logging.Logger;

final class Main {
    private static final Benchmark[] benchmarks = new Benchmark[] {
            new MathBenchmark(),
            new ColorBenchmark(),
            new CacheBenchmark(),
            new QueueBenchmark(),
            new ThreadPoolBenchmark(),
            new NetworkBufferBenchmark()
    };

    private static final Scanner scanner = new Scanner(System.in);

    private static Logger logger;
    private static boolean isAnsiPresent;
    private static boolean setup () {
        System.out.println("Starting TelLib standalone version...");
        System.out.println("\nThis version is offered for providing the possibility to:");
        System.out.println("- Benchmark");
        System.out.println("- Check feature availability");
        System.out.println("\nSetting up...");
        final Logger logger = Logging.minecraftLogger;
        try {
            logger.info("Testing custom logger...");
        } catch (final Throwable _) {
            logError("Custom logger not available...");
            return false;
        }
        (Main.logger = logger).info("Checking ANSI availability...");
        //Trying to active ANSI in Windows...
        if (Logging.enableANSI())
            logInfo("Successful attempt of trying to enforce ANSI...");
        isAnsiPresent = true;
        logInfo("\r§cIs this text red? [Y/N]: ");
        try {
            isAnsiPresent = scanner.next().equalsIgnoreCase("Y");
        } catch (final IllegalStateException _) {
            logError("§cThe scanner got closed!");
            return true;
        } catch (final Throwable _) {
            logError("§cUnexpected error occurred!");
            return true;
        }
        if (isAnsiPresent)
            logInfo("§bStarting using ANSI...\n");
        else logInfo("§bContinuing without ANSI...\n");
        return false;
    }
    private static void logInfo (String message) {
        message = Color.commonInstance.fullTranslation(message);
        if (logger != null) {
            if (isAnsiPresent)
                message = message + "§r";
            else message = message.replaceAll("§[a-fA-F0-9klmnorx]", "");
            logger.info(message);
        } else if (message.startsWith("\r"))
            System.out.print(message.replaceAll("§[a-fA-F0-9klmnorx]", ""));
        else System.out.println(message.replaceAll("§[a-fA-F0-9klmnorx]", ""));
    }
    private static void logError (String message) {
        message = Color.commonInstance.fullTranslation(message);
        if (logger != null) {
            if (isAnsiPresent)
                message = message + "§r";
            else message = message.replaceAll("§[a-fA-F0-9klmnorx]", "");
            logger.severe(message);
        } else if (message.startsWith("\r"))
            System.out.print(message.replaceAll("§[a-fA-F0-9klmnorx]", ""));
        else System.out.println(message.replaceAll("§[a-fA-F0-9klmnorx]", ""));
    }

    static void main () throws InterruptedException {
        final CountDownLatch cdl = new CountDownLatch(1);
        License.getByName("TelLib")
                .getState()
                .thenAccept(state -> {
                    try {
                        if (state == LicenseState.ACTIVE) {
                            if (setup())
                                return;
                            //noinspection TextBlockMigration
                            logInfo("             <#DB861F>(<#DB861F>§r\n" +
                                    "<#C93214>*   )    ( )\\ )      )<#FAD05C>§r\n" +
                                    "<#C93214>` )  /(  ( )(()/((  ( /(<#FAD05C>§r\n" +
                                    "<#C93214>( )(_))))((_)(_))\\ )\\())<#FAD05C>§r\n" +
                                    "<#C93214>(_(_())/((_)(_))((_|(_)\\<#FAD05C>§r\n" +
                                    "§b|_   _<#FF742F>(_))<#FF742F>§b| | |  (_|<#FAD05C>(_)<#FAD05C>§b_§r\n" +
                                    "§b  | | / -_) | |__| | '_ \\§r\n" +
                                    "§b  |_| \\___|_|____|_|_.__/     <#2FF5E1>(by Telami)<#FFFFFF>§r\n");
                            logInfo("§bEach operation will show a §3progression bar§b.");
                            boolean error;
                            short updateMillis = 1000;
                            do {
                                error = false;
                                logInfo("\r§bPlease enter the number of milliseconds for each update §3[from 1 to 1000]§b: ");
                                try {
                                    if ((updateMillis = scanner.nextShort()) < 1 || updateMillis > 1000) {
                                        logError("§cInput out of the given bound!");
                                        error = true;
                                    }
                                } catch (final NumberFormatException _) {
                                    logError("§cInvalid input format!");
                                    error = true;
                                } catch (final IllegalStateException _) {
                                    logError("§cThe scanner got closed!");
                                    return;
                                } catch (final Throwable _) {
                                    logError("§cUnexpected error occurred!");
                                    return;
                                }
                            } while (error);
                            logInfo("§bThanks for your choice...\n");
                            logInfo("§bWelcome to the §3standalone version\n");
                            for (final long updateNanos = updateMillis * 1_000_000L;;) {
                                logInfo("§bChoose which feature you want to see in action §3(basing on the currently available benchmarks)§b:");
                                logInfo("§30) §cNone, I want to quit!");
                                for (int i = 0; i < benchmarks.length; i++)
                                    logInfo("§3" + (i + 1) + ") §b" + benchmarks[i]);
                                int choice = 0;
                                do {
                                    error = false;
                                    logInfo("\r§bPlease enter your choice §3[from 0 to " + benchmarks.length + "]§b: ");
                                    try {
                                        if ((choice = scanner.nextInt()) < 0 || choice > benchmarks.length) {
                                            logError("§cInput out of the given bound!");
                                            error = true;
                                        }
                                    } catch (final NumberFormatException _) {
                                        logError("§cInvalid input format!");
                                        error = true;
                                    } catch (final IllegalStateException _) {
                                        logError("§cThe scanner got closed!");
                                        return;
                                    } catch (final Throwable _) {
                                        logError("§cUnexpected error occurred!");
                                        return;
                                    }
                                } while (error);
                                if (choice != 0) {
                                    logInfo("§bSetting up the benchmark...\n");
                                    logInfo("§eNOTE: §6Warm-up §ecycles are necessary for letting the JIT §6compile the code§e, these cycles are §6not measured§e!§r");
                                    int warmupCycles = 0;
                                    do {
                                        error = false;
                                        logInfo("\r§bPlease enter the number of warm-up cycles §3[from 0 to 100000]§b: ");
                                        try {
                                            if ((warmupCycles = scanner.nextInt()) < 0 || warmupCycles > 100_000) {
                                                logError("§cInput out of the given bound!");
                                                error = true;
                                            }
                                        } catch (final NumberFormatException _) {
                                            logError("§cInvalid input format!");
                                            error = true;
                                        } catch (final IllegalStateException _) {
                                            logError("§cThe scanner got closed!");
                                            return;
                                        } catch (final Throwable _) {
                                            logError("§cUnexpected error occurred!");
                                            return;
                                        }
                                    } while (error);
                                    int measurementCycles = 0;
                                    do {
                                        error = false;
                                        logInfo("\r§bPlease enter the number of measurement cycles §3[from 0 to 100000000]§b: ");
                                        try {
                                            if ((measurementCycles = scanner.nextInt()) < 0 || measurementCycles > 100_000_000) {
                                                logError("§cInput out of the given bound!");
                                                error = true;
                                            }
                                        } catch (final NumberFormatException _) {
                                            logError("§cInvalid input format!");
                                            error = true;
                                        } catch (final IllegalStateException _) {
                                            logError("§cThe scanner got closed!");
                                            return;
                                        } catch (final Throwable _) {
                                            logError("§cUnexpected error occurred!");
                                            return;
                                        }
                                    } while (error);
                                    logInfo("§bStarting the benchmark...\n");
                                    final int totalCycles;
                                    final Benchmark.StateHolder bsh = benchmarks[choice - 1].run(warmupCycles, measurementCycles);
                                    assert bsh != null;
                                    final double modifier = 1.0D / ((totalCycles = warmupCycles + measurementCycles) * 0.02D);
                                    for (int currentCycle, res; (currentCycle = bsh.counter.getAcquire()) < totalCycles;) {
                                        logInfo(new StringBuilder(53)
                                                .append("\r[<#ff0000>")
                                                .repeat('|', res = (int) (currentCycle * modifier))
                                                .repeat(' ', 50 - res)
                                                .append("<#00ff00>§r]")
                                                .toString());
                                        LockSupport.parkNanos(updateNanos);
                                    }
                                    logInfo("\r[<#ff0000>||||||||||||||||||||||||||||||||||||||||||||||||||<#00ff00>§r]\n\n\r");
                                    logInfo("§bPrinting benchmark result...\n");
                                    ThreadSecondarySeedHandler.spinUntil(
                                            ContentionHandler.SMART,
                                            () -> bsh.result.getAcquire() == null);
                                    //It's right to not use 'logInfo(...)'!
                                    System.out.println(bsh.result.getOpaque());
                                    logInfo("§bBenchmark terminated\n");
                                } else {
                                    logInfo("§bThank you for choosing TelLib!");
                                    return;
                                }
                            }
                        } else logError("§cCannot load the very basic license! (" + state.name() + ')');
                    } finally {
                        cdl.countDown();
                    }
                });
        cdl.await();
        scanner.close();
    }
}
