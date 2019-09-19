package me.discordBot.commands.music;

import me.discordBot.Constants;
import me.discordBot.music.PlayerManager;
import me.discordBot.objects.ICommand;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.VoiceChannel;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.managers.AudioManager;

import java.util.List;

public class PlayCommand implements ICommand {
    public void handle(List<String> args, GuildMessageReceivedEvent event) {

        try {
            PlayerManager manager = PlayerManager.getInstance();
            String msg = event.getMessage().getContentRaw();
            TextChannel channel = event.getChannel();

            String URL = msg.substring(6); //truncates the !play and extra space, to leave only the url

            //connects the bot if the bot is not already in a channel
            VoiceChannel connectedChannel = event.getMember().getVoiceState().getChannel();
            AudioManager audioManager = event.getGuild().getAudioManager();
            audioManager.openAudioConnection(connectedChannel);

            if (msg.contains("www.") || msg.contains("?v=")) {
                manager.loadAndPlay(event.getChannel(), URL); //loads the song
            } else {
                manager.loadAndPlay(event.getChannel(), "ytsearch: " + URL );
            }
        } catch (IllegalArgumentException e) {
            event.getChannel().sendMessage("You are not in a channel!").queue();
        }



    }

    @Override
    public String getHelp() {
        return "Plays a song from youtube\n" +
                "Usage: `" + Constants.PREFIX + getInvoke() + " <song url OR song name>`";
    }

    @Override
    public String getInvoke() {
        return "play";
    }
}
