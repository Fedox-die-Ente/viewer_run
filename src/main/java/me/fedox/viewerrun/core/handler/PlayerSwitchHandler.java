package me.fedox.viewerrun.core.handler;

import me.fedox.viewerrun.ViewerRun;
import me.fedox.viewerrun.core.VRModel;
import me.fedox.viewerrun.core.listener.PlayerChangedListener;
import org.bukkit.entity.Player;

import java.util.ArrayList;

import static java.util.Objects.nonNull;
import static me.fedox.viewerrun.core.handler.CacheHandler.getViewerCache;
import static me.fedox.viewerrun.core.handler.CacheHandler.setViewerCache;
import static me.fedox.viewerrun.utils.DebugUtils.debug;
import static me.fedox.viewerrun.utils.LocationUtils.teleportToSpawn;
import static me.fedox.viewerrun.utils.PlayerUtils.getMaxHealth;
import static me.fedox.viewerrun.utils.PlayerUtils.setMaxHealth;

/**
 * Handles the switching of players in the VRModel.
 */
public class PlayerSwitchHandler {

    /**
     * Constructs a PlayerSwitchHandler and registers a PlayerChangedListener to the VRModel.
     *
     * @param plugin the ViewerRun plugin instance
     * @param model the VRModel instance
     */
    public PlayerSwitchHandler(ViewerRun plugin, VRModel model) {
        model.addListener(new PlayerChangedListener() {
            @Override
            public void onCreatorChanged(Player creator) {
                // No implementation needed for creator change
            }

            @Override
            public void onViewerChanged(Player lastViewer, Player viewer) {
                if(nonNull(lastViewer)) {
                    if(viewer == lastViewer) {
                        return;
                    }

                    if(nonNull(viewer)) {
                        syncInventory(lastViewer, viewer);
                        syncStats(lastViewer, viewer);
                        syncPotionEffects(lastViewer, viewer);

                        viewer.teleport(lastViewer);
                    }

                    teleportToSpawn(plugin, lastViewer);
                } else {
                    if(nonNull(model.getCreator())) {
                        var viewerCache = getViewerCache();
                        if(nonNull(viewerCache)) {
                            debug("Use cached viewer data.");

                            viewer.getInventory().setContents(viewerCache.items());
                            viewer.setHealth(viewerCache.health());
                            setMaxHealth(viewer, viewerCache.maxHealth());
                            viewer.setSaturation(viewerCache.food());
                            viewer.teleport(viewerCache.location());

                            setViewerCache(null);
                        } else {
                            viewer.teleport(model.getCreator().getLocation());
                        }
                    }
                }
            }
        });
    }

    /**
     * Synchronizes the inventory between the last viewer and the new viewer.
     *
     * @param lastViewer the previous viewer player
     * @param viewer the new viewer player
     */
    private void syncInventory(Player lastViewer, Player viewer) {
        viewer.getInventory().clear();

        final var lastViewerInv = lastViewer.getInventory();
        final var viewerInv = viewer.getInventory();

        viewerInv.setContents(lastViewerInv.getContents());

        lastViewer.getInventory().clear();

        debug("Switched inventory between " + lastViewer.getName() + " and " + viewer.getName() + " (new viewer)");
    }

    /**
     * Synchronizes the stats (health and food level) between the last viewer and the new viewer.
     *
     * @param lastViewer the previous viewer player
     * @param viewer the new viewer player
     */
    private void syncStats(Player lastViewer, Player viewer) {
        viewer.setFoodLevel(lastViewer.getFoodLevel());
        setMaxHealth(viewer, getMaxHealth(lastViewer));
        viewer.setHealth(lastViewer.getHealth());

        lastViewer.setFoodLevel(20);
        setMaxHealth(lastViewer, 20);
        lastViewer.setHealth(20);

        debug("Switched health between " + lastViewer.getName() + " and " + viewer.getName() + " (new viewer)");
    }

    /**
     * Synchronizes the potion effects between the last viewer and the new viewer.
     *
     * @param lastViewer the previous viewer player
     * @param viewer the new viewer player
     */
    private void syncPotionEffects(Player lastViewer, Player viewer) {
        new ArrayList<>(lastViewer.getActivePotionEffects()).forEach(viewer::addPotionEffect);
        lastViewer.clearActivePotionEffects();

        debug("Switched potion effects between " + lastViewer.getName() + " and " + viewer.getName() + " (new viewer)");
    }
}