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

import java.util.ArrayList;
import java.util.Iterator;
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
            if (!(sender instanceof Player)) { //如果sender与Player类不匹配
                sender.sendMessage("§d服务器姬§5》》 §3这个指令只能让玩家使用。");
            }else if(args.length == 0){
                sender.sendMessage("§d服务器姬§5》》 §a使用方法: /gm 0/1/2/3。");
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
                    Bukkit.getLogger().info("§d服务器姬§5》》 §3这个命令只有OP能用");
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
                    Bukkit.getLogger().info("§d服务器姬§5》》 §3这个命令只有OP能用");
                } else {
                    player.setGameMode(GameMode.ADVENTURE);
                }
            }
        }
        if (command.getName().equalsIgnoreCase("tpa")) {
            if (args.length == 0) {
                sender.sendMessage("§d服务器姬§5》》 §3使用方法:/tpa <Player> 传送到某人那");
            } else if (!(sender instanceof Player)) { //如果sender与Player类不匹配
                sender.sendMessage("§d服务器姬§5》》 §3这个指令只能让玩家使用。");
            } else {
                Player player = (Player) sender;
                // 所需要执行的事（此处略）
                Player player2 = null;
                for (Player p : Bukkit.getOnlinePlayers()) {
                    if (p.getName().equals(args[0])) {
                        player2 = p;
                        break;
                    }
                }
                if (player2 == null) {
                    sender.sendMessage("§d服务器姬§5》》 §3玩家" + args[0] + "不在线");
                } else {
                    TPAReq tr=new TPAReq(player,player2);
                    TPACacheArray.list.add(tr);
                    Check c=new Check();
                    c.setTPAReq(tr);
                    new Thread(c.check,"TPAGC");
                    player.sendMessage("§d服务器姬§5》》 §3请求已成功发出 (请求将在30秒后失效)");
                    player2.sendMessage("§d服务器姬§5》》 §3玩家" + player.getName() + "希望传送到你那里，你是否允许？(允许/tpaccept 拒绝/tpacanel)(请求将在30秒后失效)");
                }
            }
        }
        if (command.getName().equalsIgnoreCase("tpahere")) {
            if(args.length == 0){
                sender.sendMessage("§d服务器姬§5》》 §3使用方法:/tpahere <Player> 让某人传送到你这");
            }else if (!(sender instanceof Player)) { //如果sender与Player类不匹配
                sender.sendMessage("§d服务器姬§5》》 §3这个指令只能让玩家使用。");
            } else {
                Player player = (Player) sender;
                // 所需要执行的事（此处略）
                Player player2 = null;
                for (Player p : Bukkit.getOnlinePlayers()) {
                    if (p.getName().equals(args[0])) {
                        player2 = p;
                        break;
                    }
                }
                if (player2 == null) {
                    sender.sendMessage("§d服务器姬§5》》 §3玩家" + args[0] + "不在线");
                } else {
                    TPAReq tr = new TPAReq(player, player2);
                    TPACacheArray.list.add(tr);
                    Check c = new Check();
                    c.setTPAReq(tr);
                    new Thread(c.check, "TPAGC");
                    player.sendMessage("§d服务器姬§5》》 §3请求已成功发出 (请求将在30秒后失效)");
                    player2.sendMessage("§d服务器姬§5》》 §3玩家" + player.getName() + "希望你传送到他那里，你是否允许？(允许/tpaccept 拒绝/tpacanel)(请求将在30秒后失效)");
                }
        }
        if (command.getName().equalsIgnoreCase("tpaccept")) {
            if (!(sender instanceof Player)) { //如果sender与Player类不匹配
                sender.sendMessage("§d服务器姬§5》》 §3 这个指令只能让玩家使用。");
            } else{
                ArrayList<TPAReq> list =TPACacheArray.list;
                Iterator it = list.iterator();
                while(it.hasNext()){
                    TPAReq tr = (TPAReq) it.next();
                    if(tr.getFrom()==(Player)sender){
                        sender.sendMessage("§d服务器姬§5》》 §3正在传送");
                        ((Player) sender).teleport(tr.getTo());
                        list.remove(tr);
                        break;
                    }else if(tr.getTo()==(Player)sender){
                        sender.sendMessage("§d服务器姬§5》》 §3正在传送");
                        tr.getFrom().teleport((Player)sender);
                        list.remove(tr);
                        break;
                    }
                }
                sender.sendMessage("§d服务器姬§5》》 §3没有查询到您的请求，亲");
            }
        }

        }
        if (command.getName().equalsIgnoreCase("tpacanel")) {
            if (!(sender instanceof Player)) { //如果sender与Player类不匹配
                sender.sendMessage("§d服务器姬§5》》 §3这个指令只能让玩家使用。");
            } else {
                ArrayList<TPAReq> list =TPACacheArray.list;
                Iterator it = list.iterator();
                while(it.hasNext()){
                    TPAReq tr = (TPAReq) it.next();
                    if(tr.getFrom()==(Player)sender){
                        sender.sendMessage("§d服务器姬§5》》 §3已拒绝传送请求");
                        tr.getTo().sendMessage("§d服务器姬§5》》 §3对方拒绝了你的请求");
                        list.remove(tr);
                        break;
                    }else if(tr.getTo()==(Player)sender){
                        sender.sendMessage("§d服务器姬§5》》 §3已拒绝传送请求");
                        tr.getTo().sendMessage("§d服务器姬§5》》 §3对方拒绝了你的请求");
                        list.remove(tr);
                        break;
                    }
                }
                sender.sendMessage("§d服务器姬§5》》 §3没有查询到您的请求，亲");
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
}
class TPACacheArray{
    static ArrayList<TPAReq> list =new ArrayList<TPAReq>();
}
class TPAReq{
    private Player from;
    private Player to;
    public TPAReq(Player from,Player to){
        this.from=from;
        this.to=to;
    }

    public Player getFrom() {
        return from;
    }

    public Player getTo() {
        return to;
    }
}
class Check{
    private TPAReq tr;

    public void setTPAReq(TPAReq tr) {
        this.tr = tr;
    }

    Runnable check=()->{
        try {
            sleep(40000);
            TPACacheArray.list.remove(tr);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    };
}