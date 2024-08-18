package me.fedox.viewerrun.listener;

import me.fedox.viewerrun.ViewerRun;
import me.fedox.viewerrun.core.QueueWorker;
import me.fedox.viewerrun.core.VRModel;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

import static me.fedox.viewerrun.core.handler.SaveHandler.saveData;
import static me.fedox.viewerrun.utils.LocationUtils.getSpawnLocation;
import static me.fedox.viewerrun.utils.PlayerUtils.getMaxHealth;
import static me.fedox.viewerrun.utils.PlayerUtils.setMaxHealth;

public class PlayerDeathListener implements Listener {
    private final ViewerRun plugin;
    private final QueueWorker queueWorker;
    private final VRModel model;

    public PlayerDeathListener(ViewerRun plugin, QueueWorker queueWorker, VRModel model) {
        this.plugin = plugin;
        this.queueWorker = queueWorker;
        this.model = model;
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e) {
        var creator = model.getCreator();

        if(e.getPlayer().equals(model.getViewer())) {
            saveData(plugin, model);

            setMaxHealth(creator, getMaxHealth(creator) - 2);

            model.setViewer(null);
            e.getPlayer().getInventory().clear();

            queueWorker.restart();
            queueWorker.rotate();
        }

        if(e.getPlayer().equals(creator)) {
            model.setEventRunning(false);
        }
    }

    @EventHandler
    public void onRespawn(PlayerRespawnEvent e) {
        e.setRespawnLocation(getSpawnLocation(plugin));
    }
}
