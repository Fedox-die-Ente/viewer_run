package me.fedox.viewerrun.commands;

import me.fedox.viewerrun.ViewerRun;
import me.fedox.viewerrun.core.QueueWorker;
import me.fedox.viewerrun.utils.Constants;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

/**
 * Command executor for the force skip command in the ViewerRun plugin.
 */
public class ForceSkipCommand implements CommandExecutor {

    private final ViewerRun plugin;
    private final QueueWorker worker;

    /**
     * Constructs a ForceSkipCommand with the specified ViewerRun plugin and QueueWorker instance.
     *
     * @param plugin the ViewerRun plugin instance
     * @param worker the QueueWorker instance
     */
    public ForceSkipCommand(ViewerRun plugin, QueueWorker worker) {
        this.plugin = plugin;
        this.worker = worker;
    }

    /**
     * Executes the force skip command.
     *
     * @param sender the sender of the command
     * @param command the command that was executed
     * @param label the alias of the command that was used
     * @param args the arguments passed to the command
     * @return true if the command was successful, false otherwise
     */
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("viewerrun.start")) {
            sender.sendMessage(Constants.PREFIX + "§cDu hast keine Rechte dazu.");
            return false;
        }

        sender.sendMessage(Constants.PREFIX + "§7Der aktuelle §aSpieler §7wurde übersprungen");
        worker.rotate();

        return true;
    }

}