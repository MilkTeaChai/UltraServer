package org.refish.ultraserver;


import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

import static org.refish.ultraserver.Utils.Printlns.logoprint;
import org.refish.ultraserver.EssDedicatedCommandHandler.PlayerTeleportCachePool;

public final class main extends JavaPlugin implements Listener {

    //全局版本设置 每次新构建时需要修改
    static final String version ="1.7.0.8";
    @Override
    public void onLoad() {
        saveDefaultConfig();
        saveResource("LoginMsg.yml",false);
        boolean EnvCheckPassed =!System.getProperty( "java.specification.version").equals("1.7") && !Objects.equals(Bukkit.getServer().getName(),"CraftBukkit");
        getLogger().info("§b======[§5环境检查§b]======");
        getLogger().info("§5正在检查你的环境：");
        getLogger().info("§5服务器版本："+Bukkit.getVersion());
        getLogger().info("§5BukkitAPI版本："+Bukkit.getBukkitVersion());
        getLogger().info("§5服务端："+Bukkit.getServer().getName());
        getLogger().info("§5插件版本："+version);
        getLogger().info("§5你的Java版本："+System.getProperty("java.specification.version"));
        getLogger().info("§5你的系统的位数："+System.getProperty("sun.arch.data.model"));
        if(! System.getProperty( "java.specification.version").equals("1.7")){
            getLogger().warning("§c您的Java版本"+System.getProperty("java.version")+"将在未来不受Refish的支持，请将Java版本升级为Java8或更高");
        }
        if(Objects.equals(Bukkit.getServer().getName(),"CraftBukkit")){
            getLogger().warning("§c你的服务端可能对该插件的适配性很差，请考虑使用Akarin或Purpur");
        }
        if(EnvCheckPassed){
            getLogger().info("§5环境检查: [§2Passed§5]");
        }else{
            getLogger().info("§5环境检查: [§cFailed§5]");
        }
    }
    @Override
    public void onEnable() {
        // Plugin startup logicjmmjro-
        //打印Logo
        logoprint();
        PlayerLogin pl=new PlayerLogin();
        AsyncChatCheck acc = new AsyncChatCheck();
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
        Objects.requireNonNull(Bukkit.getPluginCommand("setspawn")).setExecutor(new EssDedicatedCommandHandler());
        Objects.requireNonNull(Bukkit.getPluginCommand("spawn")).setExecutor(new EssDedicatedCommandHandler());
        Objects.requireNonNull(Bukkit.getPluginCommand("sethome")).setExecutor(new EssDedicatedCommandHandler());
        Objects.requireNonNull(Bukkit.getPluginCommand("home")).setExecutor(new EssDedicatedCommandHandler());
        Objects.requireNonNull(Bukkit.getPluginCommand("login")).setExecutor(new PlayerLoginCommandHandler());
        Objects.requireNonNull(Bukkit.getPluginCommand("register")).setExecutor(new PlayerLoginCommandHandler());
        Objects.requireNonNull(Bukkit.getPluginCommand("changepassword")).setExecutor(new PlayerLoginCommandHandler());
            getLogger().info("命令注册成功");
            Bukkit.getPluginManager().registerEvents(this, this);
        Bukkit.getPluginManager().registerEvents(pl, this);
        Bukkit.getPluginManager().registerEvents(acc, this);
            getLogger().info("监听器注册成功");
            getLogger().info("正在加载SQLITE数据库");

            EssDedicatedCommandHandler edch = new EssDedicatedCommandHandler();
            SQLiteCommand sc=new SQLiteCommand();
            Connection conn = null;
            try {
                if  (getConfig().getBoolean("FirstRun")){
                    conn = DriverManager.getConnection("jdbc:sqlite:"+new File(getDataFolder(),"UltraServer.db").getPath());
                    DatabaseMetaData meta = conn.getMetaData();
                    sc.setSQLiteConnection(conn);
                    sc.createNewTable("CREATE TABLE IF NOT EXISTS PlayerHome (\n ID INT PRIMARY KEY NOT NULL,\n Player text NOT NULL,\n Name text,\n LocationX real NOT NULL,\n LocationY real NOT NULL,\n LocationZ real NOT NULL,\n World real NOT NULL, \n);");
                    sc.createNewTable("CREATE TABLE IF NOT EXISTS LoginPassword (\n INT PRIMARY KEY NOT NULL,\n Player text NOT NULL,\n Password text,\n);");
                    getConfig().set("FirstRun",false);
                }else{
                // db parameters
                String url = "jdbc:sqlite:"+new File(getDataFolder(),"UltraServer.db").getPath();
                // create a connection to the database
                conn = DriverManager.getConnection(url);
                getLogger().warning("Connection to SQLite has been established, URL:"+url);
                sc.setSQLiteConnection(conn);
                }
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
            getLogger().info("正在加载反脏话机制");
            acc.config=getConfig();
            getLogger().info("反脏话机制已启动");
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
        getLogger().info("正在加载玩家登录功能");
        pl.setConfig(getConfig());
        pl.setLoginConfig(YamlConfiguration.loadConfiguration(new File(getDataFolder(),"LoginMsg.yml")));
        getLogger().info("插件已加载AwA 作者奶茶 QQ3520568665");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        PlayerTeleportCachePool ptcp =new PlayerTeleportCachePool();
        ptcp.del(1);
        ptcp.del(2);
        ptcp.del(3);
        getLogger().info("TPA功能已关闭");
        PlayerLoginCommandHandler plc = new PlayerLoginCommandHandler();
        plc.map = new HashMap<>();
        getLogger().info("插件已经被卸载了，感谢你的使用UwU");
    }

    static AtomicInteger atomicInteger =new AtomicInteger();

    public static void setAtom(){
        atomicInteger.getAndIncrement();
    }

    public static int getAtom(){
        return atomicInteger.get();
    }
}
