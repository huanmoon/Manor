package net.huanmoon007.manor;

import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class ManorEventListener implements Listener {
    int ChunkX;
    int ChunkZ;
    @EventHandler
    public void onBlockBreak (BlockBreakEvent event){
        Location location = event.getPlayer().getLocation();
        ChunkX = (int)Math.floor(location.getX() / 16);
        ChunkZ = (int)Math.floor(location.getZ() / 16);
        System.out.println("PlayerX:"+location.getX());
        System.out.println("PlayerZ:"+location.getZ());
        String FileName = ChunkX+"_"+ChunkZ+".yml";
        File BaseDir = new File("plugins\\Manor\\chunks");
        File file;
        File[] files = BaseDir.listFiles();
        for (File value : files) {
            file = value;
            System.out.println(FileName);
            System.out.println(file.getName());
            if (FileName.equals(file.getName())) {
                UUID uuid = event.getPlayer().getUniqueId();
                YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
                File ManorFile = new File("plugins\\Manor\\manors\\"+config.getString("manor")+".yml");
                config = YamlConfiguration.loadConfiguration(ManorFile);
                System.out.println(ManorFile.getName());
                UUID uuid2 = UUID.fromString(Objects.requireNonNull(config.getString("owner")));
                if (uuid.equals(uuid2)){
                    event.getPlayer().sendMessage("test2");
                    /*
                    World world = Bukkit.getWorld("world");
                    Location BlockLocation = event.getBlock().getLocation();
                    Block block = BlockLocation.getBlock();
                    Material material = event.getBlock().getType();
                    block.setType(material);
                    System.out.println(material);
                    */
                    event.setCancelled(true);
                    event.getPlayer().sendMessage(ChatColor.AQUA+"You are not the owner of this manor!");
                }
                break;
            }
        }
    }
}
