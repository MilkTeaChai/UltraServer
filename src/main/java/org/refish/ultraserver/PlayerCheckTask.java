package org.refish.ultraserver;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.List;
import java.util.Objects;

import static org.refish.ultraserver.PlayerLogin.Loginconfig;

public class PlayerCheckTask implements Listener {
    FileConfiguration config;

    PlayerLoginCommandHandler plch = new PlayerLoginCommandHandler();
    boolean plchb;
    @EventHandler
    public void PlayerQuitCheck(PlayerQuitEvent event){
        new PlayerLoginCommandHandler().map.put(event.getPlayer(),false);
    }
    @EventHandler
    public void PlayerLoginCheck(PlayerJoinEvent event){
        event.getPlayer().setScoreboard(new ColorBoard().sendBoard());
        event.getPlayer().sendMessage(Objects.requireNonNull(config.getString("ServerName")));
        event.getPlayer().sendMessage(Objects.requireNonNull(Loginconfig.getString("LoginMsg")));
        plchb = new PlayerLoginCommandHandler().map.get(event.getPlayer());
        new PlayerLogin().player=event.getPlayer();
        new Thread(new PlayerLogin(),"PlayerLoggedCheck");
        //做一个位移检查
        Runnable ultp = ()->{
            while(true){
                event.getPlayer().teleport(event.getPlayer().getLocation());
            }
        };
        Thread ultpt = new Thread(ultp);
        new PlayerLogin().ultpt=ultpt;
        ultpt.start();
    }
    @EventHandler
    public void ChatCheck(AsyncPlayerChatEvent event){
        if(!plch.map.get(event.getPlayer())){
            event.setCancelled(true);
            event.getPlayer().sendMessage(Objects.requireNonNull(Loginconfig.getString("welcome.chat_disabled")));
        }
        String message=event.getMessage();
        List<String> list = config.getStringList("setting.anti.badword.list");
        if (message.contains(list.get(1)) || message.contains(list.get(2)) || message.contains(list.get(3)) || message.contains(list.get(4)) || message.contains(list.get(5)) || message.contains(list.get(6)) || message.contains(list.get(7)) || message.contains(list.get(8))) {
            event.setCancelled(true);
            event.getPlayer().sendMessage("§d服务器姬§5》》大家都是文明人哦(U‿ฺU✿)");
        }
    }
}
