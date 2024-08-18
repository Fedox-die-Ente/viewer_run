package me.fedox.viewerrun.commands;

import me.fedox.viewerrun.ViewerRun;
import me.fedox.viewerrun.core.Timer;
import me.fedox.viewerrun.core.VRModel;
import me.fedox.viewerrun.utils.Constants;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TPCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("viewerrun.teleport")) {
            sender.sendMessage(Constants.PREFIX + "§cDu hast keine Rechte dazu.");
            return false;
        }

        Player player = (Player) sender;

        if (args.length == 0) {
            sender.sendMessage(Constants.PREFIX + "§cBitte gib einen Spieler an.");
            return false;
        }

        var target = args[0];

        Player targetPlayer = player.getServer().getPlayer(target);

        if (targetPlayer == null) {
            sender.sendMessage(Constants.PREFIX + "§cDieser Spieler ist nicht online.");
            return false;
        }

        player.teleport(targetPlayer);
        sender.sendMessage(Constants.PREFIX + "§7Teleportiere zu §a" + targetPlayer.getName());

        return true;
    }

}
