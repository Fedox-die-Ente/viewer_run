package me.fedox.viewerrun.core.handler;

import me.fedox.viewerrun.ViewerRun;
import me.fedox.viewerrun.core.QueueWorker;
import me.fedox.viewerrun.core.VRModel;
import me.fedox.viewerrun.utils.LocationUtils;
import org.bukkit.inventory.ItemStack;


import static java.util.Objects.nonNull;
import static me.fedox.viewerrun.utils.Constants.*;
import static me.fedox.viewerrun.utils.LocationUtils.saveLocation;
import static me.fedox.viewerrun.utils.LocationUtils.teleportToLocation;
import static me.fedox.viewerrun.utils.PlayerUtils.getMaxHealth;
import static me.fedox.viewerrun.utils.PlayerUtils.setMaxHealth;

public class SaveHandler {
    public static void saveData(ViewerRun plugin, VRModel model) {
        plugin.reloadConfig();
        var conf = plugin.getConfig();

        var creator = model.getCreator();
        var viewer = model.getViewer();

        conf.set(CONFIG_CURRENT_RUN_TIME, model.getCurrentTime());

        if(nonNull(creator)) {
            conf.set(CONFIG_CREATOR_INV, creator.getInventory().getContents());
            conf.set(CONFIG_CREATOR_HEALTH, creator.getHealth());
            conf.set(CONFIG_CREATOR_MAXHEALTH, getMaxHealth(creator));
            conf.set(CONFIG_CREATOR_FOOD, creator.getFoodLevel());
            saveLocation(plugin, CONFIG_CREATOR_LOCATION, creator.getLocation());
        }

        if(nonNull(viewer)) {
            conf.set(CONFIG_VIEWER_INV, viewer.getInventory().getContents());
            conf.set(CONFIG_VIEWER_HEALTH, viewer.getHealth());
            conf.set(CONFIG_VIEWER_MAXHEALTH, getMaxHealth(viewer));
            conf.set(CONFIG_VIEWER_FOOD, viewer.getFoodLevel());
            saveLocation(plugin, CONFIG_VIEWER_LOCATION, viewer.getLocation());
        }


        plugin.saveConfig();
    }

    public static void loadData(ViewerRun plugin, VRModel model) {
        plugin.reloadConfig();
        var conf = plugin.getConfig();

        var creator = model.getCreator();

        model.setCurrentTime(conf.getInt(CONFIG_CURRENT_RUN_TIME));

        if(nonNull(creator)) {
            creator.getInventory().setContents(conf.getList(CONFIG_CREATOR_INV).toArray(ItemStack[]::new));
            creator.setHealth(conf.getDouble(CONFIG_CREATOR_HEALTH));
            setMaxHealth(creator, conf.getDouble(CONFIG_CREATOR_MAXHEALTH));
            creator.setFoodLevel(conf.getInt(CONFIG_CREATOR_FOOD));
            teleportToLocation(plugin, CONFIG_CREATOR_LOCATION, creator);
        }

        var location = LocationUtils.asLocation(plugin, CONFIG_VIEWER_LOCATION);
        CacheHandler.setViewerCache(new CacheHandler.ViewerCache(conf.getList(CONFIG_VIEWER_INV).toArray(ItemStack[]::new), conf.getDouble(CONFIG_VIEWER_MAXHEALTH), conf.getDouble(CONFIG_VIEWER_HEALTH), conf.getInt(CONFIG_VIEWER_FOOD), location));
    }
}
