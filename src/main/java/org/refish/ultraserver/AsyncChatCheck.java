package org.refish.ultraserver;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.List;
import java.util.Objects;


public class AsyncChatCheck implements Listener {
    FileConfiguration config;

    PlayerLoginCommandHandler plch = new PlayerLoginCommandHandler();
    @EventHandler
    public void ChatCheck(AsyncPlayerChatEvent event){
        if(!plch.map.get(event.getPlayer())){
            event.setCancelled(true);
            event.getPlayer().sendMessage(Objects.requireNonNull(PlayerLogin.Loginconfig.getString("welcome.chat_disabled")));
        }
        String message=event.getMessage();
        List<String> list = config.getStringList("setting.anti.badword.list");
        if (message.contains(list.get(1)) || message.contains(list.get(2)) || message.contains(list.get(3)) || message.contains(list.get(4)) || message.contains(list.get(5)) || message.contains(list.get(6)) || message.contains(list.get(7)) || message.contains(list.get(8))) {
            event.setCancelled(true);
            event.getPlayer().sendMessage("§d服务器姬§5》》大家都是文明人哦(U‿ฺU✿)");
        }
    }
}
