package me.discordBot.commands.music;

import me.discordBot.Constants;
import me.discordBot.music.PlayerManager;
import me.discordBot.objects.ICommand;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

import java.util.List;


public class VolumeCommand implements ICommand {

    public void handle(List<String> args, GuildMessageReceivedEvent event) {

        String msg = event.getMessage().getContentRaw();
        TextChannel channel = event.getChannel();

        PlayerManager manager = PlayerManager.getInstance();

        //sets default volume ONCE, un-needed
//        if (manager.getGuildMusicManager(event.getGuild()).player.getVolume() == 0) {
//            manager.getGuildMusicManager(event.getGuild()).player.setVolume(100);
//        }
        int volume = -1;

        try {

            volume = Integer.parseInt(msg.substring(8)); //gets volume

            if (volume >= 0) { //changes volume
                manager.getGuildMusicManager(event.getGuild()).player.setVolume(volume); //sets volume
                channel.sendMessage("Volume set to " + volume + ".").queue(); //prints message stating successful volume change
            } else { //if a negative integer is passed
                channel.sendMessage("Are you sure that your parameter is a positive INTEGER?").queue();
            }

        } catch (StringIndexOutOfBoundsException e) { //throws exception if no parameters were sent after !volume; leads bot to display current volume
            e.printStackTrace();
            channel.sendMessage("Current volume: " + manager.getGuildMusicManager(event.getGuild()).player.getVolume() + ".").queue();
        }


    }

    @Override
    public String getHelp() {
        return "Sets the volume for the music. Leave parameter empty if you want to display the current volume.\n" +
                "Usage: `" + Constants.PREFIX + getInvoke() + " [volume amount]`";
    }

    @Override
    public String getInvoke() {

        return "volume";
    }

}
