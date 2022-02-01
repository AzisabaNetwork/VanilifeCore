package net.azisaba.vanilife.core.listeners;

import net.azisaba.vanilife.core.VanilifeCore;
import net.azisaba.vanilife.core.util.Util;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.io.IOException;

public class NotifyAdminListener implements Listener {
    private final VanilifeCore plugin;

    public NotifyAdminListener(VanilifeCore plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onAsyncPlayerChat(AsyncPlayerChatEvent e) {
        if (e.getMessage().startsWith("/")) return;
        if (e.getMessage().contains("運営") && plugin.getAdminNotifyURL() != null) {
            if (plugin.getBlockList().contains(e.getPlayer().getUniqueId())) return;
            new Thread(() -> {
                try {
                    Util.sendDiscordWebhook(plugin.getAdminNotifyURL(), e.getPlayer(), e::getMessage);
                } catch (IOException ex) {
                    plugin.getLogger().warning("Failed to send webhook");
                    ex.printStackTrace();
                }
            }, "NotifyAdminListener-Webhook-Client").start();
        }
    }
}
