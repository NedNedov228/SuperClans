package com.xecore.superclans;

import com.xecore.superclans.commands.ClanCommand;
import com.xecore.superclans.commands.ClansCommand;
import com.xecore.superclans.config.Config;
import com.xecore.superclans.database.*;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.nio.file.Path;
import java.sql.Connection;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.scoreboard.*;

public final class SuperClans extends JavaPlugin implements Listener{
    public static Path clansDbFile;
    public static Connection conn;
    private Scoreboard globalScoreboard;

    public static final String DB_DRIVER = Config.getProperty("database.driver");

    @Override
    public void onEnable() {
        // Plugin startup logic
        try {
            Class.forName(DB_DRIVER);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        getLogger().info("SuperClans has been enabled");

        getServer().getPluginManager().registerEvents(this, this);
        setupGlobalScoreboard();

        // Register commands
        getCommand("clans").setExecutor(new ClansCommand());
        getCommand("clan").setExecutor(new ClanCommand(this));


        // Initialize tables
        DBInitializer.createTables();


    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        if (DBQuery.findMemberByUUID(player.getUniqueId().toString()) == null) {
            System.out.println("Player " + player.getName() + " is not in the database Adding them now");
            DBInsert.insertMember(player.getUniqueId().toString(), player.getName());
            System.out.println("Player " + player.getName() + " has been added to the database");
        }
        updatePlayerInScoreboard(player);
        player.setScoreboard(globalScoreboard);


    }

    private void setupGlobalScoreboard() {
        ScoreboardManager manager = Bukkit.getScoreboardManager();
        globalScoreboard = manager.getNewScoreboard();


    }

    public void updatePlayerInScoreboard(Player player) {
        String clan = getPlayerClan(player); // Implement this method to get the player's clan
        String prefix = "";
        if (clan != null )
            prefix = ChatColor.GREEN + "[" + ChatColor.RED + clan + ChatColor.GREEN + "] " + ChatColor.WHITE;

        Team team = globalScoreboard.getTeam(player.getName());

        if (team == null)
            team = globalScoreboard.registerNewTeam(player.getName());


        team.setPrefix(prefix);
        team.addEntry(player.getName());

    }

    public void updatePlayerInScoreboard(String playerName) {
        String clan = getPlayerClan(playerName); // Implement this method to get the player's clan
        String prefix = "";
        if (clan != null )
            prefix = ChatColor.GREEN + "[" + ChatColor.RED + clan + ChatColor.GREEN + "] " + ChatColor.WHITE;

        Team team = globalScoreboard.getTeam(playerName);

        if (team == null)
            team = globalScoreboard.registerNewTeam(playerName);


        team.setPrefix(prefix);
        team.addEntry(playerName);

    }

    public String getPlayerClan(Player player) {
        return DBQuery.findClanNameByMemberUUID(player.getUniqueId().toString());
    }
    public String getPlayerClan(String playerName) {
        return DBQuery.findClanNameByMemberName(playerName);
    }

}


