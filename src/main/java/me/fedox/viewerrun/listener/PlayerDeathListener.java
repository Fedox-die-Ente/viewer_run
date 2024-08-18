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

    /**
     * Constructor for PlayerDeathListener.
     *
     * @param plugin the ViewerRun plugin instance
     * @param queueWorker the QueueWorker instance used to manage the queue
     * @param model the VRModel instance used to manage player states
     */
    public PlayerDeathListener(ViewerRun plugin, QueueWorker queueWorker, VRModel model) {
        this.plugin = plugin;
        this.queueWorker = queueWorker;
        this.model = model;
    }

    /**
     * Event handler for when a player dies.
     *
     * @param e the PlayerDeathEvent
     */
    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e) {
        var creator = model.getCreator();

        // Check if the player who died is the viewer
        if(e.getPlayer().equals(model.getViewer())) {
            saveData(plugin, model);

            // Decrease the creator's max health by 2
            setMaxHealth(creator, getMaxHealth(creator) - 2);

            model.setViewer(null);
            e.getPlayer().getInventory().clear();

            queueWorker.restart();
            queueWorker.rotate();
        }

        // Check if the player who died is the creator
        if(e.getPlayer().equals(creator)) {
            model.setEventRunning(false);
        }
    }

    /**
     * Event handler for when a player respawns.
     *
     * @param e the PlayerRespawnEvent
     */
    @EventHandler
    public void onRespawn(PlayerRespawnEvent e) {
        e.setRespawnLocation(getSpawnLocation(plugin));
    }
}