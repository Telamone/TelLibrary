package it.telami.minecraft.bukkit.inventory;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public non-sealed interface MultiDynamicPagedInventoryTemplate extends InventoryTemplate {
    String title ();

    int numberOfLines ();

    ItemStack[] generateContent (final int page, final Player... players);

    int lastPage ();

    ItemStack previousPageItem ();
    ItemStack nextPageItem ();

    int[] previousPageSlots ();
    int[] nextPageSlots ();
}
