package it.telami.minecraft.bukkit.inventory;

import org.bukkit.inventory.ItemStack;

/**
 * Extension of {@link InventoryTemplate}.
 * @author Telami
 * @since 1.0.0
 */
public non-sealed interface SimpleInventoryTemplate extends InventoryTemplate {
    /**
     * Generate the content of the just created inventory.
     * @return the new inventory's content
     * @author Telami
     * @since 1.0.0
     */
    ItemStack[] generateContent ();
}
