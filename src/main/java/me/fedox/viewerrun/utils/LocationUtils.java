package me.fedox.viewerrun.utils;

import me.fedox.viewerrun.ViewerRun;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import static java.lang.Float.intBitsToFloat;
import static me.fedox.viewerrun.utils.Constants.CONFIG_LOCATION;

public class LocationUtils {
    public static void teleportToSpawn(ViewerRun plugin, Player player) {
        teleportToLocation(plugin, CONFIG_LOCATION, player);
    }

    public static void teleportToLocation(ViewerRun plugin, String location, Player player) {
        player.teleport(asLocation(plugin, location));
    }

    public static Location getSpawnLocation(ViewerRun plugin) {
        return asLocation(plugin, CONFIG_LOCATION);
    }

    public static Location asLocation(ViewerRun plugin, String key) {
        String world = plugin.getConfig().getString(key + ".World");
        double x = plugin.getConfig().getDouble(key + ".X");
        double y = plugin.getConfig().getDouble(key + ".Y");
        double z = plugin.getConfig().getDouble(key + ".Z");

        int yaw = plugin.getConfig().getInt(key + ".Yaw");
        int pitch = plugin.getConfig().getInt(key + ".Pitch");

        Location loc = new Location(Bukkit.getWorld(world), x, y, z);

        float fYaw = intBitsToFloat(yaw);
        float fPitch = intBitsToFloat(pitch);

        loc.setYaw(fYaw);
        loc.setPitch(fPitch);

        return loc;
    }

    public static void saveLocation(ViewerRun plugin, String key, Location location) {
        plugin.getConfig().set(key + ".World", location.getWorld().getName());
        plugin.getConfig().set(key + ".X", location.getX());
        plugin.getConfig().set(key + ".Y", location.getY());
        plugin.getConfig().set(key + ".Z", location.getZ());

        plugin.getConfig().set(key + ".Yaw", Float.floatToIntBits(location.getYaw()));
        plugin.getConfig().set(key + ".Pitch", Float.floatToIntBits(location.getPitch()));
        plugin.saveConfig();
    }
}
