package it.telami.minecraft.bukkit.inventory;

import org.bukkit.inventory.ItemStack;

public interface PagedInventoryTemplate extends BasicTemplate {
    String title ();

    int numberOfLines ();

    ItemStack[] generateContent (final int page);

    int lastPage ();

    ItemStack previousPageItem ();
    ItemStack nextPageItem ();

    int[] previousPageSlots ();
    int[] nextPageSlots ();
}
