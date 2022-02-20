package net.azisaba.vanilife.core.listeners;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {

  @EventHandler
  public void onJoin(PlayerJoinEvent e) {
    Player p = e.getPlayer();

    ChatColor yellow = ChatColor.YELLOW;
    String[] toJoinPlayerMessage = {
        yellow +
            """
            ばにらいふへようこそ！
            ルール: https://www.azisaba.net/server-intro/vanilife/
            (Java版はURLをクリック、BEはGoogle等でアジ鯖の公式ホームページからご確認いただけます。)
            """
    };

    p.sendMessage(toJoinPlayerMessage);
  }
}
