package it.telami.minecraft.bukkit.inventory;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * Extension of {@link InventoryTemplate}. <br>
 * The generated inventory will be dependent by the players it is created for.
 * @author Telami
 * @since 1.0.0
 */
public non-sealed interface MultiDynamicInventoryTemplate extends InventoryTemplate {
    /**
     * Generate the content of the just created inventory dynamically
     * depending on the given players.
     * @param players the given players
     * @return the new inventory's content
     * @author Telami
     * @since 1.0.0
     */
    ItemStack[] generateContent (final Player... players);
}
