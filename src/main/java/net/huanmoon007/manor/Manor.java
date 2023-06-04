/*
|----|----|    |    |\   | |----| |----|
|    |    |   | |   | \  | |    | |----|
|    |    |  |---|  |  \ | |    | |  |
|    |    | |     | |   \| |----| |    |

by huanmoon007(AchieveVoid)
Inspired by RESIDENCE
*/
package net.huanmoon007.manor;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

public final class Manor extends JavaPlugin {
    @Override
    public void onEnable() {
        // Plugin startup logic
        System.out.println("\n"+
                "|----|----|    |    |\\   | |----| |----|\n" +
                "|    |    |   | |   | \\  | |    | |----|\n" +
                "|    |    |  |---|  |  \\ | |    | |  |\n" +
                "|    |    | |     | |   \\| |----| |    |\n" +
                "\n" +
                "by huanmoon007(AchieveVoid)\n" +
                "Inspired by RESIDENCE");
        File UserDir = new File("plugins\\Manor");
        //File UserFile = new File("plugins\\Manor\\ManorList.yml");
        UserDir.mkdir();
        //UserFile.createNewFile();
        Bukkit.getPluginCommand("manorcreate").setExecutor(new CommandManager());
        Bukkit.getPluginCommand("manordel").setExecutor(new CommandManager());
        Bukkit.getPluginCommand("manordelconfirm").setExecutor(new CommandManager());
        Bukkit.getPluginCommand("manordelcancel").setExecutor(new CommandManager());
        Bukkit.getPluginManager().registerEvents(new ManorCreateEventListener(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerJoinEventListener(),this);
        Bukkit.getPluginManager().registerEvents(new ManorEventListener(),this);
    }

    @Override
    public void onDisable() {
       // Plugin shutdown logic
    }
}