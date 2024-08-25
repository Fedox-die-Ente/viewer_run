package me.fedox.viewerrun.commands;

import me.fedox.viewerrun.ViewerRun;
import me.fedox.viewerrun.core.VRModel;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import static me.fedox.viewerrun.utils.Constants.CONFIG_CREATOR_UUID;
import static me.fedox.viewerrun.utils.Constants.PREFIX;

/**
 * Command executor for the set creator command in the ViewerRun plugin.
 */
public class SetCreatorCommand implements CommandExecutor {
    @SuppressWarnings("unused")
    private final ViewerRun plugin;
    private final VRModel   model;

    /**
     * Constructs a SetCreatorCommand with the specified ViewerRun plugin instance.
     *
     * @param plugin the ViewerRun plugin instance
     */
    public SetCreatorCommand(final ViewerRun plugin, final VRModel model) {
        this.plugin = plugin;
        this.model = model;
    }

    /**
     * Executes the set creator command.
     *
     * @param commandSender the sender of the command
     * @param command       the command that was executed
     * @param s             the alias of the command that was used
     * @param strings       the arguments passed to the command
     * @return true if the command was successful, false otherwise
     */
    @Override
    public boolean onCommand(@NotNull final CommandSender commandSender, @NotNull final Command command, @NotNull final String s, @NotNull final String[] strings) {

        if (!commandSender.hasPermission("viewerrun.setcreator")) {
            commandSender.sendMessage(PREFIX + "§cDu hast keine Rechte dazu.");
            return true;
        }

        if (strings.length != 1) {
            commandSender.sendMessage(PREFIX + "§cBitte gib einen Spieler an");
            return true;
        }

        final var name = strings[0];

        final OfflinePlayer foundPlayer = Bukkit.getOfflinePlayer(name);
        final var uuid = foundPlayer.getUniqueId().toString();
        if (foundPlayer.isOnline()) {
            model.setCreator(foundPlayer.getPlayer());
        }

        ViewerRun.setCreatorUUID(uuid);

        plugin.getConfig().set(CONFIG_CREATOR_UUID, uuid);
        plugin.saveConfig();

        commandSender.sendMessage(PREFIX + "Der Creator wurde auf §a" + name + " §7gesetzt");

        return true;
    }

    /**
     * Checks if the given string is a valid UUID.
     *
     * @param uuid the string to check
     * @return true if the string is a valid UUID, false otherwise
     */
    private boolean isUUID(final String uuid) {
        return uuid.matches("[a-f0-9]{8}-[a-f0-9]{4}-[a-f0-9]{4}-[a-f0-9]{4}-[a-f0-9]{12}");
    }
}