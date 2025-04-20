package it.telami.commons.data_structure.cache;

/**
 * Used by {@link Cache} for extracting value's <b>state</b>.
 * <h2>State</h2>
 * The state is defined to be the representation of value's content
 * and is important because it's the only component that persists
 * after the value's death in memory. <br>
 * The value, in the cache, might be cleaned away by the GC, at that
 * time it will be too late (or too inefficient) to retrieve the
 * reference again, so, for triggering the {@link CacheRemovalHandler},
 * it will be used value's state instead of the original value. <br>
 * It was a bit concerning at the beginning, but this approach leads to
 * <b>big benefits</b>. <br>
 * Example (<b>Read carefully the comments</b>):
 * <pre> {@code
 * //When the Market is empty, it's pushed to the Database, until a client enter in it
 * public class Market {
 *     //Transient data (you don't want them to be pushed in the Database)
 *     long clock;
 *     int clientsInside;
 *
 *     //Data that have to be saved (they have been compressed in the state)
 *     //Don't worry about the extra steps to get the data, the JVM may decide
 *     //to inline them
 *     final State state;
 *
 *     public void doSomething () {...}
 *
 *     //Having this representation let you free the Market but continue
 *     //to manage its state, that may lead to resurrection through the
 *     //CacheReConstructor in some cases.
 *     //Eventually you can maintain your class structure (without the need
 *     //of creating this class) and instead create a simple Wrapper that
 *     //act as value's state holder or represent its state through an
 *     //Object[] containing the interested variables ONLY IF your variables
 *     //are immutable.
 *     //Only this will be pushed to the Database, cleaning the rest of
 *     //the memory
 *     public static final class State implements Serializable {
 *         double money;
 *         //etc...
 *     }
 * }
 * } </pre>
 * From this, it's possible to deduce that the state is that simple thing that
 * represent the value, maintaining alive its most important components.
 * @param <V> value's type
 * @param <V_state> value's state's type
 * @author Telami
 * @since 1.0.0
 */
@FunctionalInterface
public interface CacheStateExtractor<V, V_state> {
    /**
     * Extract from the value its state, as defined by {@link CacheStateExtractor}.
     * @param value the given value
     * @return value's state
     * @author Telami
     * @since 1.0.0
     */
    V_state extract (final V value);
}
