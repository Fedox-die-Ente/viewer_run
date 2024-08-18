package me.fedox.viewerrun.core.listener;

import org.bukkit.entity.Player;

/**
 * Interface for listening to changes in the creator and viewer players of the VRModel.
 */
public interface PlayerChangedListener extends ModelListener {
    /**
     * Called when the creator player changes.
     *
     * @param creator the new creator player
     */
    void onCreatorChanged(Player creator);

    /**
     * Called when the viewer player changes.
     *
     * @param lastViewer the previous viewer player
     * @param viewer the new viewer player
     */
    void onViewerChanged(Player lastViewer, Player viewer);
}