package it.telami.commons.concurrency.thread;

/**
 * Class used for sharing constants regarding the {@link Thread threads}.
 * @author Telami
 * @since 1.0.0
 */
public final class ThreadConstants {
    //Use stable values in future!
    public static final Thread DEAD_VIRTUAL_THREAD = Thread.ofVirtual().start(() -> {});

    private ThreadConstants () {}
}
