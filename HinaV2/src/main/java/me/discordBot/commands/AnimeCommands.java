package me.discordBot.commands;

import com.github.Doomsdayrs.Jikan4java.connection.Anime.AnimeConnection;
import com.github.Doomsdayrs.Jikan4java.types.Main.Anime.Anime;
import me.discordBot.objects.ICommand;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.sql.SQLOutput;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class AnimeCommands implements ICommand {

    @Override
    public void handle(List<String> args, GuildMessageReceivedEvent event) {

        AnimeConnection animeConnection = new AnimeConnection();

        try {
            String message = event.getMessage().getContentRaw().substring(6).toLowerCase();

            //animeConnection.searchAnimeById(1).thenAccept(System.out::println); //example or template perse

            //animeConnection.search(message).thenAccept(System.out::println); // prints everything about the anime searched

            //THE GOOD STUFF
            CompletableFuture<Anime> search = animeConnection.search(message);

            TimeUnit.SECONDS.sleep(1);

            //event.getChannel().sendMessage((search.join().url)).queue();

            EmbedBuilder eb = new EmbedBuilder();
            eb.setTitle(search.join().title_english);
            eb.setImage(search.join().imageURL);
            String print = "";
            print += "**Ranking: **" + search.join().rank;
            print += "\n**# of episodes: **" + search.join().episodes;
            print += "\n**Finished Airing? **" + (!(search.join().airing) ? ":ballot_box_with_check: " : ":x:");
            print += "\n**Premiered in** " + search.join().premiered;
            eb.setDescription(print);
            event.getChannel().sendMessage(eb.build()).queue();

            //System.out.println(search); //prints everything about the completable future
        } catch (InterruptedException | IOException | ParseException e) {
            e.printStackTrace();
        }
        //new Connector().seasonLater().thenAccept(System.out::println);

    }

    @Override
    public String getHelp() {
        return "Gets information about the specified anime";
    }

    @Override
    public String getInvoke() {
        return "anime";
    }
}
