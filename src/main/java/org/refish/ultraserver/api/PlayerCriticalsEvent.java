package org.refish.ultraserver.api;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.refish.ultraserver.*;

public class PlayerCriticalsEvent extends PlayerEvent implements Listener {
    private static final HandlerList handler = new HandlerList();
    public PlayerCriticalsEvent(@NotNull Player player) {
        super(player);
    }
    //相关触发事件已经移到main方法内

    @NotNull
    @Override
    public HandlerList getHandlers() {
        return handler;
    }

    public static HandlerList getHandlerList() {
        return handler;
    }
}
