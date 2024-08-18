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

    public void setCreator(Player creator) {
        this.creator = creator;
        fireCreatorChanged(creator);
    }

    @Nullable
    public Player getCreator() {
        return creator;
    }

    public void setViewer(@Nullable Player viewer) {
        fireViewerChanged(viewer);
        this.viewer = viewer;
    }

    @Nullable
    public Player getViewer() {
        return viewer;
    }

    public boolean isPlayerActive(Player player) {
        return player.equals(creator) || player.equals(viewer);
    }

    public void setLeftTime(long leftTime) {
        this.leftTime = leftTime;
        fireTimeChanged(leftTime);
    }

    public void addListener(ModelListener listener) {
        listeners.add(listener);
    }

    private void fireEventRunningChanged(boolean running) {
        listeners.stream()
                .filter(RunningChangedListener.class::isInstance)
                .map(RunningChangedListener.class::cast)
                .forEach(l -> l.runningChanged(running));
    }

    private void fireCreatorChanged(Player creator) {
        listeners.stream()
                .filter(PlayerChangedListener.class::isInstance)
                .map(PlayerChangedListener.class::cast)
                .forEach(l -> l.onCreatorChanged(creator));
    }

    private void fireViewerChanged(Player viewer) {
        listeners.stream()
                .filter(PlayerChangedListener.class::isInstance)
                .map(PlayerChangedListener.class::cast)
                .forEach(l -> l.onViewerChanged(this.viewer, viewer));
    }

    private void fireTimeChanged(long leftTime) {
        listeners.stream()
                .filter(TimeChangedListener.class::isInstance)
                .map(TimeChangedListener.class::cast)
                .forEach(l -> l.onTimeChanged(leftTime));
    }
}
