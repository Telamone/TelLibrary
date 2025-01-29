package it.telami.minecraft.bukkit.inventory;

import org.bukkit.inventory.ItemStack;

public non-sealed interface PagedInventoryTemplate extends InventoryTemplate {
    String title ();

    int numberOfLines ();

    ItemStack[] generateContent (final int page);

    int lastPage ();

    ItemStack previousPageItem ();
    ItemStack nextPageItem ();

    int[] previousPageSlots ();
    int[] nextPageSlots ();
}
