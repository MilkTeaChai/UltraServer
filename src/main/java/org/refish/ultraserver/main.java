package org.refish.ultraserver;


import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.*;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.*;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

import static org.refish.ultraserver.Utils.Printlns.logoprint;

public final class main extends JavaPlugin implements Listener {

    //全局版本设置 每次新构建时需要修改
    static final String version ="1.7.0.6";
    public static ProtocolManager protocolManager;

    @Override
    public void onLoad() {
        saveDefaultConfig();
        File file = new File(getDataFolder(),"config.yml");
        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
        getLogger().info("§b======[§5环境检查§b]======");
        getLogger().info("§5正在检查你的环境：");
        getLogger().info("§5服务器版本："+Bukkit.getVersion());
        getLogger().info("§5BukkitAPI版本："+Bukkit.getBukkitVersion());
        getLogger().info("§5服务端："+Bukkit.getServer().getName());
        if(Objects.equals(Bukkit.getServer().getName(),"CraftBukkit")){
            getLogger().warning("你的服务端不符合该插件的运行最低前提，请使用Paper或PurPur");
            Bukkit.getPluginManager().disablePlugin(this);
        }
        protocolManager = ProtocolLibrary.getProtocolManager();
        if(!Bukkit.getVersion().contains("1.14.4")){
            getLogger().warning("该服务器的版本("+Bukkit.getVersion()+")，可能不能使用完整的功能，请等待奶茶对更高版本的支持");
        }
    }
    @Override
    public void onEnable() {
        // Plugin startup logicjmmjro-
        //打印Logo
        logoprint();
        getLogger().info("正在加载内部命令");
        //安全加载指令
        try {
            Objects.requireNonNull(Bukkit.getPluginCommand("ultraserver")).setExecutor(new CommandHandler());
            Objects.requireNonNull(Bukkit.getPluginCommand("ram")).setExecutor(new CommandHandler());
            getLogger().info("命令注册成功");
            Bukkit.getPluginManager().registerEvents(this, this);
            getLogger().info("监听器注册成功");
        } catch (NullPointerException e) {
            //在加载命令时可能会抛出空指针异常，对其捕捉
            getLogger().warning("命令注册失败，原因：空指针");
        } finally {
            //始终打印这条信息
            getLogger().warning("插件加载完成");
        }
        if (getconfig().getBoolean("setting.anti.badword.enable")) {
            getLogger().info("正在加载反脏话机制");
            main.protocolManager.addPacketListener(new PacketAdapter(this, ListenerPriority.NORMAL, PacketType.Play.Client.CHAT) {
                @Override
                public void onPacketReceiving(PacketEvent event) {
                    PacketContainer packet = event.getPacket();
                    String message = packet.getStrings().read(0);
                    List<String> list = getConfig().getStringList("setting.anti.badword.list");
                    if (message.contains(list.get(1)) || message.contains(list.get(2)) || message.contains(list.get(3)) || message.contains(list.get(4)) || message.contains(list.get(5)) || message.contains(list.get(6)) || message.contains(list.get(7)) || message.contains(list.get(8))) {
                        event.setCancelled(true);
                        event.getPlayer().sendMessage("大家都是文明人哦(U‿ฺU✿)");
                    }
                }
            });
            getLogger().info("反脏话机制已启动");
        } else {
            String reason;
            if (!getConfig().getBoolean("setting.anti.badword.enable")) {
                reason = "配置文件中已经禁用反脏话";
            } else {
                reason = "第一次启动或并配置文件不存在";
            }
            getLogger().info("由于配置文件原因,反脏话机制暂未加载 原因:" + reason);
        }

        getLogger().info("正在加载全自动垃圾清理系统");
        AutoClean ac = new AutoClean();
        ac.config(getConfig());
        ac.start();

        getLogger().info("全自动清理启动成功！");
        if(getConfig().getBoolean("setting.LoopBroadcast.enable")){
            getLogger().info("正在加载循环广播功能");
            LoopBroadcast lb =new LoopBroadcast();
            lb.config(getConfig());
            new Thread(lb,"LoopBroadcast").start();
        }
        getLogger().info("插件已加载AwA 作者奶茶 QQ3520568665");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        getLogger().info("插件已经被卸载了，感谢你的使用UwU");
    }

    static AtomicInteger atomicInteger =new AtomicInteger();

    public static void setAtom(){
        atomicInteger.getAndIncrement();
    }

    public static int getAtom(){
        return atomicInteger.get();
    }


    @NotNull
    public FileConfiguration getconfig() {
        return getConfig();
    }
}
