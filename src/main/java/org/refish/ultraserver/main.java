package org.refish.ultraserver;


import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.*;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

import static org.refish.ultraserver.Utils.Printlns.logoprint;
import org.refish.ultraserver.EssDedicatedCommandHandler.PlayerTeleportCachePool;

public final class main extends JavaPlugin implements Listener {

    //全局版本设置 每次新构建时需要修改
    static final String version ="1.7.0.7";
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
            Objects.requireNonNull(Bukkit.getPluginCommand("ultraserver")).setExecutor(new CommandHandler());
            Objects.requireNonNull(Bukkit.getPluginCommand("ram")).setExecutor(new CommandHandler());
            Objects.requireNonNull(Bukkit.getPluginCommand("gm")).setExecutor(new EssDedicatedCommandHandler());
            Objects.requireNonNull(Bukkit.getPluginCommand("gmc")).setExecutor(new EssDedicatedCommandHandler());
            Objects.requireNonNull(Bukkit.getPluginCommand("gms")).setExecutor(new EssDedicatedCommandHandler());
            Objects.requireNonNull(Bukkit.getPluginCommand("gma")).setExecutor(new EssDedicatedCommandHandler());
            Objects.requireNonNull(Bukkit.getPluginCommand("tpa")).setExecutor(new EssDedicatedCommandHandler());
            Objects.requireNonNull(Bukkit.getPluginCommand("tpahere")).setExecutor(new EssDedicatedCommandHandler());
            Objects.requireNonNull(Bukkit.getPluginCommand("tpaccept")).setExecutor(new EssDedicatedCommandHandler());
            Objects.requireNonNull(Bukkit.getPluginCommand("tpacanel")).setExecutor(new EssDedicatedCommandHandler());
            getLogger().info("命令注册成功");
            Bukkit.getPluginManager().registerEvents(this, this);
            getLogger().info("监听器注册成功");
            getLogger().info("正在加载SQLITE数据库");
            EssDedicatedCommandHandler edch = new EssDedicatedCommandHandler();
            SQLiteCommand sc=new SQLiteCommand();
            Connection conn = null;
            try {
                // db parameters
                String url = "jdbc:sqlite:"+new File(getDataFolder(),"EssentialExpansion.db").getPath();
                // create a connection to the database
                conn = DriverManager.getConnection(url);
                getLogger().warning("Connection to SQLite has been established, URL:"+url);
                edch.setSQLiteConnection(conn);
                sc.setSQLiteConnection(conn);
            } catch (SQLException e) {
                System.out.println(e.getMessage());
                getLogger().warning("SQLite加载失败,插件即将自动关闭");
                Bukkit.getPluginManager().disablePlugin(this);
            } finally {
                try {
                    if (conn != null) {
                        conn.close();
                    }
                } catch (SQLException ex) {
                    System.out.println(ex.getMessage());
                }
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
         new Thread(ac,"AutoClean").start();

        getLogger().info("全自动清理启动成功！");
        if(getConfig().getBoolean("setting.LoopBroadcast.enable")){
            getLogger().info("正在加载循环广播功能");
            LoopBroadcast lb =new LoopBroadcast();
            lb.config(getConfig());
            new Thread(lb,"LoopBroadcast").start();
        }
        getLogger().info("正在加载Essentials++拓展功能");
        edch.setConfig(YamlConfiguration.loadConfiguration(new File(getDataFolder(),"config.yml")));
        getLogger().info("插件已加载AwA 作者奶茶 QQ3520568665");
        if  (getConfig().getBoolean("FirstRun")){
            sc.createNewTable("CREATE TABLE IF NOT EXISTS PlayerHome (\n" + " ID INT PRIMARY KEY NOT NULL,\n"
                    +" Player text NOT NULL,\n" + " Name text,\n" +  " LocationX real NOT NULL,\n"+ " LocationY real NOT NULL,\n"+ " LocationZ real NOT NULL,\n"+ " World real NOT NULL,\n" +");");
            getConfig().set("FirstRun",false);
        }
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        PlayerTeleportCachePool ptcp =new PlayerTeleportCachePool();
        ptcp.del(1);
        ptcp.del(2);
        ptcp.del(3);
        getLogger().info("TPA功能已关闭");
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
