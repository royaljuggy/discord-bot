package me.discordBot.commands;

import me.discordBot.Constants;
import me.discordBot.objects.ICommand;
import net.aksingh.owmjapis.api.APIException;
import net.aksingh.owmjapis.core.OWM;
import net.aksingh.owmjapis.model.CurrentWeather;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

import java.util.List;

public class WeatherCommand implements ICommand {

    // https://bitbucket.org/aksinghnet/owm-japis/src/master/ (OWM JAVA WRAPPER)
    public void handle(List<String> args, GuildMessageReceivedEvent event) {
        OWM owm = new OWM("9275848c961124a399c43fe67c794743");
        String message = event.getMessage().getContentRaw().substring(9);
        try {

            CurrentWeather cwd = owm.currentWeatherByCityName(message);

            // checking data retrieval was successful or not
            if (cwd.hasRespCode() && cwd.getRespCode() == 200) {

                long currentTemp = Math.round(cwd.getMainData().getTemp() - 273.15);
                event.getChannel().sendMessage("**Current Temperature in " + message + ": **" + currentTemp + "°C").queue();

            }

        } catch (APIException e) {
            e.printStackTrace();
            event.getChannel().sendMessage("Data retrieval unsuccessful. Please double-check your parameters.").queue();
        }


    }

    @Override
    public String getHelp() {
        return "Gets the weather \n" +
                "Usage: `" + Constants.PREFIX + getInvoke() + " [city]`";
    }

    @Override
    public String getInvoke() {

        return "weather";
    }
}
