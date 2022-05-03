package net.azisaba.vanilife.core.util;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public record VaniLifeBook(JavaPlugin plugin) {

  public static final ItemStack vanlifeBook = new ItemStack(Material.WRITTEN_BOOK);

  static {
    BookMeta meta = (BookMeta) vanlifeBook.getItemMeta();
    String[] pages = {
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
    List<Component> lores = new ArrayList<>();
    lores.add(Component.text("バージョン 2.5"));
    meta.setTitle("Blank");
    meta.displayName(Component.text("ばにらいふ!について").color(NamedTextColor.LIGHT_PURPLE));
    meta.addPages(Arrays.stream(pages).map(Component::text).toList().toArray(new Component[0]));
    meta.lore(lores);
    meta.setAuthor("ばにらいふ!運営");
    vanlifeBook.setItemMeta(meta);
  }

}
