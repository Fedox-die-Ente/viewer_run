package me.fedox.viewerrun.utils;

import org.bukkit.attribute.Attributable;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;

public class PlayerUtils {
    public static void setMaxHealth(Player player, double value) {
        var attribute = ((Attributable)player).getAttribute(Attribute.GENERIC_MAX_HEALTH);
        attribute.setBaseValue(value);
    }

    public static double getMaxHealth(Player player) {
        var attribute = ((Attributable)player).getAttribute(Attribute.GENERIC_MAX_HEALTH);
        return attribute.getValue();
    }
}
