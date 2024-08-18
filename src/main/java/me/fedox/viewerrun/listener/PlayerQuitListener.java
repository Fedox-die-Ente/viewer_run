package me.fedox.viewerrun.listener;

import me.fedox.viewerrun.core.QueueWorker;
import me.fedox.viewerrun.core.VRModel;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import static me.fedox.viewerrun.utils.Constants.PREFIX;

public class PlayerQuitListener implements Listener {
    private final QueueWorker queueWorker;
    private final VRModel model;

    /**
     * Constructor for PlayerQuitListener.
     *
     * @param queueWorker the QueueWorker instance used to manage the queue
     * @param model the VRModel instance used to manage player states
     */
    public PlayerQuitListener(QueueWorker queueWorker, VRModel model) {
        this.queueWorker = queueWorker;
        this.model = model;
    }

    /**
     * Event handler for when a player quits the server.
     *
     * @param e the PlayerQuitEvent
     */
    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e) {
        e.setQuitMessage("§8[§c-§8] §7" + e.getPlayer().getName());

        queueWorker.removePlayer(e.getPlayer());
        if(e.getPlayer() == model.getViewer()) {
            queueWorker.restart();
            queueWorker.rotate();
        }
    }
}