package me.fedox.viewerrun.commands;

import me.fedox.viewerrun.ViewerRun;
import me.fedox.viewerrun.utils.Constants;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import static me.fedox.viewerrun.utils.Constants.CONFIG_LOCATION;
import static me.fedox.viewerrun.utils.LocationUtils.saveLocation;

public class SetSpawnCommand implements CommandExecutor {

    private final ViewerRun plugin;

    public SetSpawnCommand(ViewerRun plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {

        if (sender instanceof Player) {
            if (sender.hasPermission("viewerrun.setspawn")) {
                Player player = (Player) sender;
                Location location = player.getLocation();
                saveLocation(plugin, CONFIG_LOCATION, location);
                player.sendMessage(Constants.PREFIX + "§7Du hast den §a§lSpawn §7gesetzt");
            } else {
                sender.sendMessage(Constants.PREFIX + "§cDu hast keine Rechte dazu.");

            }
        }

        return false;
    }
}
