package net.azisaba.vanilife.core.commands;

import net.azisaba.vanilife.core.VanilifeCore;
import net.azisaba.vanilife.core.util.Util;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
//import org.bukkit.Bukkit;
//import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;

public record ToggleAdminCommand(VanilifeCore plugin) implements TabExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player player)) return true;
        String prefix = "admin-mode." + player.getUniqueId() + ".";
        boolean isAdmin = plugin.getConfig().getBoolean(prefix + "is-admin", false);
        Location loc;
        if (isAdmin) {
            loc = plugin.getConfig().getLocation(prefix + "member-location");
            plugin.getConfig().set(prefix + "admin-location", player.getLocation());
            if (loc != null) {
                /*
                player.setGameMode(GameMode.SPECTATOR);
                player.sendMessage(Component.text("5秒後にスペクテイターを解除します...").color(NamedTextColor.GREEN));
                Bukkit.getScheduler().runTaskLater(plugin, () -> player.setGameMode(GameMode.SURVIVAL), 20 * 5);
                */
                player.teleport(loc);
            }
            Util.serialize(plugin.getConfig(), prefix + "admin-inventory", player.getInventory());
            Util.deserialize(plugin.getConfig(), prefix + "member-inventory", player.getInventory());
            plugin.getConfig().set(prefix + "is-admin", false);
            player.sendMessage(Component.text("鯖民モードになりました。").color(NamedTextColor.GREEN));
        } else {
            loc = plugin.getConfig().getLocation(prefix + "admin-location");
            plugin.getConfig().set(prefix + "member-location", player.getLocation());
            if (loc != null) player.teleport(loc);
            Util.serialize(plugin.getConfig(), prefix + "member-inventory", player.getInventory());
            Util.deserialize(plugin.getConfig(), prefix + "admin-inventory", player.getInventory());
            plugin.getConfig().set(prefix + "is-admin", true);
            player.sendMessage(Component.text("Adminモードになりました。").color(NamedTextColor.GREEN));
        }
        plugin.save();
        return true;
    }

    @Override
    public @NotNull List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        return Collections.emptyList();
    }
}
