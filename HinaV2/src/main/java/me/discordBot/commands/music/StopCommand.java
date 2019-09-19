package me.discordBot.commands.music;

import me.discordBot.Constants;
import me.discordBot.music.GuildMusicManager;
import me.discordBot.music.PlayerManager;
import me.discordBot.objects.ICommand;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.managers.AudioManager;
import java.util.List;

public class StopCommand implements ICommand {

    @Override
    public void handle(List<String> args, GuildMessageReceivedEvent event) {

            PlayerManager playerManager = PlayerManager.getInstance();
            GuildMusicManager musicManager = playerManager.getGuildMusicManager(event.getGuild());
            AudioManager audioManager = event.getGuild().getAudioManager();

            musicManager.player.stopTrack();
            musicManager.player.setPaused(false);
            audioManager.closeAudioConnection(); //to disconnect from voice channel
            event.getChannel().sendMessage("Stopping the player and clearing the queue").queue();

    }

    @Override
    public String getHelp() {
        return "Stops the music player";
    }

    @Override
    public String getInvoke() {
        return "stop";
    }

}
