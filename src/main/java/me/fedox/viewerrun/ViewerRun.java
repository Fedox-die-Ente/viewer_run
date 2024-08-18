package me.fedox.viewerrun;

import lombok.Getter;
import lombok.Setter;
import me.fedox.viewerrun.commands.*;
import me.fedox.viewerrun.core.QueueWorker;
import me.fedox.viewerrun.core.Timer;
import me.fedox.viewerrun.core.VRModel;
import me.fedox.viewerrun.core.handler.PlayerSwitchHandler;
import me.fedox.viewerrun.listener.*;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;

import static me.fedox.viewerrun.utils.Constants.CONFIG_CREATOR;
import static me.fedox.viewerrun.utils.Constants.CONFIG_CREATOR_UUID;

/**
 * Main class for the ViewerRun plugin.
 * Extends JavaPlugin to integrate with the Bukkit API.
 */
public final class ViewerRun extends JavaPlugin {
    @Getter
    private static ViewerRun instance;

    private VRModel model;

    private QueueWorker queueWorker;

    @Getter
    @Setter
    public static String creatorUUID;

    @Getter
    @Setter
    public static Player viewer;

    /**
     * Called when the plugin is enabled.
     * Initializes the plugin, loads the configuration, and registers commands and listeners.
     */
    @Override
    public void onEnable() {
        instance = this;
        model = new VRModel();
        queueWorker = new QueueWorker(this, model);

        new PlayerSwitchHandler(this, model);
        new Timer(this, model);

        loadConfig();

        if (getConfig().get(CONFIG_CREATOR_UUID) == null) {
            getLogger().log(Level.WARNING, "No creator set");
        } else {
            String uuid = getConfig().getString(CONFIG_CREATOR_UUID);
            setCreatorUUID(uuid);
        }

        System.out.println("Config: " + getConfig().get(CONFIG_CREATOR_UUID));
        System.out.println("Creator: " + creatorUUID);

        registerCommands();
        registerListener();
    }

    /**
     * Called when the plugin is disabled.
     * Contains logic for shutting down the plugin.
     */
    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    /**
     * Registers all commands for the plugin.
     */
    private void registerCommands() {
        getCommand("setcreator").setExecutor(new SetCreatorCommand(this));
        getCommand("start").setExecutor(new StartCommand(model));
        getCommand("queue").setExecutor(new QueueCommand(queueWorker));
        getCommand("setspawn").setExecutor(new SetSpawnCommand(this));
        getCommand("pause").setExecutor(new PauseTimerCommand(this, model));
        getCommand("continue").setExecutor(new ContinueCommand(this, model));
        getCommand("forceskip").setExecutor(new ForceSkipCommand(this, queueWorker));
        getCommand("gm").setExecutor(new GMCommand());
        getCommand("tp").setExecutor(new TPCommand());
    }

    /**
     * Registers all event listeners for the plugin.
     */
    private void registerListener() {
        var pm = Bukkit.getPluginManager();

        pm.registerEvents(new PlayerJoinListener(this, model), this);
        pm.registerEvents(new PlayerQuitListener(queueWorker, model), this);
        pm.registerEvents(new PlayerDeathListener(this, queueWorker, model), this);
        pm.registerEvents(new QueueListener(model), this);
        pm.registerEvents(new EnderDragonShitListener(model), this);
    }

    /**
     * Loads the plugin configuration.
     * Copies default values and saves the configuration.
     */
    private void loadConfig() {
        getConfig().options().copyDefaults(true);
        saveConfig();
    }

}