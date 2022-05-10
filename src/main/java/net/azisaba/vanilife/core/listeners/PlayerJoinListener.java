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

    // 新規はこれを表示しない
    if (!p.hasPlayedBefore()) return;

    // 死亡している場合はリスポーン
    p.spigot().respawn();

    ChatColor green = ChatColor.GREEN;
    String[] toJoinPlayerMessage = {
        green +
            """
            ばにらいふへようこそ！
            ルール: https://www.azisaba.net/server-intro/vanilife/
            (Java版はURLをクリック、BEはGoogle等でアジ鯖の公式ホームページからご確認いただけます。)
            """
    };

    p.sendMessage(toJoinPlayerMessage);
  }
}
