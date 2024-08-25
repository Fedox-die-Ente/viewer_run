package me.fedox.viewerrun.core.handler;

import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;

/**
 * Handles caching of viewer data in the VRModel.
 */
public class CacheHandler {

    private static ViewerCache viewerCache;

    /**
     * Gets the cached data of the viewer.
     *
     * @return the cached data of the viewer
     */
    public static ViewerCache getViewerCache() {
        return viewerCache;
    }

    /**
     * Sets the cached data of the viewer.
     *
     * @param viewerCache the cached data to set
     */
    public static void setViewerCache(final ViewerCache viewerCache) {
        CacheHandler.viewerCache = viewerCache;
    }

    /**
     * Record to store the cached data of a viewer.
     *
     * @param items     the inventory items of the viewer
     * @param maxHealth the maximum health of the viewer
     * @param health    the current health of the viewer
     * @param food      the food level of the viewer
     * @param level     the experience level of the viewer
     * @param location  the location of the viewer
     */
    public record ViewerCache(
            ItemStack[] items, double maxHealth, double health, int food, int level, Location location
    ) {}
}