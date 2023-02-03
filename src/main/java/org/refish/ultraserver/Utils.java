package org.refish.ultraserver;

/*
这是一个常用命令集合
请开发者不要随意调用此处的任何方法
最后修改版本: dev_1.6-Beta_ReBuild-3
已优化代码:yes
*/

import org.bukkit.Bukkit;
import org.bukkit.command.SimpleCommandMap;
import org.bukkit.entity.Animals;
import org.bukkit.entity.Item;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.plugin.*;

import java.io.File;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.SortedSet;

import static org.bukkit.Bukkit.getLogger;

public class Utils {
    public static void AutoCleanMethod(){
        Bukkit.getWorlds().forEach(world -> world.getEntities().forEach(entity -> {
            if (entity instanceof Item && !(entity instanceof Player) && !(entity instanceof Animals) && !(entity instanceof Monster) && !entity.isDead()){
                entity.remove();
                main.setAtom();
            }
        }));
    }
    public static class Plugins{
        public static Plugin getPluginByName(String name) {
            for (Plugin plugin : Bukkit.getPluginManager().getPlugins()) {
                if (name.equalsIgnoreCase(plugin.getName())) {
                    return plugin;
                }
            }
            return null;
        }

        public static void enable(Plugin plugin) {
            if (plugin != null && !plugin.isEnabled()) {
                Bukkit.getPluginManager().enablePlugin(plugin);
            }
        }
        public static void disable(Plugin plugin) {
            if (plugin != null && plugin.isEnabled()) {
                Bukkit.getPluginManager().disablePlugin(plugin);
            }
        }
        public static String load(String name) {

            Plugin target;

            File pluginDir = new File("plugins");

            if (!pluginDir.isDirectory()) {
                return "NF";
            }

            File pluginFile = new File(pluginDir, name + ".jar");

            if (!pluginFile.isFile()) {
                for (File f : Objects.requireNonNull(pluginDir.listFiles())) {
                    if (f.getName().endsWith(".jar")) {
                        pluginFile = f;
                        break;
                    }
                }
            }

            try {
                target = Bukkit.getPluginManager().loadPlugin(pluginFile);
            } catch (InvalidDescriptionException e) {
                e.printStackTrace();
                return "ID";
            } catch (InvalidPluginException e) {
                e.printStackTrace();
                return "IP";
            }

            Objects.requireNonNull(target).onLoad();
            Bukkit.getPluginManager().enablePlugin(target);

            return "OK";

        }
        public static String unload(Plugin plugin) {

            String name = plugin.getName();

            PluginManager pluginManager = Bukkit.getPluginManager();

            List<Plugin> plugins;

            Map<String, Plugin> names;
            Map<Event, SortedSet<RegisteredListener>> listeners = null;

            boolean reloadlisteners = true;

            pluginManager.disablePlugin(plugin);

            try {

                Field pluginsField = Bukkit.getPluginManager().getClass().getDeclaredField("plugins");
                pluginsField.setAccessible(true);
                plugins = (List<Plugin>) pluginsField.get(pluginManager);

                Field lookupNamesField = Bukkit.getPluginManager().getClass().getDeclaredField("lookupNames");
                lookupNamesField.setAccessible(true);
                names = (Map<String, Plugin>) lookupNamesField.get(pluginManager);

                try {
                    Field listenersField = Bukkit.getPluginManager().getClass().getDeclaredField("listeners");
                    listenersField.setAccessible(true);
                    listeners = (Map<Event, SortedSet<RegisteredListener>>) listenersField.get(pluginManager);
                } catch (Exception e) {
                    reloadlisteners = false;
                }

                Field commandMapField = Bukkit.getPluginManager().getClass().getDeclaredField("commandMap");
                commandMapField.setAccessible(true);

                Field knownCommandsField = SimpleCommandMap.class.getDeclaredField("knownCommands");
                knownCommandsField.setAccessible(true);

            } catch (NoSuchFieldException | IllegalAccessException e) {
                e.printStackTrace();
                return "FAILED";
            }

            pluginManager.disablePlugin(plugin);

            if (plugins != null)
                plugins.remove(plugin);

            if (names != null)
                names.remove(name);

            if (listeners != null && reloadlisteners) {
                for (SortedSet<RegisteredListener> set : listeners.values()) {
                    set.removeIf(value -> value.getPlugin() == plugin);
                }
            }


            return "OK";
        }
    }
    public static class Printlns{
        public static void logoprint(){
            getLogger().info("                              \\\\@@@@@@@@@@@@@@@`                                                                                                               \\s\n" +
                    "                                     *\\\\`/@@@@@/`       [\\\\@@@^                                                                                                                 \\s\n" +
                    "                                 *,*^ @@@@@/              ,@@\\\\                                                                                                              \\s\n" +
                    "                               *^^ =@@@@@/              ,/@@@@@@@@@@]                                                                                                      \\s\n" +
                    "                          *,[\\\\*\\\\^*=@/[[`             ]@@/[`    ,[\\\\@@@@@\\\\                                                                                                   \\s\n" +
                    "                        /*\\\\,`,` = ]@@@@`           =[               ,\\\\@@@^                                                                                             \\s\n" +
                    "                     =* =`]`,`]@@@@@@@`                                                                                                                              \\s\n" +
                    "                    `/**^  [/@@@@@[      @@@    ,@@/ =@@`    =@@@@@@@@@@ @@@@@@@@@@`    =@@@@*=* ,@@@@@@@@@@  ,@@@@@@@@`,@@@@@@@@@@ =@@@     /@@` ,@@@@@@@@ =@@@@@@@@@\\\\   *`/**^  [/`  \\s\n" +
                    "                    ^\\\\*`^ =@@@@@`       =@@^    /@@` @@/         @@/    =@@^    @@@^  */@@@@@\\\\=* @@@         /@@^       /@@`    @@@  @@@   ,@@@  @@@`       @@@    =@@/*  *^\\\\*`^       \\s\n" +
                    "                         ,@@@@@`        @@@    ,@@/ =@@`        =@@`    @@@@@@@@@@`=*]@@@ =@@^   @@@@@@@@@\\\\ =@@@@@@@@@`,@@@@@@@@@@`  \\\\@@^ =@@/  @@@@@@@@@@ =@@@@@@@@@@]*`              \\s\n" +
                    "                         =@@@@/        =@@^    /@@` @@@         @@/    =@@/[[\\\\@@@^]^=@@@@@@@@@         [@@@ @@@^       /@@[[[@@@@`   =@@^/@@`   @@@        @@@[\\\\[@@@@` ,`,*            \\s\n" +
                    "                         =@@@@@        =@@@]]]@@@` =@@@\\\\]]]`   =@@`    @@@    =@@\\\\*/@@/[[[[@@@` ]]]]]]]@@@^ =@@@\\\\]]]] ,@@/    =@@^    @@@@/     @@@@]]]]] =@@^*`=*@@@`*                \\s\n" +
                    "                          \\\\@@@@\\\\        ,\\\\@@@@[     ,\\\\@@@@@`   @@/    =@@^*` `=@@^@@@`     @@@^=@@@@@@@@[    ,[@@@@@/ =@@`    @@@`    =@@^       [@@@@@@^ @@@,*\\\\ ,@@@`                 \\s");
        }

        public static String helpmsg= "                §5=========[§bUltraServer§5]=========\n" +
                "                          §3主命令 /us\n" +
                "                          §3显示服务器实时TPS /us tps\n" +
                "                          §3执行服务器掉落物清理(游戏内) /us clean item\n" +
                "                          §3执行服务器GC清理 /us clean ram\n" +
                "                          §3执行服务器白名单并踢出所有玩家 /us whitelist on/off\n" +
                "                          §3服务器所用内存查询(仅限OP) /ram 或/rc\n" +
                "                          §3热加载/卸载插件 /us pl load/unload/enable/disable卸载一个世界/us ulworld\n" +
                "                          §3查看你的环境/us env\n" +
                "                          §3传送到一个人那里/tpa 传送一个人到自己这里/tpahere 创造/gmc 生成/gms 冒险/gma (都只能对自己使用) /gm 0/1/2s\n" +
                "                          §3创建家/sethome 创建出生点/setspawn";
        }
    }

