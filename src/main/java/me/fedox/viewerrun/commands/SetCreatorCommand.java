package me.fedox.viewerrun.commands;

import me.fedox.viewerrun.ViewerRun;
import me.fedox.viewerrun.utils.Constants;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import static me.fedox.viewerrun.utils.Constants.CONFIG_CREATOR_UUID;

public class SetCreatorCommand implements CommandExecutor {

    @SuppressWarnings("unused")
    private ViewerRun plugin;

    public SetCreatorCommand(ViewerRun plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {

        if (!commandSender.hasPermission("viewerrun.setcreator")) {
            commandSender.sendMessage("§cDu hast keine Rechte");
            return true;
        }

        if (strings.length != 1) {
            commandSender.sendMessage("§cBenutzung: /setcreator <Spieler>");
            return true;
        }

        String uuid = String.valueOf(commandSender.getServer().getPlayer(strings[0]).getUniqueId());
        ViewerRun.setCreatorUUID(uuid);

        if (ViewerRun.getCreatorUUID() == null) {
            commandSender.sendMessage("§cDer Spieler ist nicht online");
            return true;
        }

        plugin.getConfig().set(CONFIG_CREATOR_UUID, commandSender.getServer().getPlayer(strings[0]).getUniqueId().toString());
        plugin.saveConfig();

        commandSender.sendMessage(Constants.PREFIX + "Der Creator wurde auf §a" + strings[0] + " §7gesetzt");

        return true;
    }
}
