package me.fedox.viewerrun.core;

import lombok.Getter;
import me.fedox.viewerrun.ViewerRun;
import me.fedox.viewerrun.core.listener.RunningChangedListener;
import me.fedox.viewerrun.core.listener.TimeChangedListener;
import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import static java.util.Objects.nonNull;
import static me.fedox.viewerrun.utils.Constants.CONFIG_ROTATION_TIME;
import static me.fedox.viewerrun.utils.Constants.DEFAULT_QUEUE_ROTATION_TIME;
import static me.fedox.viewerrun.utils.TimeUtils.formatTime;

@Getter
public class Timer implements Listener {
    private static BossBar bossBar;
    private static long    rotationTime;

    /**
     * Constructor for Timer.
     *
     * @param plugin the ViewerRun plugin instance
     * @param model  the VRModel instance used to manage the event state
     */
    public Timer(final ViewerRun plugin, final VRModel model) {
        final var config = plugin.getConfig();
        rotationTime = config.getLong(CONFIG_ROTATION_TIME, DEFAULT_QUEUE_ROTATION_TIME);

        // Add a listener to update the time and boss bar
        model.addListener((TimeChangedListener) leftTime -> {
            final var seconds = model.getCurrentTime() + 1;
            model.setCurrentTime(seconds);

            final String formattedTime = formatTime(seconds);
            Bukkit.getOnlinePlayers().forEach(player -> player.sendActionBar("§8● §a" + formattedTime + " §8●"));

            updateBossBar(leftTime);
        });

        // Add a listener to remove the boss bar when the event is not running
        model.addListener((RunningChangedListener) running -> {
            if (!running && bossBar != null) {
                bossBar.removeAll();
                bossBar = null;
            }
        });
    }

    /**
     * Updates the boss bar with the remaining countdown time.
     *
     * @param countdownSeconds the remaining countdown time in seconds
     */
    private void updateBossBar(final long countdownSeconds) {
        if (bossBar == null) {
            bossBar = Bukkit.createBossBar("§8» §7Wechsel in §a" + countdownSeconds + "s §8«", BarColor.GREEN, BarStyle.SEGMENTED_10);
            Bukkit.getOnlinePlayers().forEach(bossBar::addPlayer);
        } else {
            bossBar.setTitle("§8» §7Wechsel in §a" + countdownSeconds + "s §8«");
            bossBar.setProgress((double) countdownSeconds / rotationTime);
        }
    }

    @EventHandler
    public void onJoin(final PlayerJoinEvent e) {
        if (nonNull(bossBar)) {
            bossBar.addPlayer(e.getPlayer());
        }
    }

    @EventHandler
    public void onQuit(final PlayerQuitEvent e) {
        if (nonNull(bossBar)) {
            bossBar.removePlayer(e.getPlayer());
        }
    }
}