package me.fedox.viewerrun.core;

import me.fedox.viewerrun.ViewerRun;
import me.fedox.viewerrun.core.listener.RunningChangedListener;
import me.fedox.viewerrun.utils.Constants;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static java.util.Collections.unmodifiableList;
import static me.fedox.viewerrun.utils.Constants.CONFIG_ROTATION_TIME;
import static me.fedox.viewerrun.utils.Constants.DEFAULT_QUEUE_ROTATION_TIME;
import static me.fedox.viewerrun.utils.DebugUtils.debug;

public class QueueWorker {
    private final ViewerRun plugin;
    private final VRModel model;
    private final List<Player> players = new ArrayList<>();
    private final List<Player> alreadySelectedPlayers = new ArrayList<>();

    private final long rotationTime;

    private boolean running;
    private int taskId;

    public QueueWorker(ViewerRun plugin, VRModel model) {
        this.plugin = plugin;
        this.model = model;

        var config = plugin.getConfig();
        rotationTime = config.getLong(CONFIG_ROTATION_TIME, DEFAULT_QUEUE_ROTATION_TIME);

        model.addListener((RunningChangedListener) newState -> {
            if(newState) {
                start();
                rotate();
            } else {
                stop();
            }
        });
    }

    /**
     * Start the queue rotation scheduler
     * Runs every {@link Constants#DEFAULT_QUEUE_ROTATION_TIME} ticks
     */
    public void start() {
        running = true;

        AtomicInteger currentSecond = new AtomicInteger(0);

        taskId = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, () -> {
            model.setLeftTime(rotationTime - currentSecond.incrementAndGet());
            if(currentSecond.get() >= rotationTime) {
                currentSecond.set(0);
                this.rotate();
            }
        }, 0, 20);
    }

    /**
     * Restart the queue rotation scheduler
     */
    public void restart() {
        stop();
        start();
    }

    /**
     * Stop the queue rotation scheduler
     */
    public void stop() {
        if(!running) {
            return;
        }

        running = false;
        Bukkit.getScheduler().cancelTask(taskId);
    }

    /**
     * Get the players in the queue
     * @return list of players
     */
    public List<Player> getPlayers() {
        return unmodifiableList(players);
    }

    /**
     * Add a player to the queue
     * @param player the player to add to the queue
     */
    public void addPlayer(Player player) {
        players.add(player);
    }

    /**
     * Remove a player from the queue
     * @param player the player to remove from the queue
     */
    public void removePlayer(Player player) {
        players.remove(player);

        if(player == model.getViewer()) {
            rotate();
        }
    }

    /**
     * Selects a new player from the queue
     */
    public void rotate() {
        debug("Neuer Spieler wird ausgewählt..");

        var cPlayers = new ArrayList<>(players.stream()
                        .filter(p -> !alreadySelectedPlayers.contains(p))
                        .toList());

        if(cPlayers.isEmpty()) {
            alreadySelectedPlayers.clear();
            cPlayers = new ArrayList<>(players.stream()
                    .filter(p -> !p.equals(model.getViewer()))
                    .toList());
        }

        Collections.shuffle(cPlayers);

        if(cPlayers.isEmpty()) {
            debug("Keine Spieler in der Warteschlange.");
            return;
        }

        final var player = cPlayers.getFirst();
        model.setViewer(player);

        debug(player.getName() + " wurde ausgewählt.");

        // Make sure the player is not selected again
        alreadySelectedPlayers.add(player);
    }
}
