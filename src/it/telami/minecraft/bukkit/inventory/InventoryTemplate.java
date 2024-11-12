package it.telami.minecraft.bukkit.inventory;

import org.bukkit.inventory.ItemStack;

public interface InventoryTemplate extends BasicTemplate {
    String title ();

    int numberOfLines ();

    ItemStack[] generateContent ();
}
