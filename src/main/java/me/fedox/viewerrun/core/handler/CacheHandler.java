package me.fedox.viewerrun.core.handler;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;

public class CacheHandler {
    public static record ViewerCache(ItemStack[] items, double maxHealth, double health, int food, Location location) {}

    private static ViewerCache viewerCache;

    public static ViewerCache getViewerCache() {
        return viewerCache;
    }

    public static void setViewerCache(ViewerCache viewerCache) {
        CacheHandler.viewerCache = viewerCache;
    }
}
