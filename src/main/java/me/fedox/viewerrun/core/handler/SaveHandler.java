package me.fedox.viewerrun.core.handler;

import me.fedox.viewerrun.ViewerRun;
import me.fedox.viewerrun.core.VRModel;
import me.fedox.viewerrun.utils.LocationUtils;
import org.bukkit.inventory.ItemStack;

import static java.util.Objects.nonNull;
import static me.fedox.viewerrun.utils.Constants.*;
import static me.fedox.viewerrun.utils.LocationUtils.saveLocation;
import static me.fedox.viewerrun.utils.LocationUtils.teleportToLocation;
import static me.fedox.viewerrun.utils.PlayerUtils.getMaxHealth;
import static me.fedox.viewerrun.utils.PlayerUtils.setMaxHealth;

/**
 * Handles saving and loading data for the VRModel.
 */
public class SaveHandler {

    /**
     * Saves the current state of the VRModel to the plugin's configuration.
     *
     * @param plugin the ViewerRun plugin instance
     * @param model  the VRModel instance to save
     */
    public static void saveData(final ViewerRun plugin, final VRModel model) {
        plugin.reloadConfig();
        final var conf = plugin.getConfig();

        final var creator = model.getCreator();
        final var viewer = model.getViewer();

        conf.set(CONFIG_CURRENT_RUN_TIME, model.getCurrentTime());

        if (nonNull(creator)) {
            conf.set(CONFIG_CREATOR_INV, creator.getInventory().getContents());
            conf.set(CONFIG_CREATOR_HEALTH, creator.getHealth());
            conf.set(CONFIG_CREATOR_MAXHEALTH, getMaxHealth(creator));
            conf.set(CONFIG_CREATOR_FOOD, creator.getFoodLevel());
            conf.set(CONFIG_CREATOR_LEVEL, creator.getLevel());
            saveLocation(plugin, CONFIG_CREATOR_LOCATION, creator.getLocation());
        }

        if (nonNull(viewer)) {
            conf.set(CONFIG_VIEWER_INV, viewer.getInventory().getContents());
            conf.set(CONFIG_VIEWER_HEALTH, viewer.getHealth());
            conf.set(CONFIG_VIEWER_MAXHEALTH, getMaxHealth(viewer));
            conf.set(CONFIG_VIEWER_FOOD, viewer.getFoodLevel());
            conf.set(CONFIG_VIEWER_LEVEL, viewer.getLevel());
            saveLocation(plugin, CONFIG_VIEWER_LOCATION, viewer.getLocation());
        }

        plugin.saveConfig();
    }

    /**
     * Loads the saved state of the VRModel from the plugin's configuration.
     *
     * @param plugin the ViewerRun plugin instance
     * @param model  the VRModel instance to load
     */
    public static void loadData(final ViewerRun plugin, final VRModel model) {
        plugin.reloadConfig();
        final var conf = plugin.getConfig();

        final var creator = model.getCreator();

        model.setCurrentTime(conf.getInt(CONFIG_CURRENT_RUN_TIME));

        if (nonNull(creator)) {
            creator.getInventory().setContents(conf.getList(CONFIG_CREATOR_INV).toArray(ItemStack[]::new));
            creator.setHealth(conf.getDouble(CONFIG_CREATOR_HEALTH));
            setMaxHealth(creator, conf.getDouble(CONFIG_CREATOR_MAXHEALTH));
            creator.setFoodLevel(conf.getInt(CONFIG_CREATOR_FOOD));
            creator.setLevel(conf.getInt(CONFIG_CREATOR_LEVEL));
            teleportToLocation(plugin, CONFIG_CREATOR_LOCATION, creator);
        }

        final var location = LocationUtils.asLocation(plugin, CONFIG_VIEWER_LOCATION);
        CacheHandler.setViewerCache(new CacheHandler.ViewerCache(conf.getList(CONFIG_VIEWER_INV).toArray(ItemStack[]::new), conf.getDouble(CONFIG_VIEWER_MAXHEALTH), conf.getDouble(CONFIG_VIEWER_HEALTH), conf.getInt(CONFIG_VIEWER_FOOD), conf.getInt(CONFIG_VIEWER_LEVEL), location));
    }
}