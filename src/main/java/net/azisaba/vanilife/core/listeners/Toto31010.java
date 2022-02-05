package net.azisaba.vanilife.core.listeners;

import net.azisaba.vanilife.core.VanilifeCore;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class Toto31010 implements Listener {
    private final VanilifeCore plugin;

    public Toto31010(VanilifeCore plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onAsyncPlayerChat(AsyncPlayerChatEvent e) {
        if (plugin.getConfig().getBoolean("no-toto31010", true)) return;
        if (plugin.getConfig().getBoolean("toto31010", false) || e.getPlayer().getUniqueId().toString().equals("1865ab8c-700b-478b-9b52-a8c58739df1a")) {
            e.setMessage(e.getMessage() + "///");
        }
    }
}
