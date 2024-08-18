package me.fedox.viewerrun.commands;

import me.fedox.viewerrun.ViewerRun;
import me.fedox.viewerrun.core.Timer;
import me.fedox.viewerrun.core.VRModel;
import me.fedox.viewerrun.utils.Constants;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class StartCommand implements CommandExecutor {

    private final VRModel model;

    public StartCommand(VRModel model) {
        this.model = model;
    }

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
