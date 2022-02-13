package net.azisaba.vanilife.core.commands;

import net.azisaba.vanilife.core.VanilifeCore;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public record DamageNearbyCommand(VanilifeCore plugin) implements TabExecutor {
  @Override
  public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
    if (!(sender instanceof Player player)) return true;
    if (args.length == 0) {
      sender.sendMessage(ChatColor.RED + "/damagenearby <player>");
      return true;
    }
    Player target = Bukkit.getPlayerExact(args[0]);
    if (target == null) return true;
    player.getNearbyEntities(3, 3, 3).forEach(entity -> {
      if (entity instanceof Damageable) {
        ((Damageable) entity).damage(0.1, target);
      }
    });
    return true;
  }

  @Override
  public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
    if (args.length == 0) {
      return Bukkit.getOnlinePlayers().stream().map(Player::getName).collect(Collectors.toList());
    }
    if (args.length == 1) {
      return filter(Bukkit.getOnlinePlayers().stream().map(Player::getName), args[0]);
    }
    return Collections.emptyList();
  }

  private static List<String> filter(Stream<String> stream, String s) {
    return stream.filter(s1 -> s1.toLowerCase(Locale.ROOT).startsWith(s.toLowerCase(Locale.ROOT))).collect(Collectors.toList());
  }
}
