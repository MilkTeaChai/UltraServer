package org.refish.ultraserver;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

import static java.lang.Thread.sleep;
import static org.refish.ultraserver.Utils.Printlns.helpmsg;

public class EssDedicatedCommandHandler implements CommandExecutor, Listener {
    YamlConfiguration config;
    SQLiteCommand sc = new SQLiteCommand();
    public void setConfig(YamlConfiguration yc){
        this.config=yc;
    }
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (command.getName().equalsIgnoreCase("gm")) {
            if(args.length == 0){
                sender.sendMessage(helpmsg);
            }else if (!(sender instanceof Player)) { //如果sender与Player类不匹配
                sender.sendMessage("§d服务器姬§5》》 §3这个指令只能让玩家使用。");
            } else {
                Player player = (Player) sender;
                // 所需要执行的事（此处略）
                if (!player.isOp()) {
                    Bukkit.getLogger().info("这个命令只有OP能用");
                } else if (args[0].equals("1")) {
                    player.setGameMode(GameMode.CREATIVE);
                } else if (args[0].equals("0")) {
                    player.setGameMode(GameMode.SURVIVAL);
                } else if (args[0].equals("2")) {
                    player.setGameMode(GameMode.ADVENTURE);
                } else if (args[0].equals("3")) {
                    player.setGameMode(GameMode.SPECTATOR);
                }
            }
        }
        if (command.getName().equalsIgnoreCase("gms")) {
            if (!(sender instanceof Player)) { //如果sender与Player类不匹配
                sender.sendMessage("§d服务器姬§5》》 §3这个指令只能让玩家使用。");
            } else {
                Player player = (Player) sender;
                // 所需要执行的事（此处略）
                if (!player.isOp()) {
                    Bukkit.getLogger().info("这个命令只有OP能用");
                } else {
                    player.setGameMode(GameMode.SURVIVAL);
                }
            }
        }
        if (command.getName().equalsIgnoreCase("gmc")) {
            if(args.length == 0){
                sender.sendMessage(helpmsg);
            }
            if (!(sender instanceof Player)) { //如果sender与Player类不匹配
                sender.sendMessage("§d服务器姬§5》》 §3这个指令只能让玩家使用。");
            } else {
                Player player = (Player) sender;
                // 所需要执行的事（此处略）
                if (!player.isOp()) {
                    Bukkit.getLogger().info("这个命令只有OP能用");
                } else {
                    player.setGameMode(GameMode.CREATIVE);
                }
            }
        }
        if (command.getName().equalsIgnoreCase("gma")) {
            if (!(sender instanceof Player)) { //如果sender与Player类不匹配
                sender.sendMessage("§d服务器姬§5》》 §3这个指令只能让玩家使用。");
            } else {
                Player player = (Player) sender;
                // 所需要执行的事（此处略）
                if (!player.isOp()) {
                    Bukkit.getLogger().info("这个命令只有OP能用");
                } else {
                    player.setGameMode(GameMode.ADVENTURE);
                }
            }
        }
        if (command.getName().equalsIgnoreCase("tpa")) {
            if(args.length == 0){
                sender.sendMessage(helpmsg);
            }else if (!(sender instanceof Player)) { //如果sender与Player类不匹配
                sender.sendMessage("§d服务器姬§5》》 §3这个指令只能让玩家使用。");
            } else {
                Player player = (Player) sender;
                // 所需要执行的事（此处略）
                Player player2 = Bukkit.getPlayer(args[0]);
                if (!Objects.requireNonNull(player2).isOnline()) {
                    sender.sendMessage("§d服务器姬§5》》 §3玩家" + args[0] + "不在线");
                } else {
                    PlayerTeleportCachePool ptc = new PlayerTeleportCachePool();
                    if (!ptc.player1) {
                        player.sendMessage("§d服务器姬§5》》 §3请求已成功发出 (你的传送ID为1)(请求将在30秒后失效)");
                        ptc.player1 = true;
                        ptc.save(1, player, player2);
                        player2.sendMessage("§d服务器姬§5》》 §3玩家" + player.getName() + "希望传送到你那里，你是否允许？(允许/tpaccept 拒绝/tpacanel)(请求将在30秒后失效)");
                    } else if (!ptc.player2) {
                        player.sendMessage("§d服务器姬§5》》 §3请求已成功发出 (你的传送ID为2)(请求将在30秒后失效)");
                        ptc.player2 = true;
                        ptc.save(2, player, player2);
                        player2.sendMessage("§d服务器姬§5》》 §3玩家" + player.getName() + "希望传送到你那里，你是否允许？(允许/tpaccept 拒绝/tpacanel)");
                    } else if (!ptc.player3) {
                        player.sendMessage("§d服务器姬§5》》 §3请求已成功发出 (你的传送ID为3)(请求将在30秒后失效)");
                        ptc.player3 = true;
                        ptc.save(3, player, player2);
                        player2.sendMessage("§d服务器姬§5》》 §3玩家" + player.getName() + "希望传送到你那里，你是否允许？(允许/tpaccept 拒绝/tpacanel)");
                    } else {
                        player.sendMessage("§d服务器姬§5》》 §3请求爆满，请稍等重试");
                    }
                }
            }
        }
        if (command.getName().equalsIgnoreCase("tpahere")) {
            if(args.length == 0){
                sender.sendMessage(helpmsg);
            }else if (!(sender instanceof Player)) { //如果sender与Player类不匹配
                sender.sendMessage("§d服务器姬§5》》 §3这个指令只能让玩家使用。");
            } else {
                Player player = (Player) sender;
                // 所需要执行的事（此处略）
                Player player2 = Bukkit.getPlayer(args[0]);
                if (!Objects.requireNonNull(player2).isOnline()) {
                    sender.sendMessage("§d服务器姬§5》》 §3玩家" + args[0] + "不在线");
                } else {
                    PlayerTeleportCachePool ptc = new PlayerTeleportCachePool();
                    if (!ptc.player1) {
                        player.sendMessage("§d服务器姬§5》》 §3请求已成功发出 (你的传送ID为1)(请求将在30秒后失效)");
                        ptc.player1 = true;
                        ptc.save(1, player2, player);
                        player2.sendMessage("§d服务器姬§5》》 §3玩家" + player.getName() + "希望传送到你那里，你是否允许？(允许/tpaccept 拒绝/tpacanel)(请求将在30秒后失效)");
                    } else if (!ptc.player2) {
                        player.sendMessage("§d服务器姬§5》》 §3请求已成功发出 (你的传送ID为2)(请求将在30秒后失效)");
                        ptc.player2 = true;
                        ptc.save(2, player2, player);
                        player2.sendMessage("§d服务器姬§5》》 §3玩家" + player.getName() + "希望传送到你那里，你是否允许？(允许/tpaccept 拒绝/tpacanel)");
                    } else if (!ptc.player3) {
                        player.sendMessage("§d服务器姬§5》》 §3请求已成功发出 (你的传送ID为3)(请求将在30秒后失效)");
                        ptc.player3 = true;
                        ptc.save(3, player2, player);
                        player2.sendMessage("§d服务器姬§5》》 §3玩家" + player.getName() + "希望传送到你那里，你是否允许？(允许/tpaccept 拒绝/tpacanel)");
                    } else {
                        player.sendMessage("§d服务器姬§5》》 §3请求爆满，请稍等重试");
                    }
                }
            }
        }
        if (command.getName().equalsIgnoreCase("tpaccept")) {
            if (!(sender instanceof Player)) { //如果sender与Player类不匹配
                sender.sendMessage("§d服务器姬§5》》 §3 这个指令只能让玩家使用。");
            } else{
            PlayerTeleportCachePool ptc = new PlayerTeleportCachePool();
            if (sender == ptc.Player1_1){
                sender.sendMessage("§d服务器姬§5》》 §3正在传送");
                ((Player) sender).teleport(ptc.Player1_2);
                ptc.player1=false;
                ptc.del(1);
            }else if (sender == ptc.Player1_2){
                sender.sendMessage("§d服务器姬§5》》 §3正在传送");
                ptc.Player1_1.teleport(((Player) sender));
                ptc.player1=false;
            }else if (sender == ptc.Player2_1){
                sender.sendMessage("§d服务器姬§5》》 §3正在传送");
                ((Player) sender).teleport(ptc.Player2_2);
                ptc.player1=false;
            }else if (sender == ptc.Player2_2){
                sender.sendMessage("§d服务器姬§5》》 §3正在传送");
                ptc.Player2_1.teleport(((Player) sender));
                ptc.player1=false;
            }else if (sender == ptc.Player3_1){
                sender.sendMessage("§d服务器姬§5》》 §3正在传送");
                ((Player) sender).teleport(ptc.Player3_2);
                ptc.player1=false;
            }else if (sender == ptc.Player3_2){
                sender.sendMessage("§d服务器姬§5》》 §3正在传送");
                ptc.Player3_1.teleport(((Player) sender));
                ptc.player1=false;
            }else{
                sender.sendMessage("§d服务器姬§5》》 §3没有查询到您的请求，亲");
            }
            }
        }
        if (command.getName().equalsIgnoreCase("tpacanel")) {
            if (!(sender instanceof Player)) { //如果sender与Player类不匹配
                sender.sendMessage("§d服务器姬§5》》 §3这个指令只能让玩家使用。");
            } else{
                PlayerTeleportCachePool ptc = new PlayerTeleportCachePool();
                if (sender == ptc.Player1_1){
                    ptc.Player1_2.sendMessage("§d服务器姬§5》》 §3对方拒绝了你的请求");
                    sender.sendMessage("§d服务器姬§5》》 §3已拒绝传送请求");
                    ptc.player1=false;
                    ptc.del(1);
                }else if (sender == ptc.Player1_2){
                    ptc.Player1_1.sendMessage("§d服务器姬§5》》 §3对方拒绝了你的请求");
                    sender.sendMessage("§d服务器姬§5》》 §3已拒绝传送请求");
                    ptc.player1=false;
                    ptc.del(1);
                    ptc.Player1_1.teleport(((Player) sender));
                }else if (sender == ptc.Player2_1){
                    ptc.Player2_2.sendMessage("§d服务器姬§5》》 §3对方拒绝了你的请求");
                    sender.sendMessage("§d服务器姬§5》》 §3已拒绝传送请求");
                    ptc.player1=false;
                    ptc.del(2);
                }else if (sender == ptc.Player2_2){
                    ptc.Player2_1.sendMessage("§d服务器姬§5》》 §3对方拒绝了你的请求");
                    sender.sendMessage("§d服务器姬§5》》 §3已拒绝传送请求");
                    ptc.player1=false;
                    ptc.del(2);
                }else if (sender == ptc.Player3_1){
                    ptc.Player3_2.sendMessage("§d服务器姬§5》》 §3对方拒绝了你的请求");
                    sender.sendMessage("§d服务器姬§5》》 §3已拒绝传送请求");
                    ptc.player1=false;
                    ptc.del(3);
                }else if (sender == ptc.Player3_2){
                    ptc.Player3_1.sendMessage("§d服务器姬§5》》 §3对方拒绝了你的请求");
                    sender.sendMessage("§d服务器姬§5》》 §3已拒绝传送请求");
                    ptc.player1=false;
                    ptc.del(3);
                }else{
                    sender.sendMessage("§d服务器姬§5》》 §3没有查询到您的请求，亲");
                }
        }
        }
        if (command.getName().equalsIgnoreCase("setspawn")) {
            if(args.length == 0){
                sender.sendMessage(helpmsg);
            }else if (!(sender instanceof Player)) { //如果sender与Player类不匹配
                sender.sendMessage("§d服务器姬§5》》 §3 这个指令只能让玩家使用。");
            }else{
                Location pl =((Player) sender).getLocation();
                config.set("setting.Essentials.SpawnWorld",pl.getWorld().getName());
                config.set("setting.Essentials.SpawnX",pl.getX());
                config.set("setting.Essentials.SpawnY",pl.getY());
                config.set("setting.Essentials.SpawnZ",pl.getZ());
            }
        }
        if (command.getName().equalsIgnoreCase("spawn")) {
            if (!(sender instanceof Player)) { //如果sender与Player类不匹配
                sender.sendMessage("§d服务器姬§5》》 §3 这个指令只能让玩家使用。");
            }else{
                Location pl =new Location(Bukkit.getWorld(Objects.requireNonNull(config.getString("setting.Essentials.SpawnWorld"))),config.getDouble("setting.Essentials.SpawnX"),config.getDouble("setting.Essentials.SpawnY"),config.getDouble("setting.Essentials.SpawnZ"));
                ((Player) sender).teleport(pl);
            }
        }
        if (command.getName().equalsIgnoreCase("sethome")) {
            if(args.length == 0){
                sender.sendMessage(helpmsg);
            }else if (!(sender instanceof Player)) { //如果sender与Player类不匹配
                sender.sendMessage("§d服务器姬§5》》 §3 这个指令只能让玩家使用。");
            }else{
                sc.insertHomeInfo(sender.getName(),args[0],((Player) sender).getLocation().getX(),((Player) sender).getLocation().getY(),((Player) sender).getLocation().getZ(),((Player) sender).getLocation().getWorld().getName());
                sender.sendMessage("§d服务器姬§5》》 §3设置成功");
            }
        if (command.getName().equalsIgnoreCase("home")) {
            if (args.length == 0) {
                sender.sendMessage(helpmsg);
            } else if (!(sender instanceof Player)) { //如果sender与Player类不匹配
                sender.sendMessage(" §3 你必须输入一个名称。");
            } else {
                Location location = sc.selectHomeTeleport(sender.getName(), args[0]);
                if (location == null) {
                    sender.sendMessage("§d服务器姬§5》》 §3并没有找到你设置的家");
                } else {
                    ((Player) sender).teleport(location);
                }
            }}
        }
        return true;
    }

    static class PlayerTeleportCachePool implements Runnable{
        int CheckID;
        Player Player1_1;
        Player Player1_2;
        Boolean player1 = false;
        Player Player2_1;
        Player Player2_2;
        Boolean player2 = false;
        Player Player3_1;
        Player Player3_2;
        Boolean player3 = false;

        public void save(int ID, Player from, Player to) {
            switch (ID) {
                case 1-> {
                    from = Player1_1;
                    to = Player1_2;
                    CheckID = 1;
                    new Thread(this, "PlayerTeleportCheck1").start();
                }
                case 2-> {
                    from = Player2_1;
                    to = Player2_2;
                    new Thread(this, "PlayerTeleportCheck2").start();
                }
                case 3-> {
                    from = Player3_1;
                    to = Player3_2;
                    new Thread(this, "PlayerTeleportCheck3").start();
                }
            }
        }

        public void del(int ID) {
            switch (ID) {
                case 1 -> {
                    Player1_1 = Player1_2 = null;
                    player1 = false;
                }
                case 2 -> {
                    Player2_1 = Player2_2 = null;
                    player2 = false;
            }
                case 3-> {
                    Player3_1 = Player3_2 = null;
                    player3 = false;
                }
            }
        }

        @Override
        public void run() {
            try {
                sleep(40000);
                player1=false;
                del(CheckID);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}