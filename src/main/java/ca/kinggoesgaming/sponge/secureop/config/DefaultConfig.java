package ca.kinggoesgaming.sponge.secureop.config;

import ca.kinggoesgaming.sponge.secureop.PluginDescription;
import ninja.leaping.configurate.ConfigurationOptions;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.hocon.HoconConfigurationLoader;
import ninja.leaping.configurate.loader.ConfigurationLoader;

import java.io.File;
import java.io.IOException;

/**
 * The default config for Secure OP
 */
public class DefaultConfig {

    private static DefaultConfig instance = new DefaultConfig();
    private static File configFile = new File("config/" + PluginDescription.ID + "/config.conf");
    private static ConfigurationLoader<CommentedConfigurationNode> configLoader = HoconConfigurationLoader.builder().setFile(configFile).build();
    private static CommentedConfigurationNode configNode = configLoader.createEmptyNode(ConfigurationOptions.defaults());

    private DefaultConfig() {
    }

    public static DefaultConfig getInstance() {
        return instance;
    }

    public void setup() {
        if (!configFile.exists()) {
            try {
                configFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        setDefaultNodes();
        load();
    }

    private void setDefaultNodes() {
        configNode.getNode("version").setComment("Do not touch this! This is auto handled by the plugin.").setValue(1);

        configNode.getNode("options", "timer").setComment("The amount of time a timed op remains op.\n" +
                "Note that any timed OP who logs out will automatically be deopped.");

        configNode.getNode("options", "timer", "enabled").setComment("Enable timed op mode").setValue(false);
        configNode.getNode("options", "timer", "days").setComment("Number of days a timed op stays op for.").setValue(0);
        configNode.getNode("options", "timer", "hours").setComment("Number of hours a timed op stays op for.").setValue(0);
        configNode.getNode("options", "timer", "minutes").setComment("Number of minutes a timed op stays op for.").setValue(0);
        configNode.getNode("options", "timer", "seconds").setValue("Number of seconds a timed op stays op for.").setValue(15);

        configNode.getNode("options", "security", "password").setComment("The password that is needed to do /op and other \"destructive\" commands").setValue("");
        configNode.getNode("options", "security", "secret").setComment("Password when you want to assign a temporary (timed) op. Note options.timer.enabled has to be true for this to work.").setValue("");

        configNode.getNode("options", "timedOps").setComment("Do not modify this list manually! The plugin automatically handles this.").setValue("");

        configNode.getNode("options", "permOps").setComment("The permanent ops. These ops need to enter the options.security.password password every time the server restarts!").setValue("");
    }

    public void load() {
        try {
            configNode = configLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void save() {
        try {
            configLoader.save(configNode);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public CommentedConfigurationNode getConfig() {
        return configNode;
    }

    public void upgrade() {
        setDefaultNodes();
        save();
        load();
    }
}
