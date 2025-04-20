package it.telami.license;

/**
 * Enum representing a {@link License license}'s state.
 * @see LicenseState#UNINITIALIZED
 * @see LicenseState#EMPTY
 * @see LicenseState#FRAGMENTED
 * @see LicenseState#TIMED_OUT
 * @see LicenseState#ACTIVE
 * @author Telami
 * @since 1.0.0
 */
@SuppressWarnings("JavadocDeclaration")
public enum LicenseState {
    /**
     * {@link LicenseState State} used for indicating
     * that the license has not been communicated
     * by the server.
     * @author Telami
     * @since 1.0.0
     */
    UNINITIALIZED,
    /**
     * {@link LicenseState State} used for indicating
     * that the license has been communicated, but
     * didn't receive enough data to be defined.
     * @author Telami
     * @since 1.0.0
     */
    EMPTY,
    /**
     * {@link LicenseState State} used for indicating
     * that the license has been defined correctly,
     * but its information have been received only
     * partially.
     * @author Telami
     * @since 1.0.0
     */
    FRAGMENTED,
    /**
     * {@link LicenseState State} used for indicating
     * that the complete initialization of the license
     * took too much time. <br>
     * The time could be defined through the dedicated
     * startup flag like this: <br>
     * {@code -DLicenseMillisTimeOut=10000} <br>
     * The default value is indeed 10 seconds.
     * @author Telami
     * @since 1.0.0
     */
    TIMED_OUT,
    /**
     * {@link LicenseState State} used for indicating
     * that the license has been initialized correctly.
     * @author Telami
     * @since 1.0.0
     */
    ACTIVE
}
