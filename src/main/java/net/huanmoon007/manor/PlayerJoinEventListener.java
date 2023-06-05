package net.huanmoon007.manor;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.io.File;
import java.io.FileWriter;
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
                    +"\r\n"
                    +"\r\n"
                    +"\r\n"
                    +"\r\n"
                    +"\r\n"
                    +"\r\n"
                    +"\r\n"
                    +"\r\n"
                    +"\r\n");
            MiscManager fileWriter2 = new MiscManager();
            fileWriter2.ModifyFileLine("plugins\\Manor\\users\\"+uuid+".yml",2,"last-account-name: "+event.getPlayer().getName());
            fileWriter.flush();
        }
        catch (Exception exception){
            event.getPlayer().sendMessage("UserFile create failed!Report to huanmoon007.");
        }
    }
}
