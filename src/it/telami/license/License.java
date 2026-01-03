package it.telami.license;

import it.telami.annotations.LibraryOnly;

import java.time.Instant;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

/**
 * Class used for communicating with this Library's unique
 * License System, exclusive only to properly selected and
 * verified products. <br>
 * Every license has its own {@link String name} that is
 * used for interacting with it. <br>
 * The main goal of this is to synchronize the product tasks
 * with its license initialization.
 * @author Telami
 * @since 1.0.0
 */
public abstract class License {
    @LibraryOnly
    protected License (final String name) {
        //Hidden implementation...
    }

    /**
     * Return the given name's corresponding {@link License license}.
     * @param name the given name
     * @return the corresponding license
     * @author Telami
     * @since 1.0.0
     */
    public static License getByName (final String name) {
        //Hidden implementation...
        return null;
    }

    /**
     * Return all the currently existing {@link License licenses}. <br>
     * This means that the licenses that have not
     * been generated yet will not be present in
     * the returned {@link List list}. <br>
     * The list will be thread safe and modifiable.
     * @return a list containing all the currently existing licenses
     * @author Telami
     * @since 1.0.0
     */
    public static List<License> getAll () {
        //Hidden implementation...
        return null;
    }

    @LibraryOnly
    protected static synchronized boolean setGenerator (final Function<String, License> generator) {
        //Hidden implementation...
        return false;
    }

    /**
     * Return this {@link License license}'s name.
     * @return this license's name
     * @author Telami
     * @since 1.0.0
     */
    public String getName () {
        //Hidden implementation...
        return null;
    }

    /**
     * Return this {@link License license}'s current {@link LicenseState state} through a {@link CompletableFuture}
     * that will notify the requester when this license is ready.
     * @return a completable future handling this license's state
     * @author Telami
     * @since 1.0.0
     */
    public abstract CompletableFuture<LicenseState> getState ();

    /**
     * Return the {@link Instant instant} this {@link License license} will expire. <br>
     * The expiration can degrade the performance during the runtime of the program! <br>
     * So always be sure your license have been paid ahead of time if temporary, it
     * can be also updated during runtime, so no need to close the program during
     * these operations.
     * @apiNote If {@link License#isTemporary() isTemporary()} returns {@code false},
     *          then this method returns the instant this license has been activated.
     * @return the instant this license will expire
     * @author Telami
     * @since 1.0.1
     */
    public abstract Instant getExpirationTime ();

    /**
     * Return whether this license is temporary or not.
     * @return {@code true} if this license has an expiration, {@code false} otherwise
     * @author Telami
     * @since 1.0.1
     */
    public abstract boolean isTemporary ();

    /**
     * Wait all the dependencies, obtained using the given names,
     * to finish their initialization. <br>
     * The result of their initialization may be successful or not.
     * @param names the given dependencies' names
     * @return a {@link CompletableFuture} that completes when all
     *         the dependencies finish their initialization
     * @author Telami
     * @since 1.0.1
     */
    public abstract CompletableFuture<Void> awaitDependencies (final String... names);
    /**
     * Wait all the dependencies, obtained using the given names,
     * to finish their initialization. <br>
     * The result of their initialization may be successful or not
     * and that will influence the result of the returned
     * {@link CompletableFuture}.
     * @param names the given dependencies' names
     * @return a {@link CompletableFuture} that completes when all
     *         the dependencies finish their initialization, using
     *         as result the worst {@link LicenseState state} found
     *         in the dependencies
     * @author Telami
     * @since 1.0.1
     */
    public abstract CompletableFuture<LicenseState> awaitDependenciesWithResult (final String... names);
}
