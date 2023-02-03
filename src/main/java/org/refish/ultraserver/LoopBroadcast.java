package org.refish.ultraserver;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.Random;

import static java.lang.Thread.sleep;

public class LoopBroadcast implements Runnable{
    FileConfiguration config;
    public void config(FileConfiguration config){
        this.config=config;
    }
    @Override
    public void run() {
        while (true) {
            try {
                sleep(config.getInt("setting.LoopBroadcast.TimeInterval")*1000);
                Bukkit.broadcastMessage(this.config.getString("setting.LoopBroadcast.Msg1"));
                sleep(config.getInt("setting.LoopBroadcast.TimeInterval")*1000);
                Bukkit.broadcastMessage(this.config.getString("setting.LoopBroadcast.Msg2"));
                sleep(config.getInt("setting.LoopBroadcast.TimeInterval")*1000);
                Bukkit.broadcastMessage(this.config.getString("setting.LoopBroadcast.Msg3"));
                sleep(config.getInt("setting.LoopBroadcast.TimeInterval")*1000);
                Bukkit.broadcastMessage(this.config.getString("setting.LoopBroadcast.Msg4"));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        }
}
}

