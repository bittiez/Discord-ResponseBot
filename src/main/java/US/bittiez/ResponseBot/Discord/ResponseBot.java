package US.bittiez.ResponseBot.Discord;

import com.esotericsoftware.yamlbeans.YamlReader;
import com.esotericsoftware.yamlbeans.YamlWriter;
import sx.blah.discord.api.ClientBuilder;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.util.DiscordException;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;

public class ResponseBot {
    private IDiscordClient discordClient;
    private DiscordListener discordListener;
    private APIAI apiai;

    private File settings;
    private Settings config;

    public ResponseBot() {

        String thePath = new File(".").getAbsolutePath();
        settings = new File(Paths.get(thePath, "config.yml").toAbsolutePath().toString());

        initializeSettings();
        if (config == null)
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

    private void initializeSettings() {
        try {

            if (settings.exists()) {
                config = new YamlReader(new FileReader(settings)).read(Settings.class);
            } else {
                settings.createNewFile();
                config = new Settings();

                config.authorized_user = new ArrayList<String>();
                config.authorized_user.add("1234567885432221");

                config.ignoreUser = new ArrayList<String>();
                config.ignoreUser.add("6546546521321849");

                config.listenToChannels = new ArrayList<String>();
                config.listenToChannels.add("13223234536454566");

                config.prefix = "!";

                config.token = "11421ggdsfg35gbewgewgrehtg";
				config.api_token = "11421ggdsfg35gbewgewgrehtg";
                YamlWriter writer = new YamlWriter(new FileWriter(settings));
                writer.write(config);
                writer.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
