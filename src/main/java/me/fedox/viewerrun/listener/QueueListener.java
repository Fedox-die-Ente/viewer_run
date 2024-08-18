package me.fedox.viewerrun.listener;

import me.fedox.viewerrun.ViewerRun;
import me.fedox.viewerrun.core.VRModel;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityInteractEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

import static me.fedox.viewerrun.utils.LocationUtils.getSpawnLocation;
import static org.bukkit.GameMode.CREATIVE;

public class QueueListener implements Listener {
    private final VRModel model;

    public QueueListener(VRModel model) {
        this.model = model;
    }

    @EventHandler
    public void onDamage(EntityDamageEvent e) {
        if(e.getEntity() instanceof Player player) {
            if(model.isPlayerActive(player)) {
                return;
            }

            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onDamageByEntity(EntityDamageByEntityEvent e) {
        if(e.getDamager() instanceof Player player) {
            if(model.isPlayerActive(player)) {
                return;
            }

            e.setCancelled(true);
        }

    }

    @EventHandler
    public void onDamageByEntityFromFedox(EntityDamageByEntityEvent e) {
       if (e.getEntity().equals(model.getCreator()) && e.getDamager() instanceof Player) {
           e.setCancelled(true);
       }
    }


    @EventHandler
    public void onFoodChanged(FoodLevelChangeEvent e) {
        if(e.getFoodLevel() < 20) {
            if(model.isPlayerActive((Player) e.getEntity())) {
                return;
            }

            e.setFoodLevel(20);
        }
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e) {
        if(e.getPlayer().getGameMode().equals(CREATIVE)) {
            return;
        }

        if(model.isPlayerActive(e.getPlayer())) {
            return;
        }

        e.setCancelled(true);
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
        if(e.getPlayer().getGameMode().equals(CREATIVE)) {
            return;
        }

        if(model.isPlayerActive(e.getPlayer())) {
            return;
        }

        e.setCancelled(true);
    }

    @EventHandler
    public void onInteract(EntityInteractEvent e) {
        if(e.getEntity() instanceof Player player) {
            if (player.getGameMode().equals(CREATIVE)) {
                return;
            }

            if (model.isPlayerActive(player)) {
                return;
            }

            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onRespawn(PlayerRespawnEvent e) {
        e.setRespawnLocation(getSpawnLocation(ViewerRun.getInstance()));
    }
}
