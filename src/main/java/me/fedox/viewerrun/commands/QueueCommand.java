package me.fedox.viewerrun.commands;

import me.fedox.viewerrun.core.QueueWorker;
import me.fedox.viewerrun.utils.Constants;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

/**
 * Command executor for the queue command in the ViewerRun plugin.
 */
public class QueueCommand implements CommandExecutor {

    private final QueueWorker queueWorker;

    /**
     * Constructs a QueueCommand with the specified QueueWorker.
     *
     * @param queueWorker the QueueWorker instance
     */
    public QueueCommand(QueueWorker queueWorker) {
        this.queueWorker = queueWorker;
    }

    /**
     * Executes the queue command.
     *
     * @param commandSender the sender of the command
     * @param command the command that was executed
     * @param s the alias of the command that was used
     * @param args the arguments passed to the command
     * @return true if the command was successful, false otherwise
     */
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (!(commandSender instanceof Player player)) {
            commandSender.sendMessage("Nur Spieler dürfen diesen Befehl machen :(");
            return true;
        }

        if (args.length == 0) {
            if (queueWorker.getPlayers().contains(player)) {
                player.sendMessage(Constants.PREFIX + "§7Du bist §a§lbereits §7in der Warteschlange.");
            } else {
                queueWorker.addPlayer(player);
                player.sendMessage(Constants.PREFIX + "§7Du wurdest in die §a§lWarteschlange §7hinzugefügt.");
            }
        } else if (args[0].equalsIgnoreCase("list")) {
            StringBuilder message = new StringBuilder(Constants.PREFIX + "§7Spieler in der Warteschlange: \n");

            for (Player listedPlayer : queueWorker.getPlayers()) {
                message.append(Constants.PREFIX + "§a").append(listedPlayer.getName()).append("\n");
            }

            player.sendMessage(message.toString());
        } else {
            player.sendMessage(Constants.PREFIX + "§7Benutzung: §a/queue [list]");
        }

        return true;
    }

}