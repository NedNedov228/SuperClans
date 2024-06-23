package com.xecore.superclans;

import com.xecore.superclans.commands.ClansCommand;
import com.xecore.superclans.listeners.MyListener;
import io.papermc.paper.command.brigadier.Commands;
import org.bukkit.plugin.java.JavaPlugin;

public final class SuperClans extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        getLogger().info("SuperClans has been enabled");

        getServer().getPluginManager().registerEvents(new MyListener(), this);

        getCommand("clans").setExecutor(new ClansCommand());



    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
