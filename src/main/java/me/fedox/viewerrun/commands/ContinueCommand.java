package me.fedox.viewerrun.commands;

import me.fedox.viewerrun.ViewerRun;
import me.fedox.viewerrun.core.VRModel;
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
            sender.sendMessage("§cDu hast keine Rechte");
            return false;
        }

        sender.sendMessage("§aDer ViewerRun wird fortgeführt");
        model.setEventRunning(true);
        loadData(plugin, model);

        return true;
    }
}
