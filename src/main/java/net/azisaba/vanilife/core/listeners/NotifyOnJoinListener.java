package net.azisaba.vanilife.core.listeners;

import net.azisaba.vanilife.core.VanilifeCore;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public record NotifyOnJoinListener(VanilifeCore plugin) implements Listener {
  @EventHandler
  public void onPlayerJoin(PlayerJoinEvent e) {
    if (plugin.getAdminChatUtil().isAdminChatEnabled(e.getPlayer())) {
      e.getPlayer().sendMessage(Component.text("AdminChatは現在有効になっています。").color(NamedTextColor.LIGHT_PURPLE));
      e.getPlayer().sendMessage(Component.text("")
          .append(Component.text("/adminchat").color(NamedTextColor.YELLOW))
          .append(Component.text("で無効化できます。").color(NamedTextColor.GREEN))
      );
    }
  }
}
