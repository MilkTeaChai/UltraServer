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
        GetAtom gm =new GetAtom();
        gm.config(config);
        new Thread(gm,"GetAtom").start();
             try {sleep(config.getInt("setting.AutoClean.CleanTime")* 1000);
             Bukkit.broadcastMessage(this.config.getString("setting.AutoClean.CleanMsgAt60s"));
             sleep(50000);
             Bukkit.broadcastMessage(config.getString("setting.AutoClean.CleanMsgAt10s"));
             sleep(5000);
             Bukkit.broadcastMessage(config.getString("setting.AutoClean.CleanMsgCountdown")+"5!!");
             sleep(1000);
             Bukkit.broadcastMessage(config.getString("setting.AutoClean.CleanMsgCountdown")+"4!!");
             sleep(1000);
             Bukkit.broadcastMessage(config.getString("setting.AutoClean.CleanMsgCountdown")+"3!!");
             sleep(1000);
             Bukkit.broadcastMessage(config.getString("setting.AutoClean.CleanMsgCountdown")+"2!!");
             sleep(1000);
             Bukkit.broadcastMessage(config.getString("setting.AutoClean.CleanMsgCountdown")+"1!!");
             sleep(1000);
             Bukkit.broadcastMessage(config.getString("setting.AutoClean.CleanMsgNow"));
             Utils.AutoCleanMethod();
             } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
}
class GetAtom implements Runnable{
    FileConfiguration config;
    public void config(FileConfiguration config){
        this.config=config;
    }
        @Override
        public void run() {
            try {
                while (true) {
                    sleep(config.getInt("setting.AutoClean.CleanTime")* 1000+50000+5000+1000+1000+1000+1000+1000+1000);
                    Bukkit.broadcastMessage(config.getString("setting.AutoClean.CleanMsgWhenOver").replace('#', (char) main.getAtom()));
                    main.atomicInteger = new AtomicInteger();
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
