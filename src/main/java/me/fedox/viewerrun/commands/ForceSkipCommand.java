package me.fedox.viewerrun.commands;

import me.fedox.viewerrun.ViewerRun;
import me.fedox.viewerrun.core.QueueWorker;
import me.fedox.viewerrun.core.Timer;
import me.fedox.viewerrun.core.VRModel;
import me.fedox.viewerrun.core.handler.SaveHandler;
import me.fedox.viewerrun.utils.Constants;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import static me.fedox.viewerrun.core.handler.SaveHandler.saveData;

public class ForceSkipCommand implements CommandExecutor {

    private final ViewerRun plugin;
    private final QueueWorker worker;

    public ForceSkipCommand(ViewerRun plugin, QueueWorker worker) {
        this.plugin = plugin;
        this.worker = worker;
    }

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
