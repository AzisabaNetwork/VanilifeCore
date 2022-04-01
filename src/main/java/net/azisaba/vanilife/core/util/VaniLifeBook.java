package net.azisaba.vanilife.core.util;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.plugin.java.JavaPlugin;

public record VaniLifeBook(JavaPlugin plugin) {

  public static final ItemStack vanlifeBook = new ItemStack(Material.WRITTEN_BOOK);

  static {
    BookMeta meta = (BookMeta) vanlifeBook.getItemMeta();
    String[] page = {
        // 1 Page
        """
        ～ ばにらいふ! 仕様書 ～
        ・各種ツールを使用して自身の財産を守ることができます。
        ・各ワールドは8ヶ月で削除されて新しくなります。
        ・ワールド間を移動してもインベントリとエンダーチェストは引き継げます。
        """,
        // 2 Page
        """
        ばにらいふの遊び方はアジ鯖公式Wikiへ！
        
        https://wiki.azisaba.net/wiki/
        """,
        // 3 Page
        """
        また、ばにらいふ!プレイ時はアジ鯖全体ルールに加え、ばにらいふ!ルールを守る必要があります。
        
        https://www.azisaba.net/server-intro/vanilife#rules
        """,
    };
    meta.setTitle("Blank");
    meta.setDisplayName(ChatColor.LIGHT_PURPLE + "ばにらいふ!について" + ChatColor.YELLOW + " - Ver2");
    meta.addPage(page);
    meta.setAuthor("ばにらいふ!運営");
    vanlifeBook.setItemMeta(meta);
  }

}
