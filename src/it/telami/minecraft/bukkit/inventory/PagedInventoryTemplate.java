package it.telami.minecraft.bukkit.inventory;

import org.bukkit.inventory.ItemStack;

/**
 * Extension of {@link InventoryTemplate}. <br>
 * Additionally it will have specifications for its pagination.
 * @author Telami
 * @since 1.0.0
 */
public non-sealed interface PagedInventoryTemplate extends InventoryTemplate {
    /**
     * Generate the content of the just created inventory
     * depending on the given page.
     * @param page the given page
     * @return the new inventory's content
     * @author Telami
     * @since 1.0.0
     */
    ItemStack[] generateContent (final int page);

    /**
     * Return this {@link PagedInventoryTemplate template}'s last page.
     * @return this template's last page
     * @author Telami
     * @since 1.0.0
     */
    int lastPage ();

    /**
     * Return this {@link PagedInventoryTemplate template}'s
     * 'previous page' item that will be placed in the slots returned
     * by {@link PagedInventoryTemplate#previousPageSlots()}.
     * @return this template's 'previous page' item
     * @author Telami
     * @since 1.0.0
     */
    ItemStack previousPageItem ();
    /**
     * Return this {@link PagedInventoryTemplate template}'s
     * 'next page' item that will be placed in the slots returned
     * by {@link PagedInventoryTemplate#nextPageSlots()}.
     * @return this template's 'next page' item
     * @author Telami
     * @since 1.0.0
     */
    ItemStack nextPageItem ();

    /**
     * Return an array containing the slots where the
     * {@link PagedInventoryTemplate#previousPageItem()}
     * will be placed.
     * @return an array containing the slots dedicated to the 'previous page' item
     * @author Telami
     * @since 1.0.0
     */
    int[] previousPageSlots ();
    /**
     * Return an array containing the slots where the
     * {@link PagedInventoryTemplate#nextPageItem()}
     * will be placed.
     * @return an array containing the slots dedicated to the 'next page' item
     * @author Telami
     * @since 1.0.0
     */
    int[] nextPageSlots ();
}
