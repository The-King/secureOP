package ca.kinggoesgaming.sponge.secureop;

import ca.kinggoesgaming.sponge.secureop.config.DefaultConfig;
import com.google.inject.Inject;
import org.slf4j.Logger;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.GameInitializationEvent;
import org.spongepowered.api.event.game.state.GamePreInitializationEvent;
import org.spongepowered.api.event.game.state.GameStartedServerEvent;
import org.spongepowered.api.event.game.state.GameStoppingServerEvent;
import org.spongepowered.api.plugin.Plugin;

import java.io.File;

/**
 * The main class for Secure OP plugin
 */
@Plugin(id = PluginDescription.ID, name = PluginDescription.NAME, version = PluginDescription.VERSION)
public class SecureOP {

    @Inject
    private Logger logger;

    private File configDir = new File("config/" + PluginDescription.ID + "/");

    public Logger getLogger() {
        return logger;
    }

    @Listener
    public void onPreInitializationEvent(GamePreInitializationEvent event) {
        getLogger().info("Loading...");
    }

    @Listener
    public void onInitization(GameInitializationEvent event) {
        getLogger().info("Loading config");
        if (!configDir.exists()) {
            configDir.mkdirs();
        }
        DefaultConfig.getInstance().setup();
    }

    @Listener
    public void onServerStarted(GameStartedServerEvent event) {
        getLogger().info("Loaded!");
    }

    @Listener
    public void onServerStopping(GameStoppingServerEvent event) {
        getLogger().info("Unloading...");
    }
}
