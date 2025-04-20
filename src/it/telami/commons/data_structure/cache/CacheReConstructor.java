package it.telami.commons.data_structure.cache;

/**
 * Used by {@link Cache} for re-constructing value its precedent state. <br>
 * This will create a completely new reference, so it's important to take
 * care of instance dependent code! <br>
 * Beside that, this is a powerful optimization for the cache and has an
 * actual use case only if used together with {@link CacheStateExtractor},
 * where is described the definition of state.
 * @param <V> value's type
 * @param <V_state> value's state's type
 * @author Telami
 * @since 1.0.0
 */
@FunctionalInterface
public interface CacheReConstructor<V, V_state> {
    /**
     * Re-construct the value from its state, as described by {@link CacheStateExtractor}.
     * @param state value's state
     * @return a new value represented by the given state
     * @author Telami
     * @since 1.0.0
     */
    V reconstruct (final V_state state);
}
