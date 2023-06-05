package net.huanmoon007.manor;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;

import java.io.FileWriter;
import java.io.IOException;
import java.util.UUID;

public class ManorCreateEventListener implements Listener {
    public double FirstPointX;
    public double FirstPointZ;
    public double SecondPointX;
    public double SecondPointZ;

    public boolean IsFirstPointed = false;

    @EventHandler
    public void onRodUse (PlayerFishEvent event) {
        UUID uuid = Bukkit.getPlayerExact(event.getPlayer().getName()).getUniqueId();
        Location location = event.getPlayer().getLocation();
        //event.getPlayer().sendMessage("test1");
        String ItemName = event.getPlayer().getInventory().getItemInMainHand().getItemMeta().getDisplayName();
        //event.getPlayer().sendMessage(ItemName);
        if (ItemName.equals("ManorTool") && !IsFirstPointed) {
            FirstPointX = location.getX();
            FirstPointZ = location.getZ();
            IsFirstPointed = true;
            event.getPlayer().sendMessage(ChatColor.AQUA+"The first point:"+FirstPointX+" ~ "+FirstPointZ);
        }
        else if (ItemName.equals("ManorTool") && IsFirstPointed) {
            SecondPointX = location.getX();
            SecondPointZ = location.getZ();
            IsFirstPointed = false;
            event.getPlayer().sendMessage(ChatColor.AQUA+"The second point:"+SecondPointX+" ~ "+SecondPointZ);
            if (FirstPointX > SecondPointX){
                double swap = FirstPointX;
                FirstPointX = SecondPointX;
                SecondPointX = swap;
            }
            if (FirstPointZ < SecondPointZ){
                double swap = FirstPointZ;
                FirstPointZ = SecondPointZ;
                SecondPointZ = swap;
            }
            MiscManager fileWriter = new MiscManager();
            fileWriter.ModifyFileLine("plugins\\Manor\\users\\"+uuid+".yml",3,"last-first-point-x: "+FirstPointX);
            fileWriter.ModifyFileLine("plugins\\Manor\\users\\"+uuid+".yml",4,"last-first-point-z: "+FirstPointZ);
            fileWriter.ModifyFileLine("plugins\\Manor\\users\\"+uuid+".yml",5,"last-second-point-x: "+SecondPointX);
            fileWriter.ModifyFileLine("plugins\\Manor\\users\\"+uuid+".yml",6,"last-second-point-z: "+SecondPointZ);
            /*try (FileWriter fileWriter = new FileWriter("plugins\\Manor\\users\\"+uuid+".yml",true)){
                fileWriter.append("last-first-point-x: "+FirstPointX+"\r\n"
                        +"last-first-point-z: "+FirstPointZ+"\r\n"
                        +"last-second-point-x: "+SecondPointX+"\r\n"
                        + "last-second-point-z: "+SecondPointZ+"\r\n");
                fileWriter.flush();
            }
            catch (IOException exception){

            }*/
        }
        //event.getPlayer().sendMessage(FirstPointX+" "+FirstPointZ +" "+SecondPointX+" "+SecondPointZ);
    }
}
