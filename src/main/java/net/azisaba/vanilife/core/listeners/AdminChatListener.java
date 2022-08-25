package net.azisaba.vanilife.core.listeners;

import io.papermc.paper.event.player.AsyncChatEvent;
import net.azisaba.vanilife.core.VanilifeCore;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public record AdminChatListener(VanilifeCore plugin) implements Listener {
  @EventHandler
  public void onAsyncChat(AsyncChatEvent e) {
    if (plugin.getAdminChatUtil().isAdminChatEnabled(e.getPlayer())) {
      String plainMessage = PlainTextComponentSerializer.plainText().serialize(e.message());
      if (plainMessage.startsWith("!")) {
        //先頭の「!」をなくす
        String messageWithoutMark = plainMessage.substring(1);
        e.message(PlainTextComponentSerializer.plainText().deserialize(messageWithoutMark));
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
