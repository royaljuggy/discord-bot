package me.discordBot.commands;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageEmbed;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;



public class ChatFilter extends ListenerAdapter {

    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {

        String messageSent = event.getMessage().getContentRaw().toLowerCase(); //stores the message sent
        String checkMsg = messageSent.replaceAll("[^A-Za-z]", "");


        //language filter; replace string parameter with any unwanted word
        if (checkMsg.indexOf("nigger") != -1) {
            //messageSent = messageSent.replaceAll("nigger", "neighbour").queue();

            event.getChannel().sendMessage("You can't say that!").queue();

            //event.getMessage().delete().queue();

            //event.getMessage().editMessage(cs);
        }

        if (messageSent.indexOf("moan") != -1) {

            EmbedBuilder eb = new EmbedBuilder();
            eb.setTitle("Y'all say moan?");
            eb.setImage("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRBXGRyufLKqepOZ-NZmn4gda-91y1b-VcIvYyP5LSyzzS-CbMi0w");
            event.getChannel().sendMessage(eb.build()).queue();

            //event.getChannel().sendMessage("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRBXGRyufLKqepOZ-NZmn4gda-91y1b-VcIvYyP5LSyzzS-CbMi0w").queue();

        }
    }
}
