package com.xecore.superclans.listeners;


import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class MyListener implements Listener {
    @EventHandler
    public void onJoin(PlayerJoinEvent event) {

        final Player player = event.getPlayer();

        player.sendMessage("Hello, "+ player.getName()+"welcome to server and have fun!");

//        event.setJoinMessage(null);
    }
}
