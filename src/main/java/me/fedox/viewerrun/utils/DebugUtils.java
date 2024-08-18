package me.fedox.viewerrun.utils;

import org.bukkit.Bukkit;

import java.util.List;

import static me.fedox.viewerrun.utils.Constants.PREFIX;

public class DebugUtils {
    private static final List<String> devs = List.of("austrilol", "Fedox");

    public static void debug(String message) {
        Bukkit.getOnlinePlayers().stream()
                .filter(p -> devs.contains(p.getName()))
                .forEach(p -> p.sendMessage(PREFIX + message));
    }
}
