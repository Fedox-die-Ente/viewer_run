package me.fedox.viewerrun.commands;

import me.fedox.viewerrun.ViewerRun;
import me.fedox.viewerrun.core.Timer;
import me.fedox.viewerrun.core.VRModel;
import me.fedox.viewerrun.core.handler.SaveHandler;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import static me.fedox.viewerrun.core.handler.SaveHandler.saveData;

public class PauseTimerCommand implements CommandExecutor {

    private final ViewerRun plugin;
    private final VRModel model;

    public PauseTimerCommand(ViewerRun plugin, VRModel model) {
        this.plugin = plugin;
        this.model = model;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("viewerrun.start")) {
            sender.sendMessage("§cDu hast keine Rechte");
            return false;
        }

        // plugin.getQueueWorker().pause();
//        Timer.pause();

        sender.sendMessage("§aDer ViewerRun wurde pausiert");
        saveData(plugin, model);
        model.setEventRunning(false);

        return true;
    }

}
