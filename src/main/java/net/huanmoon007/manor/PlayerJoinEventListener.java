package net.huanmoon007.manor;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.UUID;

public class PlayerJoinEventListener implements Listener {
    @EventHandler
    public void onPlayerJoin (PlayerJoinEvent event){
        UUID uuid = Bukkit.getPlayerExact(event.getPlayer().getName()).getUniqueId();
        File UserDir = new File("plugins\\Manor\\users");
        File UserFile = new File("plugins\\Manor\\users\\"+uuid+".yml");
        UserDir.mkdir();
        //event.getPlayer().sendMessage("test0");
        try (FileWriter fileWriter = new FileWriter("plugins\\Manor\\users\\"+uuid+".yml")){
            UserFile.createNewFile();
            fileWriter.append("uuid: "+uuid+"\r\n"
                    +"last-account-name: "+event.getPlayer().getName()+"\r\n");
        }
        catch (Exception exception){
            event.getPlayer().sendMessage("UserFile create failed!Report to huanmoon007.");
        }
    }
}
