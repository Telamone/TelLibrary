package it.telami.minecraft.bukkit.inventory;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public interface MultiDynamicInventoryTemplate extends BasicTemplate {
    String title ();

    int numberOfLines ();

    ItemStack[] generateContent (final Player... players);
}
