package net.azisaba.vanilife.core.commands;

import net.azisaba.vanilife.core.VanilifeCore;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;

public record AdminChatCommand(VanilifeCore plugin) implements TabExecutor {
  @Override
  public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
    if (args.length == 0) {
      if (!(sender instanceof Player player)) {
        sender.sendMessage(Component.text("このコマンドはコンソールから実行できません。").color(NamedTextColor.RED));
        return true;
      }
      if (plugin.getAdminChatUtil().isAdminChatEnabled(player)) {
        plugin.getAdminChatUtil().disableAdminChat(player);
        sender.sendMessage(Component.text("AdminChatを無効にしました").color(NamedTextColor.RED));
      } else {
        plugin.getAdminChatUtil().enableAdminChat(player);
        sender.sendMessage(Component.text("AdminChatを有効にしました").color(NamedTextColor.GREEN));
      }
      return true;
    }
    String message = String.join("", args);
    plugin.getAdminChatUtil().handleAdminChat(sender, message);
    return true;
  }

  @Override
  public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
    return Collections.emptyList();
  }
}
