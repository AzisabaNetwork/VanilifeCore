package net.azisaba.vanilife.core;

import net.azisaba.vanilife.core.commands.AdminChatCommand;
import net.azisaba.vanilife.core.commands.BookCommand;
import net.azisaba.vanilife.core.commands.DamageNearbyCommand;
import net.azisaba.vanilife.core.commands.ToggleAdminCommand;
import net.azisaba.vanilife.core.commands.VanilifeCommand;
import net.azisaba.vanilife.core.listeners.AdminChatListener;
import net.azisaba.vanilife.core.listeners.FirstPlayerJoinListener;
import net.azisaba.vanilife.core.listeners.NotifyAdminListener;
import net.azisaba.vanilife.core.listeners.NotifyOnJoinListener;
import net.azisaba.vanilife.core.listeners.PlayerJoinListener;
import net.azisaba.vanilife.core.listeners.Toto31010;
import net.azisaba.vanilife.core.util.AdminChatUtil;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public class VanilifeCore extends JavaPlugin {
  private static final String DEFAULT_ADMIN_CHAT_FORMAT = "&d[&e%SENDER_NAME%&d] &f%MESSAGE%";
  private final Set<UUID> blockList = new HashSet<>();
  private final AdminChatUtil adminChatUtil = new AdminChatUtil(this);
  private String adminNotifyURL = null;
  private String firstJoinNotifyURL = null;
  // %SENDER_NAME% - the name of the sender
  // %MESSAGE% - the message
  private String adminChatFormat = DEFAULT_ADMIN_CHAT_FORMAT;

  @Override
  public void onEnable() {
    reload();
    save();
    Bukkit.getPluginManager().registerEvents(new NotifyAdminListener(this), this);
    Bukkit.getPluginManager().registerEvents(new Toto31010(this), this);
    Bukkit.getPluginManager().registerEvents(new PlayerJoinListener(), this);
    Bukkit.getPluginManager().registerEvents(new FirstPlayerJoinListener(this), this);
    Bukkit.getPluginManager().registerEvents(new AdminChatListener(this), this);
    Bukkit.getPluginManager().registerEvents(new NotifyOnJoinListener(this), this);
    Objects.requireNonNull(Bukkit.getPluginCommand("toggleadmin")).setExecutor(new ToggleAdminCommand(this));
    Objects.requireNonNull(Bukkit.getPluginCommand("vanilife")).setExecutor(new VanilifeCommand(this));
    Objects.requireNonNull(Bukkit.getPluginCommand("book")).setExecutor(new BookCommand());
    Objects.requireNonNull(Bukkit.getPluginCommand("damagenearby")).setExecutor(new DamageNearbyCommand(this));
    Objects.requireNonNull(Bukkit.getPluginCommand("adminchat")).setExecutor(new AdminChatCommand(this));
  }

  public void reload() {
    blockList.clear();
    reloadConfig();
    for (String s : getConfig().getStringList("blockList")) {
      try {
        blockList.add(UUID.fromString(s));
      } catch (IllegalArgumentException e) {
        getLogger().warning("Failed to parse blockList " + s);
        e.printStackTrace();
      }
    }
    adminNotifyURL = getConfig().getString("adminNotifyURL");
    firstJoinNotifyURL = getConfig().getString("firstJoinNotifyURL");
    adminChatFormat = getConfig().getString("adminChatFormat", DEFAULT_ADMIN_CHAT_FORMAT);
  }

  public void save() {
    getConfig().set("blockList", blockList.stream().map(UUID::toString).collect(Collectors.toList()));
    getConfig().set("adminNotifyURL", adminNotifyURL);
    getConfig().set("firstJoinNotifyURL", firstJoinNotifyURL);
    getConfig().set("adminChatFormat", adminChatFormat);
    saveConfig();
  }

  @NotNull
  public AdminChatUtil getAdminChatUtil() {
    return adminChatUtil;
  }

  @NotNull
  public Set<UUID> getBlockList() {
    return blockList;
  }

  @Nullable
  public String getAdminNotifyURL() {
    return adminNotifyURL;
  }

  @Nullable
  public String getFirstJoinNotifyURL() {
    return firstJoinNotifyURL;
  }

  @NotNull
  public String getAdminChatFormat() {
    return Objects.requireNonNull(adminChatFormat);
  }
}
