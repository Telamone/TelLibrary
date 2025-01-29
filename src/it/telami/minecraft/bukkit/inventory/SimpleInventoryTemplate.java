package it.telami.minecraft.bukkit.inventory;

import org.bukkit.inventory.ItemStack;

public non-sealed interface SimpleInventoryTemplate extends InventoryTemplate {
    String title ();

    int numberOfLines ();

    ItemStack[] generateContent ();
}
