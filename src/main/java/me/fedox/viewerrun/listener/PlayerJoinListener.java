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

    public PlayerJoinListener(ViewerRun plugin, VRModel model) {
        this.plugin = plugin;
        this.model = model;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        var player = e.getPlayer();

        player.sendMessage(PREFIX + "§7Willkommen beim §aViewerRun §7Event!");
        e.setJoinMessage("§8[§a+§8] §7" + player.getName());

        if (player.getUniqueId().toString().equals(plugin.getCreatorUUID())) {
            model.setCreator(player);

            if(!model.isEventRunning()) {
                setMaxHealth(player, 20);
            }

            return;
        }

        teleportToSpawn(plugin, player);
    }
}
