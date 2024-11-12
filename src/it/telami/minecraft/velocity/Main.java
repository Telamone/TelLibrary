package it.telami.minecraft.velocity;

import com.velocitypowered.api.event.PostOrder;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.plugin.annotation.DataDirectory;
import com.velocitypowered.api.proxy.ProxyServer;

import javax.inject.Inject;
import java.nio.file.Path;
import java.util.logging.Logger;

@Plugin(id = "TelLib", name = "TelLib", version = "1.0.0", authors = {"Telami"})
public final class Main {
    @Inject
    public Main (final ProxyServer proxy, final Logger logger, @DataDirectory final Path pluginsDirectory) {}

    @Subscribe(order = PostOrder.FIRST)
    private void onEnable (final ProxyInitializeEvent e) {}
}
