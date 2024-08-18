package me.fedox.viewerrun.utils;

import org.bukkit.Bukkit;

import java.util.List;

import static me.fedox.viewerrun.utils.Constants.PREFIX;

public class DebugUtils {
    // List of developer usernames who will receive debug messages
    private static final List<String> devs = List.of("austrilol", "Fedox");

    /**
     * Sends a debug message to all online players whose names are in the devs list.
     *
     * @param message the debug message to be sent
     */
    public static void debug(String message) {
        Bukkit.getOnlinePlayers().stream()
                .filter(p -> devs.contains(p.getName()))
                .forEach(p -> p.sendMessage(PREFIX + message));
    }
}