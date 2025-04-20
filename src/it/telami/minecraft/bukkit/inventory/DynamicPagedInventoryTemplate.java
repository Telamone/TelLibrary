package it.telami.minecraft.bukkit.inventory;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * Extension of {@link InventoryTemplate}. <br>
 * The generated inventory will be dependent by the player it is created for. <br>
 * Additionally it will have specifications for its pagination.
 * @author Telami
 * @since 1.0.0
 */
public non-sealed interface DynamicPagedInventoryTemplate extends InventoryTemplate {
    /**
     * Generate the content of the just created inventory dynamically
     * depending on the given page and player.
     * @param page the given page
     * @param player the given player
     * @return the new inventory's content
     * @author Telami
     * @since 1.0.0
     */
    ItemStack[] generateContent (final int page, final Player player);

    /**
     * Return this {@link DynamicPagedInventoryTemplate template}'s last page.
     * @return this template's last page
     * @author Telami
     * @since 1.0.0
     */
    int lastPage ();

    /**
     * Return this {@link DynamicPagedInventoryTemplate template}'s
     * 'previous page' item that will be placed in the slots returned
     * by {@link DynamicPagedInventoryTemplate#previousPageSlots()}.
     * @return this template's 'previous page' item
     * @author Telami
     * @since 1.0.0
     */
    ItemStack previousPageItem ();
    /**
     * Return this {@link DynamicPagedInventoryTemplate template}'s
     * 'next page' item that will be placed in the slots returned
     * by {@link DynamicPagedInventoryTemplate#nextPageSlots()}.
     * @return this template's 'next page' item
     * @author Telami
     * @since 1.0.0
     */
    ItemStack nextPageItem ();

    /**
     * Return an array containing the slots where the
     * {@link DynamicPagedInventoryTemplate#previousPageItem()}
     * will be placed.
     * @return an array containing the slots dedicated to the 'previous page' item
     * @author Telami
     * @since 1.0.0
     */
    int[] previousPageSlots ();
    /**
     * Return an array containing the slots where the
     * {@link DynamicPagedInventoryTemplate#nextPageItem()}
     * will be placed.
     * @return an array containing the slots dedicated to the 'next page' item
     * @author Telami
     * @since 1.0.0
     */
    int[] nextPageSlots ();
}
