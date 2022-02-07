package net.azisaba.vanilife.core.listeners;

import net.azisaba.vanilife.core.VanilifeCore;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public record JailMeru(VanilifeCore plugin) implements Listener {
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        if (e.getPlayer().getUniqueId().toString().equals("bb991c6b-aafb-405c-b2af-57cd5828962d")) {
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "/jail " + e.getPlayer().getName() + " ばにらいふ牢屋");
        }
    }
}
