package net.azisaba.vanilife.core.util;

import net.azisaba.vanilife.core.VanilifeCore;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.command.BlockCommandSender;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import static net.azisaba.vanilife.core.util.Util.entry;
import static net.azisaba.vanilife.core.util.Util.mapOf;

public class AdminChatUtil {
  private final Set<UUID> adminChatEnabledPlayers = new HashSet<>();
  private final VanilifeCore plugin;

  public AdminChatUtil(VanilifeCore plugin) {
    this.plugin = plugin;
  }

  public void enableAdminChat(@NotNull Player player) {
    adminChatEnabledPlayers.add(player.getUniqueId());
  }

  public void disableAdminChat(@NotNull Player player) {
    adminChatEnabledPlayers.remove(player.getUniqueId());
  }

  @Contract(pure = true)
  public boolean isAdminChatEnabled(@NotNull Player player) {
    return adminChatEnabledPlayers.contains(player.getUniqueId()) && player.hasPermission("vanilifecore.adminchat");
  }

  public void handleAdminChat(@NotNull CommandSender sender, @NotNull String message) {
    if (sender instanceof BlockCommandSender) {
      return;
    }
    String finalMessage = Util.replaceByMap(plugin.getAdminChatFormat(), variables(sender, message));
    Component component = LegacyComponentSerializer.builder().extractUrls().character('&').build().deserialize(finalMessage);
    Bukkit.getConsoleSender().sendMessage(component);
    for (Player player : Bukkit.getOnlinePlayers()) {
      if (!player.hasPermission("vanilifecore.adminchat.listen")) continue;
      player.sendMessage(component);
    }
  }

  @NotNull
  public static Map<String, ?> variables(@NotNull CommandSender sender, @NotNull String message) {
    String name = "Console";
    if (sender instanceof Player) {
      name = sender.getName();
    }
    return mapOf(
        entry("%SENDER_NAME%", name),
        entry("%MESSAGE%", message)
    );
  }
}
