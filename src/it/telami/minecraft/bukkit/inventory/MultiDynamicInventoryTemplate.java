package it.telami.minecraft.bukkit.inventory;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public non-sealed interface MultiDynamicInventoryTemplate extends InventoryTemplate {
    String title ();

    int numberOfLines ();

    ItemStack[] generateContent (final Player... players);
}
