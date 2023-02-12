package org.refish.ultraserver;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;
import org.bukkit.util.CachedServerIcon;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.util.Objects;

public class MotdSys implements Listener {
    private YamlConfiguration config;
    private CachedServerIcon icon;

    public void setConfig(YamlConfiguration yc){
        this.config=yc;
    }
    public void setIconFile(File icon) throws Exception {
        this.icon=Bukkit.getServer().loadServerIcon(icon);

    }
    @EventHandler
    public void setMotd(ServerListPingEvent event){
        event.setMotd(Objects.requireNonNull(config.getString("motd")));
        event.setMaxPlayers(config.getInt("maxPlayer"));
        event.setServerIcon(icon);
    }
}