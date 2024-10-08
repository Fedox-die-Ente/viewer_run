package me.fedox.viewerrun.commands;

import me.fedox.viewerrun.types.EGamemodes;
import me.fedox.viewerrun.utils.Constants;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Command executor for the GM (GameMode) command in the ViewerRun plugin.
 */
public class GMCommand implements CommandExecutor {

    /**
     * Executes the GM command.
     *
     * @param sender the sender of the command
     * @param command the command that was executed
     * @param label the alias of the command that was used
     * @param args the arguments passed to the command
     * @return true if the command was successful, false otherwise
     */
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("viewerrun.gamemode")) {
            sender.sendMessage(Constants.PREFIX + "§cDu hast keine Rechte dazu.");
            return false;
        }

        Player player = (Player) sender;

        if (args.length == 0) {
            sender.sendMessage(Constants.PREFIX + "§cBitte gib ein Spielmodi an.");
            return false;
        }

        var gameMode = args[0];
        int gameModeInt = Integer.parseInt(gameMode);
        if (EGamemodes.isGamemode(gameModeInt)) {

            GameMode gamemode = EGamemodes.getFromId(gameModeInt);

            player.setGameMode(gamemode);
            player.sendMessage(Constants.PREFIX + "§7Spielmodus wurde auf §a" + gamemode.name() + " §7gesetzt.");

            return true;
        } else {
            sender.sendMessage(Constants.PREFIX + "§cDieser Spielmodus existiert nicht");
        }

        return true;
    }

}