package me.fedox.viewerrun.listener;

import me.fedox.viewerrun.ViewerRun;
import me.fedox.viewerrun.core.VRModel;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import static me.fedox.viewerrun.utils.Constants.PREFIX;
import static me.fedox.viewerrun.utils.LocationUtils.teleportToSpawn;
import static me.fedox.viewerrun.utils.PlayerUtils.setMaxHealth;

public class PlayerJoinListener implements Listener {
    private final ViewerRun plugin;
    private final VRModel model;

    /**
     * Constructor for PlayerJoinListener.
     *
     * @param plugin the ViewerRun plugin instance
     * @param model the VRModel instance used to manage player states
     */
    public PlayerJoinListener(ViewerRun plugin, VRModel model) {
        this.plugin = plugin;
        this.model = model;
    }

    /**
     * Event handler for when a player joins the server.
     *
     * @param e the PlayerJoinEvent
     */
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        var player = e.getPlayer();

        // Send a welcome message to the player
        player.sendMessage(PREFIX + "§7Willkommen beim §aViewerRun §7Event!");
        // Set the join message for the player
        e.setJoinMessage("§8[§a+§8] §7" + player.getName());

        // Check if the player is the creator
        if (player.getUniqueId().toString().equals(plugin.getCreatorUUID())) {
            model.setCreator(player);

            // Set the player's max health if the event is not running
            if(!model.isEventRunning()) {
                setMaxHealth(player, 20);
            }

            return;
        }

        // Teleport the player to the spawn location
        teleportToSpawn(plugin, player);
    }
}