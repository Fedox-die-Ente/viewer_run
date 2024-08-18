package me.fedox.viewerrun.commands;

import me.fedox.viewerrun.ViewerRun;
import me.fedox.viewerrun.core.VRModel;
import me.fedox.viewerrun.utils.Constants;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import static me.fedox.viewerrun.core.handler.SaveHandler.loadData;

public class ContinueCommand implements CommandExecutor {
    private final ViewerRun plugin;
    private final VRModel model;

    public ContinueCommand(ViewerRun plugin, VRModel model) {
        this.plugin = plugin;
        this.model = model;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("viewerrun.start")) {
            sender.sendMessage(Constants.PREFIX + "§cDu hast keine Rechte dazu.");
            return false;
        }

        sender.sendMessage(Constants.PREFIX + "§7Das §aEvent wurde fortgesetzt!");
        model.setEventRunning(true);
        loadData(plugin, model);

        return true;
    }
}
