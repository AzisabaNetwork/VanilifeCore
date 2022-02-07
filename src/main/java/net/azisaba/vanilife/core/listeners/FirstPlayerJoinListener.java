package net.azisaba.vanilife.core.listeners;

import org.bukkit.event.Listener;
import net.azisaba.vanilife.core.VanilifeCore;
import net.azisaba.vanilife.core.util.VaniLifeBook;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;

public record FirstPlayerJoinListener(VanilifeCore plugin) implements Listener {
  @EventHandler
  public void onFristPlayerJoin(PlayerJoinEvent e) {
    Player p = e.getPlayer();

    if(p.hasPlayedBefore()) return;

    //　初参加はどデカく。
    // 本来Paper Plでの .setJoinMessage()は非推奨だが、Componentなど面倒くさいので無視しています。かかってこいよPaper
    e.setJoinMessage(ChatColor.YELLOW + "ばにらいふ!初参加の" + p.getName() + "がログインしました!");
    Bukkit.getOnlinePlayers().forEach(player -> player.playSound(player.getLocation(), Sound.BLOCK_AMETHYST_BLOCK_BREAK, 1, 1));

    // 本の配布とオープン
    p.getInventory().addItem(VaniLifeBook.vanlifeBook);
  }
}
