package me.fedox.viewerrun.core;

import lombok.Getter;
import me.fedox.viewerrun.ViewerRun;
import me.fedox.viewerrun.core.listener.RunningChangedListener;
import me.fedox.viewerrun.core.listener.TimeChangedListener;
import me.fedox.viewerrun.utils.TimeUtils;
import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;

import static me.fedox.viewerrun.utils.Constants.CONFIG_ROTATION_TIME;
import static me.fedox.viewerrun.utils.Constants.DEFAULT_QUEUE_ROTATION_TIME;
import static me.fedox.viewerrun.utils.TimeUtils.formatTime;

@Getter
public class Timer {
    private static BossBar bossBar;
    private static long rotationTime;

    /**
     * Constructor for Timer.
     *
     * @param plugin the ViewerRun plugin instance
     * @param model the VRModel instance used to manage the event state
     */
    public Timer(ViewerRun plugin, VRModel model) {
        var config = plugin.getConfig();
        rotationTime = config.getLong(CONFIG_ROTATION_TIME, DEFAULT_QUEUE_ROTATION_TIME);

        // Add a listener to update the time and boss bar
        model.addListener((TimeChangedListener) leftTime -> {
            var seconds = model.getCurrentTime() + 1;
            model.setCurrentTime(seconds);

            String formattedTime = formatTime(seconds);
            Bukkit.getOnlinePlayers().forEach(player -> player.sendActionBar("§8● §a" + formattedTime + " §8●"));

            updateBossBar(leftTime);
        });

        // Add a listener to remove the boss bar when the event is not running
        model.addListener((RunningChangedListener) running -> {
            if(!running && bossBar != null) {
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
    private void updateBossBar(long countdownSeconds) {
        if (bossBar == null) {
            bossBar = Bukkit.createBossBar("§8» §7Wechsel in §a" + countdownSeconds + "s §8«", BarColor.GREEN, BarStyle.SEGMENTED_10);
            Bukkit.getOnlinePlayers().forEach(bossBar::addPlayer);
        } else {
            bossBar.setTitle("§8» §7Wechsel in §a" + countdownSeconds + "s §8«");
            bossBar.setProgress((double) countdownSeconds / rotationTime);
        }
    }
}