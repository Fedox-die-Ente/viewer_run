package me.fedox.viewerrun.listener;

import com.destroystokyo.paper.Title;
import me.fedox.viewerrun.core.Timer;
import me.fedox.viewerrun.core.VRModel;
import me.fedox.viewerrun.utils.TimeUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.EnderDragon;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

import static me.fedox.viewerrun.utils.Constants.PREFIX;
import static me.fedox.viewerrun.utils.TimeUtils.formatTime;

public class EnderDragonShitListener implements Listener {
    private final VRModel model;

    public EnderDragonShitListener(VRModel model) {
        this.model = model;
    }

    @EventHandler
    public void onEnderDragonShit(EntityDeathEvent e) {
        if(e.getEntity() instanceof EnderDragon) {
            Title title = new Title("§f§k!!! §a§l§nBEENDET§r §f§k!!!", "§7Der Enderdrache wurde besiegt.", 10, 40, 10);
            Bukkit.getOnlinePlayers().forEach(player -> {
                player.sendTitle(title);
            });

            if (e.getEntity().getKiller() != null) {
                Bukkit.broadcastMessage(PREFIX + "§7Der Enderdrache wurde von §a" + e.getEntity().getKiller().getName() + " §7besiegt.");
            }

            Bukkit.broadcastMessage(PREFIX + "§7Das §aEvent §7wurde beendet in folgender Zeit: §a" + formatTime(model.getCurrentTime()));

            model.setEventRunning(false);
        }
    }

}
