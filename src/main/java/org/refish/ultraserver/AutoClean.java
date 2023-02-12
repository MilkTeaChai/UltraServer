package org.refish.ultraserver;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.*;

import java.util.concurrent.atomic.AtomicInteger;

import static java.lang.Thread.sleep;

public class AutoClean implements Runnable{

    FileConfiguration config;

    AtomicInteger num = new AtomicInteger();
    public void config(FileConfiguration config){
        this.config=config;
    }
    public void run() {
                 while (true) {
                     try{
                         sleep(config.getInt("setting.AutoClean.CleanTime") * 1000);
                         Bukkit.broadcastMessage(this.config.getString("setting.AutoClean.CleanMsgAt60s"));
                         sleep(50000);
                         Bukkit.broadcastMessage(config.getString("setting.AutoClean.CleanMsgAt10s"));
                         sleep(5000);
                         Bukkit.broadcastMessage(config.getString("setting.AutoClean.CleanMsgCountdown") + "5!!");
                         sleep(1000);
                         Bukkit.broadcastMessage(config.getString("setting.AutoClean.CleanMsgCountdown") + "4!!");
                         sleep(1000);
                         Bukkit.broadcastMessage(config.getString("setting.AutoClean.CleanMsgCountdown") + "3!!");
                         sleep(1000);
                         Bukkit.broadcastMessage(config.getString("setting.AutoClean.CleanMsgCountdown") + "2!!");
                         sleep(1000);
                         Bukkit.broadcastMessage(config.getString("setting.AutoClean.CleanMsgCountdown") + "1!!");
                         sleep(1000);
                         Bukkit.broadcastMessage(config.getString("setting.AutoClean.CleanMsgNow"));
                         sleep(1000);
                         Bukkit.broadcastMessage(config.getString("setting.AutoClean.CleanMsgWhenOver").replace('#', (char) Utils.AutoCleanMethod()));
                 } catch(InterruptedException e){
                     throw new RuntimeException(e);
                 }
             }
        }
}