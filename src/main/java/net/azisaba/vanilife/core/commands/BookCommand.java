package net.azisaba.vanilife.core.commands;

import net.azisaba.vanilife.core.util.VaniLifeBook;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class BookCommand implements CommandExecutor {

  @Override
  public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, String[] args) {
    if(!(sender instanceof Player player)) return true;
    if(command.getName().equals("book")) {
      player.getInventory().addItem(VaniLifeBook.vanlifeBook);
      player.sendMessage("本をあなたのインベントリに入れました。");
    }
    return true;
  }
}
