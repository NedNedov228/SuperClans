package com.xecore.superclans.commands;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class ClansCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {

        if (!(commandSender instanceof Player)) {
            commandSender.sendMessage("§cYou must be a player to use this command.");

        return true;
        }

        final Player player = (Player) commandSender;

        player.teleport(new Location(player.getWorld(), 0f, 70f, 0f));
        return true;
    }
}
