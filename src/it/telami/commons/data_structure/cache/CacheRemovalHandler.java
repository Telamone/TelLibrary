package it.telami.commons.data_structure.cache;

/**
 * Used by {@link Cache} for triggering an action on the removal of
 * a value. <br>
 * The removal's reason is described by {@link Reason}. <br>
 * It's given the state instead of the value itself because it may
 * have been entirely removed from memory.
 * @param <K> key's type
 * @param <V_state> value's state's type
 * @author Telami
 * @since 1.0.0
 */
@FunctionalInterface
@SuppressWarnings("JavadocDeclaration")
public interface CacheRemovalHandler<K, V_state> {
    /**
     * Represent the reason that caused the {@link CacheRemovalHandler}
     * to be triggered.
     * @see Reason#INVALIDATED
     * @see Reason#EXPIRED
     * @see Reason#GC
     * @author Telami
     * @since 1.0.0
     */
    enum Reason {
        /**
         * {@link Cache#invalidate(Object) invalidate(...)} has been called
         * on this entry.
         * @see Reason
         * @author Telami
         * @since 1.0.0
         */
        INVALIDATED,
        /**
         * The time defined for value's removal has been reached. <br>
         * The value remained unused and so it has been removed from memory.
         * @see Reason
         * @author Telami
         * @since 1.0.0
         */
        EXPIRED,
        /**
         * The GC decided to forcefully free the memory, leading to
         * value's removal from the cache.
         * @see Reason
         * @author Telami
         * @since 1.0.0
         */
        GC
    }

    /**
     * Triggered when a value is removed from the cache, as defined by {@link CacheRemovalHandler}.
     * @param reason see {@link Reason}
     * @param key the disassociated key
     * @param state value's state
     * @author Telami
     * @since 1.0.0
     */
    void onRemove (final Reason reason, final K key, final V_state state);
}
