package org.refish.ultraserver;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.scoreboard.*;

import java.util.List;

public class ColorBoard {
    private YamlConfiguration config;
    private List<String> getBody =config.getStringList("body");


    public void setConfig(YamlConfiguration yc){
        this.config=yc;
    }

    public Scoreboard sendBoard(){
        ScoreboardManager manager = Bukkit.getScoreboardManager();// 建立新Scoreboard
        Scoreboard scoreboard = manager.getNewScoreboard();
        Objective objective = scoreboard.registerNewObjective("ColorBoard", "dummy", config.getString("Title"));
// 设置记分项展示位置
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        // 给记分项增加 内容与对应的分数
        //执行一个遍历
        for (String str :getBody){
            Score score = objective.getScore(str);
        }
        return scoreboard;
    }

}
