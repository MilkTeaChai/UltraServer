package org.refish.ultraserver;
/*
这是一个命令集
为了区分主启动Main.class
特意分类出了CommandHandler
最后修改版本: dev_1.5-Beta_ReBuild-6
代码优化:yes
 */

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.*;
import org.bukkit.event.Listener;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

import static org.bukkit.Bukkit.getLogger;
import static org.refish.ultraserver.Utils.Plugins;
import static org.refish.ultraserver.Utils.Printlns.helpmsg;

public class CommandHandler implements CommandExecutor, Listener {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, Command command, @NotNull String label, String[] args) {
        // 如果这里注册了多条命令则使用
        if (command.getName().equalsIgnoreCase("ultraserver")) {
            // 你要写的代码
            if(args.length == 0){
                sender.sendMessage(helpmsg);
            }
            switch (args[0]) {
                case "help":
                    sender.sendMessage(helpmsg);
                    break;
                case "tps":
                    getLogger().warning("计算TPS......");
                    double[] tps = Bukkit.getServer().getTPS();
                    sender.sendMessage("1m   "+Math.round(tps[0])+"  5m   "+Math.round(tps[0])+"  15m   "+Math.round(tps[0])+"     平均:"+Math.round(tps[0]+tps[1]+tps[2])/3);
                    break;
                case "whistlist":
                    if (Objects.equals(args[2], "on")) {
                        Bukkit.setWhitelist(true);
                        for(Player player:Bukkit.getOnlinePlayers()){
                            if(!player.isOp()){
                                player.kickPlayer("§d服务器姬§5》》§3白名单已启动，请自行询问腐竹和管理员相关事宜");
                            }
                        }
                    } else {
                        if (Objects.equals(args[2], "off")) {
                            Bukkit.setWhitelist(false);
                        }
                    }
                    break;
                case "clean":
                    if ("item".equals(args[1])) {
                        AtomicInteger num = new AtomicInteger();

                        Bukkit.getWorlds().forEach(world -> world.getEntities().forEach(entity -> {

                            if (entity instanceof Item && !(entity instanceof Player) && !(entity instanceof Animals) && !(entity instanceof Monster) && !entity.isDead()){
                                entity.remove();
                            num.getAndIncrement();
                        }
                        }));
                        getLogger().info("垃圾清理完毕");
                    } else {
                        if("ram".equals(args[1])){
                            System.gc();
                            getLogger().info("GC清理结束");
                        }else {
                            sender.sendMessage(helpmsg);
                        }
                    }
                    break;
                case "pl":
                    switch (args[1]){
                        case "load":
                            switch (Plugins.load(args[2])) {
                                case "OK" :
                                    sender.sendMessage("加载成功");
                                    break;
                                case "NF" :
                                    sender.sendMessage("找不到该插件");
                                    break;
                                case "ID" :
                                    sender.sendMessage("无效的描述信息");
                                    break;
                                case "IP" :
                                    sender.sendMessage("无效的插件");
                                    break;
                            }

                            break;
                        case "enable":
                            Plugins.enable(Plugins.getPluginByName(args[2]));
                            sender.sendMessage("执行成功");
                            break;
                        case "disable":
                            Plugins.disable(Plugins.getPluginByName(args[2]));
                            sender.sendMessage("执行成功");
                            break;
                        case "unload":
                            if (Plugins.unload(Objects.requireNonNull(Plugins.getPluginByName(args[2]))).equals("OK")){
                                sender.sendMessage("执行成功");
                            }else{
                                sender.sendMessage("执行失败");
                            }
                        case "" :
                        default:
                            sender.sendMessage(helpmsg);
                            break;
                    }
                    break;
                case "ulworld":
                    Bukkit.unloadWorld(Objects.requireNonNull(Bukkit.getWorld(args[1])),true);
                    sender.sendMessage("执行成功");
                    break;
                case "env":
                    sender.sendMessage("§b======[§5环境§b]======");
                    sender.sendMessage("§5正在检查你的环境：");
                    sender.sendMessage("§5服务器版本："+Bukkit.getVersion());
                    sender.sendMessage("§5BukkitAPI版本："+Bukkit.getBukkitVersion());
                    sender.sendMessage("§5服务端："+Bukkit.getServer().getName());
                    sender.sendMessage("§5插件版本："+main.version);
                case "" :
                default:
                    sender.sendMessage(helpmsg);
                    break;
            }


        }  //再次筛选玩家输入的命令
        if (command.getName().equalsIgnoreCase("ram")) {
            sender.sendMessage("总内存：%server_ram_total% 已用内存：%server_ram_used%  空闲内存：%server_ram_free%");
        }return true;
    }
}
