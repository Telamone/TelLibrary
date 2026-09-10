package it.telami.commons.util.log;

/**
 * Class used for handling logged {@link String strings} and {@link Throwable errors},
 * respectively through the methods {@link LogHandler#save(String)} and
 * {@link LogHandler#saveException(Throwable)}. <br>
 * The handling is redirected to the {@link Thread#isDaemon() daemon} {@link Thread}
 * created by this class' instance. <br>
 * An example using {@link it.telami.commons.util.Logging Logging} and {@link LogManager}
 * would be this: <pre> {@code
 * //Create a new LogHandler
 * final LogHandler handler = new LogHandler(new ProcessorImpl());
 * //Make the calling class (for example: MyClass)
 * //be the owner of the just created 'handler'
 * LogManager.registerHandler(handler);
 * //Create a new Logger
 * final Logger logger = Logging.newAlignedLogger(
 *     "Example", //logger name
 *     true, //choose whether to add a clock or not
 *     true, //choose whether to use a minecraft color decoder for ANSI
 *     handler::save); //log handler
 *
 * //Usage example
 * try {
 *     riskyCode();
 *     logger.info("Code executed successfully");
 * } catch (final Throwable t) {
 *     //It's recommended to cache this value and not to get it everytime!
 *     final LogHandler regHandler = LogManager.getHandler(MyClass.class);
 *     assert regHandler == handler;
 *     handler.saveException(t);
 *     logger.severe("Code run into some error! (Check the logs for details)");
 * }
 * } </pre>
 * @param <LP> type of {@link CloseableLogProcessor processor}
 * @author Telami
 * @since 1.0.3
 */
public final class LogHandler<LP extends CloseableLogProcessor> {
    /**
     * Create a new {@link LogHandler} instance.
     * @param processor the given {@link CloseableLogProcessor processor} instance
     * @author Telami
     * @since 1.0.3
     */
    public LogHandler (final LP processor) {
        //Hidden implementation...
    }

    /**
     * Save and {@link CloseableLogProcessor process} the given {@link String}.
     * @param s the given {@link String}
     * @author Telami
     * @since 1.0.3
     */
    public void save (final String s) {
        //Hidden implementation...
    }
    /**
     * Save and {@link CloseableLogProcessor process} the given {@link Throwable}.
     * @param t the given {@link Throwable}
     * @author Telami
     * @since 1.0.3
     */
    public void saveException (final Throwable t) {
        //Hidden implementation...
    }
}
