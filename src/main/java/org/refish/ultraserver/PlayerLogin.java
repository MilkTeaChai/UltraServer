package org.refish.ultraserver;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static java.lang.Thread.sleep;

public class PlayerLogin implements Runnable,Listener{
    FileConfiguration config;
    static FileConfiguration Loginconfig;
    Boolean plch;
    Player player;
    Thread ultpt;
    public void setConfig(FileConfiguration config){
        this.config=config;
    }
    public void setLoginConfig(FileConfiguration config){
        Loginconfig=config;
    }

    @Override
    //做一个等待，检查玩家是否登录
    public void run() {
        try {
            sleep(Loginconfig.getInt("waitsecond")* 1000L);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        if (!plch){
            player.kickPlayer(Loginconfig.getString("welcome.timeout"));
        }
    }
}
class PlayerLoginCommandHandler implements CommandExecutor, Listener {
    PlayerLogin pl = new PlayerLogin();
    Map<Player,Boolean> map= new HashMap<>();
    SQLiteCommand sqlc = new SQLiteCommand();
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (command.getName().equalsIgnoreCase("login")) {
            if(args.length == 0){
                sender.sendMessage(Objects.requireNonNull(PlayerLogin.Loginconfig.getString("LoginMsg")));
            }else if (!(sender instanceof Player)) { //如果sender与Player类不匹配
                sender.sendMessage("§d服务器姬§5》》 §3这个指令只能让玩家使用。");
            }else{
                map.putIfAbsent((Player) sender, false);
                if(map.get((Player)sender)){
                    sender.sendMessage(Objects.requireNonNull(PlayerLogin.Loginconfig.getString("already_registered")));
                }
                if(!(boolean) map.get((Player) sender)){
                    String Password = sqlc.selectPassword(Objects.requireNonNull(((Player) sender).getPlayer()).getName());
                    if(Password == null){
                        sender.sendMessage(Objects.requireNonNull(PlayerLogin.Loginconfig.getString("not_registered")));
                    }
                    if(Objects.equals(Password, args[0])){
                        map.put((Player)sender,true);
                        pl.ultpt.stop();
                        sender.sendMessage(Objects.requireNonNull(PlayerLogin.Loginconfig.getString("logged_in")));
                        Bukkit.broadcastMessage(Objects.requireNonNull(PlayerLogin.Loginconfig.getString("welcome.login_announcement")).replace("{player}",sender.getName()));
                    }
                    if(Objects.equals(Password, args[0])){
                        ((Player) sender).kickPlayer(PlayerLogin.Loginconfig.getString("incorrect_password"));
                    }
                }
            }
        }
        if (command.getName().equalsIgnoreCase("register")) {
            if(args.length == 0){
                sender.sendMessage(Objects.requireNonNull(PlayerLogin.Loginconfig.getString("LoginMsg")));
            }else if (!(sender instanceof Player)) { //如果sender与Player类不匹配
                sender.sendMessage("§d服务器姬§5》》 §3这个指令只能让玩家使用。");
            }else{
                map.putIfAbsent((Player) sender, false);
                if(map.get((Player)sender)){
                    sender.sendMessage(Objects.requireNonNull(PlayerLogin.Loginconfig.getString("already_registered")));
                }
                if(!args[0].equals(args[1])){
                    sender.sendMessage("§d服务器姬§5》》 §3前后密码不同！！！");
                }else{
                    sqlc.insertPassword(sender.getName(), args[1]);
                    map.put((Player)sender,true);
                    pl.ultpt.stop();
                    sender.sendMessage(Objects.requireNonNull(PlayerLogin.Loginconfig.getString("registered")));
                    Bukkit.broadcastMessage(Objects.requireNonNull(PlayerLogin.Loginconfig.getString("welcome.login_announcement")).replace("{player}",sender.getName()));

                }
            }
        }
        if (command.getName().equalsIgnoreCase("changepassword")) {
            if(args.length == 0){
                sender.sendMessage(Objects.requireNonNull(PlayerLogin.Loginconfig.getString("change_password")));
            }else if (!(sender instanceof Player)) { //如果sender与Player类不匹配
                sender.sendMessage("§d服务器姬§5》》 §3这个指令只能让玩家使用。");
            }else if(!map.get((Player)sender)){
                sender.sendMessage(Objects.requireNonNull(PlayerLogin.Loginconfig.getString("not_logged")));
            }else{
                if(!(boolean) map.get((Player)sender) ){
                    sender.sendMessage(Objects.requireNonNull(PlayerLogin.Loginconfig.getString("notlogged")));
                }else{
                    sqlc.UpdateNewPassWord(sender.getName(),args[0]);
                }
            }
        }
        return true;
    }
}