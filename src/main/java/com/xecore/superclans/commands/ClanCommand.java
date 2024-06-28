package com.xecore.superclans.commands;

import com.xecore.superclans.SuperClans;
import com.xecore.superclans.database.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import static org.bukkit.Bukkit.getOnlinePlayers;


public class ClanCommand implements CommandExecutor, TabCompleter {

    private final SuperClans plugin;

    public ClanCommand(SuperClans plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (!(commandSender instanceof Player)) {
            commandSender.sendMessage("§cYou must be a player to use this command.");
            return true;
        }

        Player player = (Player) commandSender;
        String playerName = player.getName();
        String playerUUID = player.getUniqueId().toString();

        if (strings.length == 0) {
            System.out.println(DBQuery.findClanIdByMemberUUID(playerUUID));
            int clanId = DBQuery.findClanIdByMemberUUID(playerUUID);
            if(clanId == -1||
                    clanId == 0){
                commandSender.sendMessage("§cYou are not in a clan");
                return true;
            }

            try{
                var clanName = DBQuery.findClanNameByMemberUUID(playerUUID);

                if (clanName != null) {
                    commandSender.sendMessage(clanName);

                }
            }catch (NullPointerException e){
                commandSender.sendMessage("§cYou are not in a clan");
            }
            return true;
        }
        else if (strings.length  == 2){
            if (strings[0].equalsIgnoreCase("create")) {
                if (DBQuery.findClanIdByMemberUUID(playerUUID) != -1) {
                    commandSender.sendMessage("§cYou are already in a clan");
                    return true;
                }
                if (!isClanNameValid(strings[1])) {
                    commandSender.sendMessage("§cClan name must be between 3 and 16 characters");
                    return true;
                }
                if(DBQuery.findClanIdByName(strings[1]) != -1){
                    commandSender.sendMessage("§cClan name is already taken");
                    return true;
                }
                var msg = DBInsert.insertClan( strings[1], playerUUID);
                DBInsert.insertClanMember(playerUUID, DBQuery.findClanIdByName(strings[1]));
                plugin.updatePlayerInScoreboard(player);
                commandSender.sendMessage(msg);

                return true;
            }
            else if (strings[0].equalsIgnoreCase("add")) {

                //check if player is in clan
                if(DBQuery.findClanIdByMemberUUID(playerUUID) == -1){
                    commandSender.sendMessage("§cYou are not in a clan");
                    return true;
                }
                // check if the player is owner
                if (DBQuery.findClanOwnerByMemberUUID(playerUUID).equals(null)) {
                    commandSender.sendMessage("§cYou are not the owner of the clan");
                    return true;
                }
                // check if the player is in the clan
                if (DBQuery.findClanIdByMemberName(strings[1]) != -1 ) {
                    commandSender.sendMessage("§cPlayer is already in the clan");
                    return true;
                }

                DBInsert.insertClanMember(DBQuery.findMemberUUIDByName(strings[1]), DBQuery.findClanIdByMemberUUID(playerUUID));
                plugin.updatePlayerInScoreboard(strings[1]);
                commandSender.sendMessage("Player " + strings[1] + " has been added to the clan");
                return true;
            }
            else if (strings[0].equalsIgnoreCase("kick")) {
                if(DBQuery.findClanIdByMemberUUID(playerUUID) == -1){
                    commandSender.sendMessage("§cYou are not in a clan");
                    return true;
                }
                if (!DBQuery.findClanOwnerByMemberUUID(playerUUID).equals(playerUUID)) {
                    commandSender.sendMessage("§cYou are not the owner of the clan");
                    return true;
                }
                if (strings[1].equals(playerName)) {
                    commandSender.sendMessage("§You cannot kick yourself from the clan");
                    return true;
                }

                if (DBQuery.findClanIdByMemberName(strings[1])
                        != DBQuery.findClanIdByMemberUUID(playerUUID)) {

                    commandSender.sendMessage("§cPlayer is not in the clan");
                    return true;
                }
                plugin.getLogger()
                        .info("Player " + playerUUID + " kicks "+ DBQuery.findMemberUUIDByName(strings[1]) +" from the clan");
                DBUpdate.deleteMemberFromClan(DBQuery.findMemberUUIDByName(strings[1]),DBQuery.findClanIdByMemberUUID(playerUUID));
                plugin.updatePlayerInScoreboard(strings[1]);
                commandSender.sendMessage("Player " + strings[1] + " has been kicked from the clan");
                return true;
            }
        }  else if (strings.length == 1) {
            if (strings[0].equalsIgnoreCase("leave")) {

                if(DBQuery.findClanIdByMemberUUID(playerUUID) == -1){
                    commandSender.sendMessage("§cYou are not in a clan");
                    return true;
                }
                if (DBQuery.findClanOwnerByMemberUUID(playerUUID).equals(playerUUID)) {
                    commandSender.sendMessage("§cYou are the owner of the clan so you cannot leave it");
                    return true;
                }
                DBUpdate.deleteMemberFromClan(playerUUID, DBQuery.findClanIdByMemberUUID(playerUUID));
                plugin.updatePlayerInScoreboard(player);
                commandSender.sendMessage("You left the clan");
                return true;
            }
            else if (strings[0].equalsIgnoreCase("disband")) {
                var clanId = DBQuery.findClanIdByMemberUUID(playerUUID);
                if(DBQuery.findClanIdByMemberUUID(playerUUID) == -1){
                    commandSender.sendMessage("§cYou are not in a clan");
                    return true;
                }
                if (!DBQuery.findClanOwnerByMemberUUID(playerUUID).equals(playerUUID)) {
                    commandSender.sendMessage("§cYou are not the owner of the clan");
                    return true;
                }

                var clanName = DBQuery.findClanNameById(clanId);
                var clanMembers =  DBQuery.findMemberNames(clanId);
                DBUpdate.deleteClan(clanId);
                for (var member : clanMembers) {
                    plugin.updatePlayerInScoreboard(member);
                }
                plugin.updatePlayerInScoreboard(player);
                commandSender.sendMessage("Your clan " + clanName + " has been disbanded");

                return true;
            }
        }
        commandSender.sendMessage("§cUsage: /clan help");
        return true;
    }

    private boolean isClanNameValid(String clanName) {
        return clanName.length() <= 16 && clanName.length() >= 3;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (strings.length == 1) {
            return List.of("create", "add", "kick", "leave", "disband","help");
        }
        else if (strings.length == 2) {
            if (strings[0].equalsIgnoreCase("add") || strings[0].equalsIgnoreCase("kick")) {
                return getOnlinePlayers().stream().map(Player::getName).toList();
            }
        }
        return List.of();
    }
}
