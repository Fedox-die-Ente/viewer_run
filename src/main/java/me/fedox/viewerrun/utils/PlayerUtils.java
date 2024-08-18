package me.fedox.viewerrun.utils;

import org.bukkit.attribute.Attributable;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;

public class PlayerUtils {

    /**
     * Sets the maximum health of a player.
     *
     * @param player the player whose maximum health is to be set
     * @param value the value to set the maximum health to
     */
    public static void setMaxHealth(Player player, double value) {
        var attribute = ((Attributable)player).getAttribute(Attribute.GENERIC_MAX_HEALTH);
        attribute.setBaseValue(value);
    }

    /**
     * Gets the maximum health of a player.
     *
     * @param player the player whose maximum health is to be retrieved
     * @return the maximum health value of the player
     */
    public static double getMaxHealth(Player player) {
        var attribute = ((Attributable)player).getAttribute(Attribute.GENERIC_MAX_HEALTH);
        return attribute.getValue();
    }
}