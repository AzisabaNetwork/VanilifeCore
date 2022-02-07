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
        
        初期スポーンからみて、右(西側)が資源エリア、左(東側)が建築エリアです。
        
        1年に1回ワールドがリセットされます。
        
        リセット時は手持ちとエンダーチェストのアイテムのみ持ち越し可能です。
        """,
        // 2 Page
        """
        以下の行為が発覚した場合、アジ鯖全体での処罰がなされます。
        
        ・Minecraftバニラではできない動きを実現する行為(チートなど)
        ・暴言・反社会的発言等、不適切発言
        ・チャットガイドラインに違反する行為
        ・不法行為及び、各種規約違反
        """,
        // 3 Page
        """
        ・アジ鯖にとって不利益となる行為
        ・運営のモチベーションを奪う行為
        ・その他、運営が不適切と判断した場合(アジ鯖利用規約 第二条第四項)
        """,
        """
        以下の行為が発覚した場合、ばにらいふ！では暫定的に一日の期限BANが適応されます。
        その後、処罰が確定次第その処罰が適用されます。
        
        ・他人に迷惑をかける行為および荒らし行為
         - 他人が設置したブロックを無許可で破壊する行為
        """,
        """
         - 他人が設置したチェスト等のインベントリから無許可でアイテムを持ち出す行為
         - 他人が所有しているエンティティを無許可で害す行為
         - 建築エリアで大規模な資源採集を行う行為
         - その他他人に迷惑をかけていると判断される行為
        """,
        """
        ・サーバーに過度な負荷を与える行為
        ・マクロを使用する行為
        ・「ばにらいふ！」で入手できる全てのアイテムをほかのアジ鯖系列サーバーの通貨やアイテムと取引する行為
        ・その他運営が不適切だと判断した行為
        """,
        // 4 Page
        """
        なお、このルールはアジ鯖公式ホームページからも確認ができます。
        
        https://www.azisaba.net/server-intro/vanilife/#rule
        
        (クリックで開くことができます。)
        """
    };
    meta.setTitle("Blank");
    meta.setDisplayName(ChatColor.LIGHT_PURPLE + "ばにらいふ!について");
    meta.addPage(page);
    meta.setAuthor("ばにらいふ!運営");
    vanlifeBook.setItemMeta(meta);
  }

}
