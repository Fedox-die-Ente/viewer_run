package me.fedox.viewerrun.commands;

import me.fedox.viewerrun.ViewerRun;
import me.fedox.viewerrun.core.Timer;
import me.fedox.viewerrun.core.VRModel;
import me.fedox.viewerrun.core.handler.SaveHandler;
import me.fedox.viewerrun.utils.Constants;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import static me.fedox.viewerrun.core.handler.SaveHandler.saveData;

/**
 * Command executor for the pause timer command in the ViewerRun plugin.
 */
public class PauseTimerCommand implements CommandExecutor {

    private final ViewerRun plugin;
    private final VRModel model;

    /**
     * Constructs a PauseTimerCommand with the specified ViewerRun plugin and VRModel instance.
     *
     * @param plugin the ViewerRun plugin instance
     * @param model the VRModel instance
     */
    public PauseTimerCommand(ViewerRun plugin, VRModel model) {
        this.plugin = plugin;
        this.model = model;
    }

    /**
     * Executes the pause timer command.
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

        sender.sendMessage(Constants.PREFIX + "§7Das §aEvent wurde pausiert!");
        saveData(plugin, model);
        model.setEventRunning(false);

        return true;
    }

}