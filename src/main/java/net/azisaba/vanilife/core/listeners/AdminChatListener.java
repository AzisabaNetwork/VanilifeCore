package net.azisaba.vanilife.core.listeners;

import io.papermc.paper.event.player.AsyncChatEvent;
import net.azisaba.vanilife.core.VanilifeCore;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public record AdminChatListener(VanilifeCore plugin) implements Listener {
  @EventHandler
  public void onAsyncChat(AsyncChatEvent e) {
    if (plugin.getAdminChatUtil().isAdminChatEnabled(e.getPlayer())) {
      if (PlainTextComponentSerializer.plainText().serialize(e.message()).startsWith("!")) {
        return;
      }

      e.setCancelled(true);
      String messageAsString = LegacyComponentSerializer.legacySection().serialize(e.message());
      Bukkit.getScheduler().runTask(plugin, () -> plugin.getAdminChatUtil().handleAdminChat(
          e.getPlayer(),
          messageAsString
      ));
    }
  }
}
