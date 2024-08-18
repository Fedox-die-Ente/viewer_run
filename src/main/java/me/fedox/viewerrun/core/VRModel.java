package me.fedox.viewerrun.core;

import lombok.Getter;
import lombok.Setter;
import me.fedox.viewerrun.core.listener.ModelListener;
import me.fedox.viewerrun.core.listener.PlayerChangedListener;
import me.fedox.viewerrun.core.listener.RunningChangedListener;
import me.fedox.viewerrun.core.listener.TimeChangedListener;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

import static me.fedox.viewerrun.utils.PlayerUtils.setMaxHealth;

public class VRModel {
    @Getter
    private boolean eventRunning;
    private Player creator;
    private Player viewer;
    @Getter
    private long leftTime;
    @Getter
    @Setter
    private long currentTime = 0;

    private List<ModelListener> listeners = new ArrayList<>();

    /**
     * Sets the event running state and resets the current time and viewer.
     * Also resets the health and inventory of all online players.
     *
     * @param eventRunning the new event running state
     */
    public void setEventRunning(boolean eventRunning) {
        this.eventRunning = eventRunning;

        currentTime = 0;
        viewer = null;

        for(Player player : Bukkit.getOnlinePlayers()) {
            setMaxHealth(player, 20);
            player.setHealth(20);
            player.getInventory().clear();
            player.clearActivePotionEffects();
        }

        fireEventRunningChanged(eventRunning);
    }

    /**
     * Sets the creator player and notifies listeners.
     *
     * @param creator the new creator player
     */
    public void setCreator(Player creator) {
        this.creator = creator;
        fireCreatorChanged(creator);
    }

    /**
     * Gets the creator player.
     *
     * @return the creator player, or null if not set
     */
    @Nullable
    public Player getCreator() {
        return creator;
    }

    /**
     * Sets the viewer player and notifies listeners.
     *
     * @param viewer the new viewer player
     */
    public void setViewer(@Nullable Player viewer) {
        fireViewerChanged(viewer);
        this.viewer = viewer;
    }

    /**
     * Gets the viewer player.
     *
     * @return the viewer player, or null if not set
     */
    @Nullable
    public Player getViewer() {
        return viewer;
    }

    /**
     * Checks if a player is active (either the creator or the viewer).
     *
     * @param player the player to check
     * @return true if the player is active, false otherwise
     */
    public boolean isPlayerActive(Player player) {
        return player.equals(creator) || player.equals(viewer);
    }

    /**
     * Sets the left time and notifies listeners.
     *
     * @param leftTime the new left time
     */
    public void setLeftTime(long leftTime) {
        this.leftTime = leftTime;
        fireTimeChanged(leftTime);
    }

    /**
     * Adds a listener to the model.
     *
     * @param listener the listener to add
     */
    public void addListener(ModelListener listener) {
        listeners.add(listener);
    }

    /**
     * Notifies listeners that the event running state has changed.
     *
     * @param running the new event running state
     */
    private void fireEventRunningChanged(boolean running) {
        listeners.stream()
                .filter(RunningChangedListener.class::isInstance)
                .map(RunningChangedListener.class::cast)
                .forEach(l -> l.runningChanged(running));
    }

    /**
     * Notifies listeners that the creator has changed.
     *
     * @param creator the new creator player
     */
    private void fireCreatorChanged(Player creator) {
        listeners.stream()
                .filter(PlayerChangedListener.class::isInstance)
                .map(PlayerChangedListener.class::cast)
                .forEach(l -> l.onCreatorChanged(creator));
    }

    /**
     * Notifies listeners that the viewer has changed.
     *
     * @param viewer the new viewer player
     */
    private void fireViewerChanged(Player viewer) {
        listeners.stream()
                .filter(PlayerChangedListener.class::isInstance)
                .map(PlayerChangedListener.class::cast)
                .forEach(l -> l.onViewerChanged(this.viewer, viewer));
    }

    /**
     * Notifies listeners that the left time has changed.
     *
     * @param leftTime the new left time
     */
    private void fireTimeChanged(long leftTime) {
        listeners.stream()
                .filter(TimeChangedListener.class::isInstance)
                .map(TimeChangedListener.class::cast)
                .forEach(l -> l.onTimeChanged(leftTime));
    }
}