package org.refish.ultraserver.api;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class PlayerFallEvent extends PlayerEvent {
    private static final HandlerList handler = new HandlerList();

    public PlayerFallEvent(@NotNull Player player) {
        super(player);
    }
    @EventHandler
    public void JumpFall(com.destroystokyo.paper.event.player.PlayerJumpEvent event){
        //判断，跳跃后将下落
        Event PlayerFallEvent = new PlayerFallEvent(event.getPlayer());
        Bukkit.getServer().getPluginManager().callEvent(PlayerFallEvent);
    }
    @EventHandler
    public void HighPlaceFall(PlayerMoveEvent event){
        Player player= event.getPlayer();
        Double PlayerLocation = player.getLocation().getY();
        int BlockLocationY = player.getLocation().getBlock().getY();
        int BlockLocationZ = player.getLocation().getBlock().getZ();
        int BlockLocationX = player.getLocation().getBlock().getX();
        int newestBlockLocationY=BlockLocationY;
        Block block= Objects.requireNonNull(Bukkit.getWorld("world")).getBlockAt(BlockLocationX,newestBlockLocationY,BlockLocationZ);
        do{
            newestBlockLocationY=newestBlockLocationY-1;
        }while(Objects.equals(block.getType(), Material.AIR));
        if(newestBlockLocationY>4){
            Event PlayerFallEvent = new PlayerFallEvent(event.getPlayer());
            Bukkit.getServer().getPluginManager().callEvent(PlayerFallEvent);
        }
    }
    @NotNull
    @Override
    public HandlerList getHandlers() {
        return handler;
    }

    public static HandlerList getHandlerList() {
        return handler;
    }
}
