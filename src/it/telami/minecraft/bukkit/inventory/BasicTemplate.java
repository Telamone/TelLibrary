package it.telami.minecraft.bukkit.inventory;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

interface BasicTemplate extends InventoryHolder {
    default Inventory getInventory () {
        return null;
    }
}
