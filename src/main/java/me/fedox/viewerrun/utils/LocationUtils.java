package me.fedox.viewerrun.utils;

import me.fedox.viewerrun.ViewerRun;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import static java.lang.Float.intBitsToFloat;
import static me.fedox.viewerrun.utils.Constants.CONFIG_LOCATION;

public class LocationUtils {

    /**
     * Teleports a player to the spawn location.
     *
     * @param plugin the instance of the ViewerRun plugin
     * @param player the player to be teleported
     */
    public static void teleportToSpawn(ViewerRun plugin, Player player) {
        teleportToLocation(plugin, CONFIG_LOCATION, player);
    }

    /**
     * Teleports a player to a specified location.
     *
     * @param plugin the instance of the ViewerRun plugin
     * @param location the key of the location in the configuration
     * @param player the player to be teleported
     */
    public static void teleportToLocation(ViewerRun plugin, String location, Player player) {
        player.teleport(asLocation(plugin, location));
    }

    /**
     * Retrieves the spawn location from the configuration.
     *
     * @param plugin the instance of the ViewerRun plugin
     * @return the spawn location
     */
    public static Location getSpawnLocation(ViewerRun plugin) {
        return asLocation(plugin, CONFIG_LOCATION);
    }

    /**
     * Converts a location key from the configuration to a Location object.
     *
     * @param plugin the instance of the ViewerRun plugin
     * @param key the key of the location in the configuration
     * @return the Location object
     */
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

    /**
     * Saves a Location object to the configuration.
     *
     * @param plugin the instance of the ViewerRun plugin
     * @param key the key under which the location will be saved
     * @param location the Location object to be saved
     */
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