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

import static org.refish.ultraserver.Utils.Printlns.logoprint;

public final class main extends JavaPlugin implements Listener {

    //全局版本设置 每次新构建时需要修改
    static final String version ="1.7.0.5";
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
        }catch(NullPointerException e){
            //在加载命令时可能会抛出空指针异常，对其捕捉
            getLogger().warning("命令注册失败，原因：空指针");
        }finally {
            //始终打印这条信息
            getLogger().warning("插件加载完成");
        }
        if(getconfig().getBoolean("setting.anti.badword.enable")) {
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
        }else{
            String reason;
            if(! getConfig().getBoolean("setting.anti.badword.enable")){
                reason="配置文件中已经禁用反脏话";
            } else{
                reason="第一次启动或并配置文件不存在";
            }
            getLogger().info("由于配置文件原因,反脏话机制暂未加载 原因:"+reason);
        }
            getLogger().info("正在加载反加速外挂");
            getLogger().info("您其实可以在配置文件中修改检测系统的相关参数");
            getLogger().info("反加速挂加载成功");
            getLogger().info("正在加载全自动垃圾清理系统");
            AutoClean ac=new AutoClean();
            ac.config(getConfig());
            ac.start();
            getLogger().info("全自动清理启动成功！");
            getLogger().warning("由于某些原因，反外挂需要等待加载完毕后才能使用（这并不是什么BUG）");
        getLogger().info("插件已加载AwA 作者奶茶 QQ3520568665");
    }

    @EventHandler
    public void AntiSpeeded(org.bukkit.event.player.PlayerMoveEvent event){
        PacketContainer fakeExplosion = new PacketContainer(PacketType.Play.Client.ENTITY_ACTION);
        Player player= event.getPlayer();
        new Thread() {
            @Override
            public void run() {
                super.run();
                Double getX = player.getLocation().getX();
                Double getY = player.getLocation().getY();
                Double getZ = player.getLocation().getZ();
                try {
                    sleep(1000);
                } catch (InterruptedException e) {
                    getLogger().warning(e+"");
                }
                Double thgetX = player.getLocation().getX();
                Double thgetY = player.getLocation().getY();
                Double thgetZ = player.getLocation().getZ();
                if (Math.abs(thgetX - getX) < getconfig().getInt("setting.anti.chest.AntiSpeed.XLong") && Math.abs(thgetZ - getZ) < getconfig().getInt("setting.anti.chest.AntiSpeed.ZLong") && Math.abs(thgetY - getY) < getconfig().getInt("setting.anti.chest.AntiSpeed.YLong") && Math.sqrt(getX * getX + getZ * getZ) < getconfig().getInt("setting.anti.chest.AntiSpeed.LongJumpLong") || player.isOp()) {
                    //这是检测通过的结果区
                    //检测1 在一秒内直线运动速度是否超过正常数值(x轴8.1格/秒,Y轴4.2格/秒,Z轴8.5格/秒)
                    //检测2 假设玩家进行斜线运动，构建直角三角形模型，进行速度判定
                } else {
                    //这里是检测不通过的结果区
                    //会进行第二次采样测试
                    Double secgetX = player.getLocation().getX();
                    Double secgetY = player.getLocation().getY();
                    Double secgetZ = player.getLocation().getZ();
                    try {
                        sleep(1000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    Double secthgetX = player.getLocation().getX();
                    Double secthgetY = player.getLocation().getY();
                    Double secthgetZ = player.getLocation().getZ();
                    if (Math.abs(secthgetX - secgetX) < getconfig().getInt("setting.anti.chest.AntiSpeed.XLong") && Math.abs(secthgetZ - secgetZ) < getconfig().getInt("setting.anti.chest.AntiSpeed.ZLong") && Math.abs(secthgetY - secgetY) < getconfig().getInt("setting.anti.chest.AntiSpeed.YLong") && Math.sqrt(secgetX * secgetX + secgetZ * secgetZ) < getconfig().getInt("setting.anti.chest.AntiSpeed.LongJumpLong")) {
                        //第二次采样测试和第一次的方法一样
                        //判断结果是否异常
                        //出现异常时，向后退步
                        //以下是向后退的ProtocolLib代码
                        fakeExplosion.getDoubles()
                                .write(0, player.getLocation().getX())
                                .write(1, player.getLocation().getY())
                                .write(2, player.getLocation().getZ());
                        fakeExplosion.getFloat().write(0, 3.0F);
                        fakeExplosion.getBlockPositionCollectionModifier().write(0, new ArrayList<>());
                        fakeExplosion.getVectors().write(0, player.getVelocity().add(new Vector(1, 1, 1)));

                        try {
                            protocolManager.sendServerPacket(player, fakeExplosion);
                        } catch (InvocationTargetException e) {
                            getLogger().warning(e+"");
                        }
                    }
                }
            }
        }.start();
        fakeExplosion.getDoubles()
                .write(0, player.getLocation().getX())
                .write(1, player.getLocation().getY())
                .write(2, player.getLocation().getZ());
        fakeExplosion.getFloat().write(0, 3.0F);
        fakeExplosion.getBlockPositionCollectionModifier().write(0, new ArrayList<>());
        fakeExplosion.getVectors().write(0, player.getVelocity().add(new Vector(1, 1, 1)));

        try {
            protocolManager.sendServerPacket(player, fakeExplosion);
        } catch (InvocationTargetException e) {
            getLogger().warning(e+"");
        }
    }


    @Override
    public void onDisable() {
        // Plugin shutdown logic
        getLogger().info("插件已经被卸载了，感谢你的使用UwU");
    }


    @NotNull
    public FileConfiguration getconfig() {
        return getConfig();
    }
}
