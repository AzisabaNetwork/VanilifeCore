package net.azisaba.vanilife.core;

import net.azisaba.vanilife.core.commands.VanilifeCommand;
import net.azisaba.vanilife.core.listeners.NotifyAdminListener;
import net.azisaba.vanilife.core.listeners.Toto31010;
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
    private final Set<UUID> blockList = new HashSet<>();
    private String adminNotifyURL = null;

    @Override
    public void onEnable() {
        reload();
        Bukkit.getPluginManager().registerEvents(new NotifyAdminListener(this), this);
        Bukkit.getPluginManager().registerEvents(new Toto31010(this), this);
        Objects.requireNonNull(Bukkit.getPluginCommand("vanilife")).setExecutor(new VanilifeCommand(this));
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
    }

    public void save() {
        getConfig().set("blockList", blockList.stream().map(UUID::toString).collect(Collectors.toList()));
        getConfig().set("adminNotifyURL", adminNotifyURL);
        saveConfig();
    }

    @NotNull
    public Set<UUID> getBlockList() {
        return blockList;
    }

    @Nullable
    public String getAdminNotifyURL() {
        return adminNotifyURL;
    }
}
