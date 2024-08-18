package me.fedox.viewerrun.core.listener;

import org.bukkit.entity.Player;

public interface PlayerChangedListener extends ModelListener {
    void onCreatorChanged(Player creator);
    void onViewerChanged(Player lastViewer, Player viewer);
}
