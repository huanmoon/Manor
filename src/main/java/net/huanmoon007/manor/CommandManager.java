package net.huanmoon007.manor;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Objects;
import java.util.UUID;

public class CommandManager implements CommandExecutor {
    @Override
    public boolean onCommand (CommandSender sender, Command cmd, String label, String[] args){
        boolean IsDelCancelled;
        String DelManorName;
        if (cmd.getName().equalsIgnoreCase("manorcreate")){
            UUID uuid = Bukkit.getPlayerExact(sender.getName()).getUniqueId();
            File file = new File("plugins\\Manor\\users\\"+uuid+".yml");
            YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
            double FirstPointX = new Double(config.getString("last-first-point-x"));
            double FirstPointZ = new Double(config.getString("last-first-point-z"));
            double SecondPointX = new Double(config.getString("last-second-point-x"));
            double SecondPointZ = new Double(config.getString("last-second-point-z"));
            if (Math.abs(SecondPointX-FirstPointX) <= 16 || Math.abs(FirstPointZ-SecondPointZ) <= 16){
                sender.sendMessage(ChatColor.AQUA+"Manor's width and height must > 16");

                return false;
            }
            /*
            FirstPointX = (int)Math.floor(FirstPointX);
            FirstPointZ = (int)Math.floor(FirstPointZ);
            SecondPointX = (int)Math.floor(SecondPointX);
            SecondPointZ = (int)Math.floor(SecondPointZ);
            */
            int FirstChunkX = (int)Math.floor(FirstPointX)/16;
            int FirstChunkZ = (int)Math.floor(FirstPointZ)/16;
            int SecondChunkX = (int)Math.floor(SecondPointX)/16;
            int SecondChunkZ = (int)Math.floor(SecondPointZ)/16;
            File ManorDir = new File("plugins\\Manor\\manors");
            File ManorFile = new File("plugins\\Manor\\manors\\"+args[2]+".yml");
            //File UserFile = new File("Manor\\manors\\"+uuid+".yml");
            ManorDir.mkdir();
            if (ManorFile.exists()){
                sender.sendMessage(ChatColor.AQUA+"There is already a manor named that.");
            }
            else {
                try (FileWriter fileWriter = new FileWriter("plugins\\Manor\\manors\\"+args[2]+".yml")){
                    ManorFile.createNewFile();
                   //sender.sendMessage("Your manor "+args[2]+" create successfully!");
                    fileWriter.append("manor-name: "+args[2]+"\r\n"
                            +"the-first-point-x: "+FirstPointX+"\r\n"
                            +"the-first-point-z: "+FirstPointZ+"\r\n"
                            +"the-first-chunk-x: "+FirstChunkX+"\r\n"
                            +"the-first-chunk-z: "+FirstChunkZ+"\r\n"
                            +"the-second-point-x: "+SecondPointX+"\r\n"
                            +"the-second-point-z: "+SecondChunkZ+"\r\n"
                            +"the-second-chunk-x: "+SecondChunkX+"\r\n"
                            +"the-second-chunk-z: "+SecondChunkZ+"\r\n"
                            +"the-lower-y: "+args[0]+"\r\n"
                            +"the-highest-y: "+args[1]+"\r\n"
                            +"owner: "+uuid+"\r\n"
                            +"\r\n");
                    fileWriter.flush();
                    MiscManager fileWriter2 = new MiscManager();
                    fileWriter2.ModifyFileLine("plugins\\Manor\\manors\\"+args[2]+".yml",13,"owner-last-account-name: "+sender.getName());
                    File ChunkDir = new File("plugins\\Manor\\chunks");
                    ChunkDir.mkdir();
                    System.out.println("FCX:"+FirstChunkX);
                    System.out.println("SCX:"+SecondChunkX);
                    System.out.println(FirstChunkX < SecondChunkX);
                    for (int i = FirstChunkX;i < SecondChunkX;i++){
                        System.out.println("i:"+i);
                        for (int j = FirstChunkZ;j > SecondChunkZ;j--){
                            System.out.println("j:"+j);
                            File ChunkFile = new File("plugins\\Manor\\chunks\\"+i+"_"+j+".yml");
                            ChunkFile.createNewFile();
                            FileWriter fileWriter3 = new FileWriter("plugins\\Manor\\chunks\\"+i+"_"+j+".yml");
                            fileWriter3.append("manor: "+args[2]);
                            fileWriter3.flush();
                        }
                    }
                    sender.sendMessage(ChatColor.AQUA+args[2]+" create successfully!");
                }
                catch (IOException exception){
                    sender.sendMessage(ChatColor.AQUA+"Your manor "+args[2]+" create failed!This is an unknown bug,report it to huanmoon007!");
                    try {
                        throw exception;
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }

            return true;
        }

        else if (cmd.getName().equalsIgnoreCase("manordel")){
            UUID uuid = Objects.requireNonNull(Bukkit.getPlayerExact(sender.getName())).getUniqueId();
            sender.sendMessage(uuid);
            System.out.println(uuid);
            File file = new File("plugins\\Manor\\manors\\"+args[0]+".yml");
            YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
            UUID uuid2 = UUID.fromString(Objects.requireNonNull(config.getString("owner")));
            //sender.sendMessage("test114514");
            //sender.sendMessage(Objects.requireNonNull(config.getString("owner")));
            if (uuid.equals(uuid2)){
                sender.sendMessage(ChatColor.AQUA+"use /manordelconfirm to confirm that!Or use /manordelcancel to cancel that!");
                DelManorName = args[0];
                IsDelCancelled = false;
                System.out.println(IsDelCancelled);
                MiscManager fileWriter = new MiscManager();
                fileWriter.ModifyFileLine("plugins\\Manor\\users\\"+uuid+".yml",7,"is-del-cancelled: "+ IsDelCancelled);
                fileWriter.ModifyFileLine("plugins\\Manor\\users\\"+uuid+".yml",8,"del-manor-name: "+args[0]);
            }
            else {
                sender.sendMessage(ChatColor.AQUA+"You are not the owner of this manor!");
            }
            return true;
        }

        else if (cmd.getName().equalsIgnoreCase("manordelconfirm")){
            UUID uuid = Objects.requireNonNull(Bukkit.getPlayerExact(sender.getName())).getUniqueId();
            File UserFile = new File("plugins\\Manor\\users\\"+uuid+".yml");
            YamlConfiguration config = YamlConfiguration.loadConfiguration(UserFile);
            IsDelCancelled = config.getBoolean("is-del-cancelled");
            System.out.println(IsDelCancelled);
            sender.sendMessage("test0");
            if (!IsDelCancelled){
                DelManorName = config.getString("del-manor-name");
                File file = new File("plugins\\Manor\\manors\\"+ DelManorName +".yml");
                file.delete();
                MiscManager fileWriter = new MiscManager();
                IsDelCancelled = true;
                fileWriter.ModifyFileLine("plugins\\Manor\\users\\"+uuid+".yml",7,"is-del-cancelled: "+ IsDelCancelled);
                sender.sendMessage(ChatColor.AQUA+"Manor delete successfully.");

                return true;
            }
            else {
                return false;
            }
        }

        else if (cmd.getName().equalsIgnoreCase("manordelcancel")){
            UUID uuid = Objects.requireNonNull(Bukkit.getPlayerExact(sender.getName())).getUniqueId();
            File UserFile = new File("plugins\\Manor\\users\\"+uuid+".yml");
            YamlConfiguration config = YamlConfiguration.loadConfiguration(UserFile);
            IsDelCancelled = config.getBoolean("is-del-cancelled");
            if (!IsDelCancelled){
                MiscManager fileWriter = new MiscManager();
                IsDelCancelled = true;
                fileWriter.ModifyFileLine("plugins\\Manor\\users\\"+uuid+".yml",7,"is-del-cancelled: "+ IsDelCancelled);
                sender.sendMessage(ChatColor.AQUA+"Delete request cancelled successfully.");
                return true;
            }
            else {
                return false;
            }
        }

        return false;
    }
}
