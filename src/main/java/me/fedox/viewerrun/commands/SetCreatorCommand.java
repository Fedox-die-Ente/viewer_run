package me.fedox.viewerrun.commands;

import me.fedox.viewerrun.ViewerRun;
import me.fedox.viewerrun.utils.Constants;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import static me.fedox.viewerrun.utils.Constants.CONFIG_CREATOR_UUID;

/**
 * Command executor for the set creator command in the ViewerRun plugin.
 */
public class SetCreatorCommand implements CommandExecutor {

    @SuppressWarnings("unused")
    private ViewerRun plugin;

    /**
     * Constructs a SetCreatorCommand with the specified ViewerRun plugin instance.
     *
     * @param plugin the ViewerRun plugin instance
     */
    public SetCreatorCommand(ViewerRun plugin) {
        this.plugin = plugin;
    }

    /**
     * Executes the set creator command.
     *
     * @param commandSender the sender of the command
     * @param command the command that was executed
     * @param s the alias of the command that was used
     * @param strings the arguments passed to the command
     * @return true if the command was successful, false otherwise
     */
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {

        if (!commandSender.hasPermission("viewerrun.setcreator")) {
            commandSender.sendMessage(Constants.PREFIX + "§cDu hast keine Rechte dazu.");
            return true;
        }

        if (strings.length != 1) {
            commandSender.sendMessage(Constants.PREFIX + "§cBitte gib einen Spieler an");
            return true;
        }

        String uuid = String.valueOf(commandSender.getServer().getPlayer(strings[0]).getUniqueId());
        ViewerRun.setCreatorUUID(uuid);

        if (ViewerRun.getCreatorUUID() == null) {
            commandSender.sendMessage(Constants.PREFIX + "§cDer Spieler konnte nicht gefunden werden");
            return true;
        }

        plugin.getConfig().set(CONFIG_CREATOR_UUID, commandSender.getServer().getPlayer(strings[0]).getUniqueId().toString());
        plugin.saveConfig();

        commandSender.sendMessage(Constants.PREFIX + "Der Creator wurde auf §a" + strings[0] + " §7gesetzt");

        return true;
    }
}