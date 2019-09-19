package me.discordBot;


import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.*;
import net.dv8tion.jda.core.events.ReadyEvent;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Listener extends ListenerAdapter {

    private final CommandManager manager;
    private final Logger logger = LoggerFactory.getLogger(Listener.class);

    Listener(CommandManager manager) {
        this.manager = manager;
    }

    @Override
    public void onReady(ReadyEvent event) {
        logger.info("Logged in as %#s", event.getJDA().getSelfUser());
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {

        User author = event.getAuthor();
        Message message = event.getMessage();
        String content = message.getContentDisplay();

        if (event.isFromType(ChannelType.TEXT)) {

            Guild guild = event.getGuild();
            TextChannel textChannel = event.getTextChannel();

            //commented out so that console is not spammed with text messages
            //logger.info("(%s)[%s]<%#s>: %s", guild.getName(), textChannel.getName(), author, content);
        } else if (event.isFromType(ChannelType.PRIVATE)) {
            //read above
            //logger.info("[PRIV]<%#s>: %s", author, content);
        }
    }

    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {

        if (event.getMessage().getContentRaw().equalsIgnoreCase(Constants.PREFIX  + "shutdown") && event.getAuthor().getIdLong() == Constants.OWNER) {
            shutdown(event.getJDA());
            return;
        }

        if(!event.getAuthor().isBot() && !event.getMessage().isWebhookMessage() && event.getMessage().getContentRaw().startsWith(Constants.PREFIX)) {
            manager.handleCommand(event);
        }

    }

    private void shutdown(JDA jda) {
        jda.shutdown();
    }
}
