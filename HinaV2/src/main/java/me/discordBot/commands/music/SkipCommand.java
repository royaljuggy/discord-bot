package me.discordBot.commands.music;

import me.discordBot.Constants;
import me.discordBot.music.PlayerManager;
import me.discordBot.objects.ICommand;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.VoiceChannel;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.managers.AudioManager;

import java.util.List;

public class SkipCommand implements ICommand {

    @Override
    public void handle(List<String> args, GuildMessageReceivedEvent event) {
        PlayerManager manager = PlayerManager.getInstance();

        manager.getGuildMusicManager(event.getGuild()).scheduler.nextTrack();
        event.getChannel().sendMessage("Skipping current song").queue();
    }

    @Override
    public String getHelp() {
        return "Skips the current song\n" +
        "Usage: `" + Constants.PREFIX + getInvoke() + "`";
    }

    @Override
    public String getInvoke() {
        return "skip";
    }

}
