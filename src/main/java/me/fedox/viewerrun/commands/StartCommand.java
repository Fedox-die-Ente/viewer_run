package me.fedox.viewerrun.commands;

import me.fedox.viewerrun.ViewerRun;
import me.fedox.viewerrun.core.Timer;
import me.fedox.viewerrun.core.VRModel;
import me.fedox.viewerrun.utils.Constants;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

/**
 * Command executor for the start command in the ViewerRun plugin.
 */
public class StartCommand implements CommandExecutor {

    private final VRModel model;

    /**
     * Constructs a StartCommand with the specified VRModel.
     *
     * @param model the VRModel instance
     */
    public StartCommand(VRModel model) {
        this.model = model;
    }

    /**
     * Executes the start command.
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

        model.setEventRunning(true);

        sender.sendMessage(Constants.PREFIX + "§7Das §aEvent wurde gestartet!");

        return true;
    }

}