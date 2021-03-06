package net.azisaba.vanilife.core.listeners;

import net.azisaba.vanilife.core.VanilifeCore;
import net.azisaba.vanilife.core.util.Util;
import net.azisaba.vanilife.core.util.VaniLifeBook;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.io.IOException;

public record FirstPlayerJoinListener(VanilifeCore plugin) implements Listener {
  @EventHandler
  public void onFirstPlayerJoin(PlayerJoinEvent e) {
    Player p = e.getPlayer();
    ChatColor green = ChatColor.GREEN;

    if (p.hasPlayedBefore()) return;

    String[] toJoinPlayerMessage = {
        green +
            """
            ばにらいふへようこそ！
            ここでは難しいコマンドなどが一切ない、純粋な『バニラサバイバルのマイクラ』を楽しむことができます。
            マルチサーバーに来たばかりの初心者さんでも遊びやすい簡単かつシンプルさを追求したシステムです。
            ルールを確認したら、ばにらいふを楽しみましょう！
            
            https://wiki.azisaba.net/
            (Java版はURLをクリック、BEはGoogle等でアジ鯖の公式ホームページからご確認いただけます。)
            """
    };

    //　初参加はどデカく。
    e.joinMessage(Component.text("ばにらいふ！初参加の" + p.getName() + "がログインしました!").color(NamedTextColor.YELLOW));
    Bukkit.getOnlinePlayers().forEach(player -> player.playSound(player.getLocation(), Sound.BLOCK_AMETHYST_BLOCK_BREAK, 1, 1));

    // 本の配布とオープン
    p.getInventory().addItem(VaniLifeBook.vanlifeBook);

    // 最初のばにらいふ!メッセージを送信
    p.sendMessage(toJoinPlayerMessage);

    // Webhookを送る
    new Thread(() -> {
      try {
        Util.sendDiscordWebhook(plugin.getFirstJoinNotifyURL(), e.getPlayer(), null);
      } catch (IOException ex) {
        plugin.getLogger().warning("Failed to send webhook");
        ex.printStackTrace();
      }
    }, "FirstPlayerJoinListener-Webhook-Client").start();
  }
}
