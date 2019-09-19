package me.discordBot;

import me.discordBot.commands.ChatFilter;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.entities.Game;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.security.auth.login.LoginException;
import java.util.ArrayList;

//open weather network api key: 9275848c961124a399c43fe67c794743

public class Bot {

    private Bot() {

        ArrayList arr = new ArrayList();

        CommandManager commandManager = new CommandManager();
        Listener listener = new Listener(commandManager);
        Logger logger = LoggerFactory.getLogger(Bot.class);

        try {
            logger.info("Booting");
            new JDABuilder(AccountType.BOT).addEventListener(new ChatFilter()) //added eventlistener since chatfilter does not implement ICommand interface
                    .setToken(Secret.TOKEN)
                    .setGame(Game.playing("anime"))
                    .addEventListener(listener)
                    .build().awaitReady();
            logger.info("Running");
        } catch (LoginException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Bot();

    }
}
