package net.azisaba.vanilife.core.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class Toto31010 implements Listener {
    @EventHandler
    public void onAsyncPlayerChat(AsyncPlayerChatEvent e) {
        if (e.getPlayer().getUniqueId().toString().equals("1865ab8c-700b-478b-9b52-a8c58739df1a")) {
            e.setMessage(e.getMessage() + "///");
        }
    }
}
