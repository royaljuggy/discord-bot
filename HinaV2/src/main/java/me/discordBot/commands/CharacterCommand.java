package me.discordBot.commands;

import com.github.Doomsdayrs.Jikan4java.connection.Anime.AnimeConnection;
import com.github.Doomsdayrs.Jikan4java.connection.Character.CharacterConnection;
import com.github.Doomsdayrs.Jikan4java.types.Main.Character.Character;
import com.github.Doomsdayrs.Jikan4java.types.Main.Anime.Anime;
import me.discordBot.objects.ICommand;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public class CharacterCommand implements ICommand {

    public void handle(List<String> args, GuildMessageReceivedEvent event) {

        AnimeConnection animeConnection = new AnimeConnection();
        CharacterConnection charConnect = new CharacterConnection();

        try {
            String message = event.getMessage().getContentRaw().substring(11);

            //animeConnection.searchAnimeById(1).thenAccept(System.out::println); //example or template perse

            //animeConnection.search(message).thenAccept(System.out::println); // prints everything about the anime searched

            //THE GOOD STUFF
            CompletableFuture<Character> search = charConnect.search(message);

            TimeUnit.SECONDS.sleep(1);

            //event.getChannel().sendMessage((search.join().url)).queue();

            EmbedBuilder eb = new EmbedBuilder();
            eb.setTitle(search.join().name);
            eb.setImage(search.join().image_url);

            String print = "";

            StringBuilder builder = new StringBuilder();
            String text = "";

            //to print all the character's anime appearances (since these values are held in a huge arraylist?)
            for (int x = 0; x < search.join().animeography.size(); x++) {
                builder.append(search.join().animeography.get(x).name + "\n");
            }

            text = builder.toString();

            print += "**Anime: **\n" + text + "\n"; //adds all of the anime appearances to the description

            text = "N/A"; //change str to none for the case if the character has no nicknames (see below)

            //to print all the character's nicknames
            builder = new StringBuilder();

            if (search.join().nicknames.size() != 0) {
                for (int x = 0; x < search.join().nicknames.size(); x++) {
                    builder.append(search.join().nicknames.get(x) + "\n");
                }

                text = builder.toString();
            }


            print += "**Nicknames: **" + text + "\n";

            print += "**Japanese Voice Actor: **" + search.join().voice_actors.get(0).name + "\n";
            eb.setDescription(print);
            event.getChannel().sendMessage(eb.build()).queue();

            //System.out.println(search); //prints everything about the completable future
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //new Connector().seasonLater().thenAccept(System.out::println);

    }

    @Override
    public String getHelp() {
        return "Gets information about the specified character (from an anime). \n\t" +
                "**Note: **If the anime has multiple voice actors, only the FIRST will be printed.";
    }

    @Override
    public String getInvoke() {
        return "character";
    }
}
