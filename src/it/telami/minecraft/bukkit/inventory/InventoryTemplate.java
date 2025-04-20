package it.telami.minecraft.bukkit.inventory;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

/**
 * Defines a basic inventory holder that handles an inventory's
 * title and number of lines. <br>
 * Using this class's extensions can be useful if implemented
 * for distinguishing the type of inventory that is currently
 * handled.
 * @see DynamicInventoryTemplate
 * @see DynamicPagedInventoryTemplate
 * @see MultiDynamicInventoryTemplate
 * @see MultiDynamicPagedInventoryTemplate
 * @see PagedInventoryTemplate
 * @see SimpleInventoryTemplate
 * @author Telami
 * @since 1.0.0
 */
public sealed interface InventoryTemplate extends InventoryHolder permits
        DynamicInventoryTemplate,
        DynamicPagedInventoryTemplate,
        MultiDynamicInventoryTemplate,
        MultiDynamicPagedInventoryTemplate,
        PagedInventoryTemplate,
        SimpleInventoryTemplate {
    default Inventory getInventory () {
        return null;
    }

    /**
     * Return the just created inventory's title.
     * @return the new inventory's title
     * @author Telami
     * @since 1.0.0
     */
    String title ();

    /**
     * Return the just created inventory's number of lines.
     * @return the new inventory's number of lines
     * @author Telami
     * @since 1.0.0
     */
    int numberOfLines ();
}
