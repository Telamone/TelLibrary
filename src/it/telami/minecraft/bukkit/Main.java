package it.telami.minecraft.bukkit;

import it.telami.license.License;
import it.telami.license.LicenseState;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.concurrent.Executors;

public final class Main extends JavaPlugin {
    public void onEnable () {
        License.getByName("TelLib")
                .requestVersion("1.0.3")
                .getState()
                .thenAcceptAsync(state -> {
                    try {
                        if (state != LicenseState.ACTIVE) {
                            Bukkit.getPluginManager().disablePlugin(this);
                            return;
                        }
                        getLogger().info("Plugin enabled!");
                    } catch (final Throwable t) {
                        Bukkit.getPluginManager().disablePlugin(this);
                        //noinspection CallToPrintStackTrace
                        t.printStackTrace();
                    }
                }, Executors.newSingleThreadExecutor());
    }

    public void onLoad () {}

    public void onDisable () {}
}
