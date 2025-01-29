package it.telami.minecraft.bukkit.inventory;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

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
}
