package it.telami.minecraft.bukkit.inventory;

import it.telami.annotations.PlannedForFuture;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

/**
 * Class used for creating and handling inventories in various ways. <br>
 * The creation take advantage of {@link InventoryTemplate}'s extensions.
 * @author Telami
 * @since 1.0.0
 */
public final class InventoryUtils {
    private InventoryUtils () {}

    /**
     * Create a new inventory given its template with its corresponding dependencies. <br>
     * If the template doesn't depend on the given page or on the given players, then
     * the given arguments are ignored. <br>
     * If the template depend on a single player, then only the first players'
     * element will be read.
     * @param template the given template
     * @param page the given page
     * @param players the given players
     * @return a new inventory
     * @param <T> an {@link InventoryTemplate}'s extension type
     * @author Telami
     * @since 1.0.0
     */
    public static <T extends InventoryTemplate> Inventory createInventory (final T template, final int page, final Player... players) {
        return null;
    }
    /**
     * Does the same as {@link InventoryUtils#createInventory(InventoryTemplate, int, Player...)},
     * but doesn't perform any check.
     * @param template the given template
     * @param page the given page
     * @param players the given players
     * @return a new inventory
     * @param <T> an {@link InventoryTemplate}'s extension type
     * @author Telami
     * @since 1.0.0
     */
    public static <T extends InventoryTemplate> Inventory createInventoryUnchecked (final T template, final int page, final Player... players) {
        return null;
    }

    /**
     * Create a new inventory based on the given {@link SimpleInventoryTemplate template}.
     * @param template the given template
     * @return a new inventory
     * @author Telami
     * @since 1.0.0
     */
    public static Inventory createInventory (final SimpleInventoryTemplate template) {
        return null;
    }
    /**
     * Create a new inventory based on the given {@link SimpleInventoryTemplate template},
     * but doesn't perform any check.
     * @param template the given template
     * @return a new inventory
     * @author Telami
     * @since 1.0.0
     */
    public static Inventory createInventoryUnchecked (final SimpleInventoryTemplate template) {
        return null;
    }
    /**
     * Create a new inventory based on the given {@link DynamicInventoryTemplate template}
     * and the given player.
     * @param template the given template
     * @param player the given player
     * @return a new inventory
     * @author Telami
     * @since 1.0.0
     */
    public static Inventory createInventory (
            final DynamicInventoryTemplate template,
            final Player player) {
        return null;
    }
    /**
     * Create a new inventory based on the given {@link DynamicInventoryTemplate template}
     * and the given player, but doesn't perform any check.
     * @param template the given template
     * @param player the given player
     * @return a new inventory
     * @author Telami
     * @since 1.0.0
     */
    public static Inventory createInventoryUnchecked (
            final DynamicInventoryTemplate template,
            final Player player) {
        return null;
    }
    /**
     * Create a new inventory based on the given {@link MultiDynamicInventoryTemplate template}
     * and the given players.
     * @param template the given template
     * @param players the given players
     * @return a new inventory
     * @author Telami
     * @since 1.0.0
     */
    public static Inventory createInventory (
            final MultiDynamicInventoryTemplate template,
            final Player... players) {
        return null;
    }
    /**
     * Create a new inventory based on the given {@link MultiDynamicInventoryTemplate template}
     * and the given players, but doesn't perform any check.
     * @param template the given template
     * @param players the given players
     * @return a new inventory
     * @author Telami
     * @since 1.0.0
     */
    public static Inventory createInventoryUnchecked (
            final MultiDynamicInventoryTemplate template,
            final Player... players) {
        return null;
    }
    /**
     * Create a new inventory based on the given {@link PagedInventoryTemplate template}
     * and the given page.
     * @param template the given template
     * @param page the given page
     * @return a new inventory
     * @author Telami
     * @since 1.0.0
     */
    public static Inventory createInventory (
            final PagedInventoryTemplate template,
            final int page) {
        return null;
    }
    /**
     * Create a new inventory based on the given {@link PagedInventoryTemplate template}
     * and the given page, but doesn't perform any check.
     * @param template the given template
     * @param page the given page
     * @return a new inventory
     * @author Telami
     * @since 1.0.0
     */
    public static Inventory createInventoryUnchecked (
            final PagedInventoryTemplate template,
            final int page) {
        return null;
    }
    /**
     * Create a new inventory based on the given {@link DynamicPagedInventoryTemplate template},
     * the given page and the given player.
     * @param template the given template
     * @param page the given page
     * @param player the given player
     * @return a new inventory
     * @author Telami
     * @since 1.0.0
     */
    public static Inventory createInventory (
            final DynamicPagedInventoryTemplate template,
            final int page,
            final Player player) {
        return null;
    }
    /**
     * Create a new inventory based on the given {@link DynamicPagedInventoryTemplate template},
     * the given page and the given player, but doesn't perform any check.
     * @param template the given template
     * @param page the given page
     * @param player the given player
     * @return a new inventory
     * @author Telami
     * @since 1.0.0
     */
    public static Inventory createInventoryUnchecked (
            final DynamicPagedInventoryTemplate template,
            final int page,
            final Player player) {
        return null;
    }
    /**
     * Create a new inventory based on the given {@link MultiDynamicPagedInventoryTemplate template},
     * the given page and the given players.
     * @param template the given template
     * @param page the given page
     * @param players the given players
     * @return a new inventory
     * @author Telami
     * @since 1.0.0
     */
    public static Inventory createInventory (
            final MultiDynamicPagedInventoryTemplate template,
            final int page,
            final Player... players) {
        return null;
    }
    /**
     * Create a new inventory based on the given {@link MultiDynamicPagedInventoryTemplate template},
     * the given page and the given players, but doesn't perform any check.
     * @param template the given template
     * @param page the given page
     * @param players the given players
     * @return a new inventory
     * @author Telami
     * @since 1.0.0
     */
    public static Inventory createInventoryUnchecked (
            final MultiDynamicPagedInventoryTemplate template,
            final int page,
            final Player... players) {
        return null;
    }

    /**
     * See {@link InventoryUtils#fillRandomlyUnchecked(int[], long[], long, ItemStack[], Inventory) fillRandomlyUnchecked(...)}
     * for full documentation, this method adds data validation that might be slow! <br>
     * For avoiding useless overhead, use it like in this example:
     * <pre> {@code
     * //Loads data from configuration...
     * //(This first call will check the loaded data!)
     * final int[] firstOutput = fillRandomly(...);
     * //Use these data (if no exception have been dropped)
     * (... = new Thread(() -> {
     *     //Updates again the data periodically...
     *     //(Data have been already checked!)
     *     final int[] newOutput = fillRandomlyUnchecked(...);
     *     //And so on...
     * })).start();
     * } </pre>
     * This example shown how to update periodically a set of random items!
     * @param slots (as described in {@link InventoryUtils#fillRandomlyUnchecked(int[], long[], long, ItemStack[], Inventory) fillRandomlyUnchecked(...)})
     * @param chances (as described in {@link InventoryUtils#fillRandomlyUnchecked(int[], long[], long, ItemStack[], Inventory) fillRandomlyUnchecked(...)})
     * @param lastChance (as described in {@link InventoryUtils#fillRandomlyUnchecked(int[], long[], long, ItemStack[], Inventory) fillRandomlyUnchecked(...)})
     * @param items (as described in {@link InventoryUtils#fillRandomlyUnchecked(int[], long[], long, ItemStack[], Inventory) fillRandomlyUnchecked(...)})
     * @param inv (as described in {@link InventoryUtils#fillRandomlyUnchecked(int[], long[], long, ItemStack[], Inventory) fillRandomlyUnchecked(...)})
     * @return as described in {@link InventoryUtils#fillRandomlyUnchecked(int[], long[], long, ItemStack[], Inventory) fillRandomlyUnchecked(...)}
     * @author Telami
     * @since 1.0.0
     */
    public static int[] fillRandomly (
            final int[] slots,
            final long[] chances,
            final long lastChance,
            final ItemStack[] items,
            final Inventory inv) {
        return null;
    }
    /**
     * Fill the slots (in range from 0 to {@link Inventory#getSize() inv.getSize()}) of a given
     * inventory with the given items chosen randomly in respect of the given chances. <p>
     * Note that, most of the time, having a single item with <b>very high</b> percentage in
     * respect to the others, since a single item <b>cannot</b> be inserted twice, will
     * slow down the calculation!
     * @param slots array containing the indexes, of the given inventory, to fill
     * @param chances chances that MUST respect this convention:
     *                <pre> {@code
     *                chances == {0, 5, 25, 40, 65}  &&  lastChance == 100
     *                } </pre>
     *                will be interpreted like this:
     *                <pre> {@code
     *                {5%, 20%, 15%, 25%, 35%}
     *                } </pre>
     *                The indexes of the percentage is the same of the item it is referred to:
     *                <pre> {@code
     *                chances[0] -> items[0]
     *                } </pre>
     *                If decimal percentage is needed, increase 'lastChance'! <br>
     *                The formula used for calculating percentage is this: <br>
     *                (replacing eventually 'chances[length]' with 'lastChance')
     *                <pre> {@code
     *                //Returns something like 43.75 that is in percentage 43.75%
     *                n -> ((chances[n + 1] - chances[n]) / (double) lastChance) * 100.0D;
     *                } </pre>
     *                You can reverse this formula for mapping chances in configuration
     *                to this convention's chances, resulting in one similar to this:
     *                <pre> {@code
     *                final byte afterPointDigits = ...;
     *                //There are many other solutions for calculating 'lastChance'.
     *                //Other solutions may guarantee coherency using percentages that
     *                //summed don't necessary need to be 100!
     *                //It's additionally possible to calculate the 'afterPointDigits'
     *                //taking the percentage as a String and then processing it.
     *                //The programmer have the freedom to choose the right method
     *                //needed in its specific case.
     *                final long lastChance = Math.pow(10, afterPointDigits);
     *                long currentChance = 0;
     *                long[] chances = ...;
     *                //index variable to increment, possibly, in a loop!
     *                int i = 0;
     *                //This isn't a lambda, it's a function's pseudo-representation!
     *                newPercentage -> {
     *                    assert i >= 0 && i < chances.length;
     *                    chances[i] = currentChance;
     *                    currentChance += (long) ((newPercentage / 100.0D) * lastChance) + currentChance;
     *                }
     *                } </pre>
     *                <p>
     *                Alternatively, is preferred to use the already optimized {@link InventoryUtils#extractChances(ConfigurationSection, String) extractChances(...)}
     *                function that will be supported in the premium version of a future release specified in its annotation {@link PlannedForFuture}.
     *                </p>
     *                Using the example above, changing 'lastChance' to 1000,
     *                the percentages would look like this:
     *                <pre> {@code
     *                {0.5%, 2%, 1.5%, 2.5%, 93.5%}
     *                } </pre>
     * @param lastChance is the last chance, following the precedent convention, that is
     *                  responsible, as shown above, for the 'after point digit approximation'
     * @param items array containing the items that will be randomly chosen
     * @param inv the inventory to fill
     * @return an array containing the indexes of the chosen items.
     * @author Telami
     * @since 1.0.0
     */
    public static int[] fillRandomlyUnchecked (
            final int[] slots,
            final long[] chances,
            final long lastChance,
            final ItemStack[] items,
            final Inventory inv) {
        return null;
    }

    @PlannedForFuture
    public static ChancePair extractChances (final ConfigurationSection cs,
                                             final String inItemToPercentagePath) {
        //When implementing this method, update the documentation above!
        throw new UnsupportedOperationException();
    }
    //Waiting for project Valhalla :)
    public static final class ChancePair {
        public final long lastChance;
        public final long[] chances;
        private ChancePair (final long lastChance,
                            final long[] chances) {
            this.lastChance = lastChance;
            this.chances = chances;
        }
    }
}
