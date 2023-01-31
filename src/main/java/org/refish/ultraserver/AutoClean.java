package org.refish.ultraserver;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.*;

import java.io.File;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

import org.refish.ultraserver.Utils;

public class AutoClean extends Thread{
    int itemnum;
    public void run() {

        File file = new File("./UltraServer","config.yml");
        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
        while (true){
    AtomicInteger num = new AtomicInteger();
         Bukkit.getWorlds().forEach(world -> world.getEntities().forEach(entity -> {
             try {sleep(config.getInt("setting.AutoClean.CleanTime")* 1000);
             Bukkit.broadcastMessage(config.getString("setting.AutoClean.CleanMsgAt60s"));
             sleep(60000);
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
             boolean itemRules = entity instanceof Item && !(entity instanceof Player) && !(entity instanceof Animals) && !(entity instanceof Monster) && !entity.isDead();
             if (itemRules) {
            entity.remove();
            num.getAndIncrement();
             }
             Bukkit.broadcastMessage(Objects.requireNonNull(config.getString("setting.AutoClean.CleanMsgWhenOver")).replace('#', (char) num.intValue()));
             } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
         }));
        }
    }
}
