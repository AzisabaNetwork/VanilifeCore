package net.azisaba.vanilife.core.commands;

import net.azisaba.vanilife.core.VanilifeCore;
import net.azisaba.vanilife.core.util.Util;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public record VanilifeCommand(VanilifeCore plugin) implements TabExecutor {
  private static final List<String> COMMANDS = Arrays.asList("block", "unblock", "reload");

  @Override
  public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
    if (args.length == 0) {
      sender.sendMessage(ChatColor.RED + "/vanilife (block|unblock)");
      return true;
    }
    if ("block".equalsIgnoreCase(args[0])) {
      if (args.length == 1) {
        sender.sendMessage(ChatColor.RED + "/vanilife block (UUID or online player name)");
        return true;
      }
      UUID uuid = getUniqueIdFromPlayerNameOrUUID(args[1]);
      if (uuid == null) {
        sender.sendMessage(ChatColor.RED + "プレイヤーが見つかりません。");
        return true;
      }
      plugin.getBlockList().add(uuid);
      plugin.save();
      sender.sendMessage(ChatColor.GREEN + "ブロックリストに" + uuid + "を追加しました。");
    } else if ("unblock".equalsIgnoreCase(args[0])) {
      if (args.length == 1) {
        sender.sendMessage(ChatColor.RED + "/vanilife unblock (UUID or online player name)");
        return true;
      }
      UUID uuid = getUniqueIdFromPlayerNameOrUUID(args[1]);
      if (uuid == null) {
        sender.sendMessage(ChatColor.RED + "プレイヤーが見つかりません。");
        return true;
      }
      plugin.getBlockList().remove(uuid);
      plugin.save();
      sender.sendMessage(ChatColor.GREEN + "ブロックリストから" + uuid + "を削除しました。");
    } else if ("reload".equalsIgnoreCase(args[0])) {
      plugin.reload();
      sender.sendMessage(ChatColor.GREEN + "設定を再読み込みしました。");
    }
    return true;
  }

  @Override
  public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
    if (args.length == 0) return Collections.emptyList();
    if (args.length == 1) {
      return filter(COMMANDS.stream(), args[0]);
    }
    if (args.length == 2) {
      if ("block".equalsIgnoreCase(args[0])) {
        if (args[1].isEmpty()) {
          Collection<? extends Player> players = Bukkit.getOnlinePlayers();
          return filter(Util.merge(players.stream().map(Player::getName), players.stream().map(p -> p.getUniqueId().toString())), args[1]);
        }
      } else if ("unblock".equalsIgnoreCase(args[0])) {
        return filter(plugin.getBlockList().stream().map(UUID::toString), args[1]);
      }
    }
    return Collections.emptyList();
  }

  private static List<String> filter(Stream<String> stream, String s) {
    return stream.filter(s1 -> s1.toLowerCase(Locale.ROOT).startsWith(s.toLowerCase(Locale.ROOT))).collect(Collectors.toList());
  }

  private static UUID getUniqueIdFromPlayerNameOrUUID(String name) {
    UUID uuid;
    try {
      uuid = UUID.fromString(name);
      if (uuid.version() != 4) throw new IllegalArgumentException("Invalid UUID version");
    } catch (IllegalArgumentException e) {
      Player player = Bukkit.getPlayerExact(name);
      if (player == null) {
        return null;
      }
      uuid = player.getUniqueId();
    }
    return uuid;
  }
}
