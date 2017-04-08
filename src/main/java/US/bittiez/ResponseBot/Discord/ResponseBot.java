package US.bittiez.ResponseBot.Discord;

import US.bittiez.ResponseBot.Discord.Config.Config;
import sx.blah.discord.api.ClientBuilder;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.util.DiscordException;

import java.io.File;
import java.nio.file.Paths;

public class ResponseBot {
    private static File settings = new File(Paths.get(new File(".").getAbsolutePath(), "config.yml").toAbsolutePath().toString());
    private IDiscordClient discordClient;
    private DiscordListener discordListener;
    private APIAI apiai;
    private Settings config;

    public ResponseBot() {
        config = new Settings();

        if (new Config().INIT_SETTINGS(settings, config).isNewConfig())
            return;

        this.apiai = new APIAI(config);
        ClientBuilder clientBuilder = new ClientBuilder(); // Creates the ClientBuilder instance
        clientBuilder.withToken(config.token); // Adds the login info to the builder

        try {
            discordClient = clientBuilder.login();
        } catch (DiscordException e) {
            e.printStackTrace();
        }
        if (discordClient != null) {
            discordListener = new DiscordListener(config, discordClient, apiai);
            discordClient.getDispatcher().registerListener(discordListener);
        }

    }
}
