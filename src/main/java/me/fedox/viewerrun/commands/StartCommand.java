package me.fedox.viewerrun.commands;

import me.fedox.viewerrun.ViewerRun;
import me.fedox.viewerrun.core.Timer;
import me.fedox.viewerrun.core.VRModel;
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
            sender.sendMessage("§cDu hast keine Rechte");
            return false;
        }

        model.setEventRunning(true);

        sender.sendMessage("§aDer ViewerRun wurde gestartet");

        return true;
    }

}
