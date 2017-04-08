package US.bittiez.ResponseBot.Discord.Config;

import US.bittiez.ResponseBot.Discord.Settings;
import com.esotericsoftware.yamlbeans.YamlException;
import com.esotericsoftware.yamlbeans.YamlReader;
import com.esotericsoftware.yamlbeans.YamlWriter;

import java.io.*;
import java.util.ArrayList;

public class Config {
    public CONFIG_RESPONSE INIT_SETTINGS(File settings, Settings config) {
        CONFIG_RESPONSE configResponse = new CONFIG_RESPONSE();
        try {
            if (settings.exists()) {

                config = new YamlReader(new FileReader(settings)).read(Settings.class);

                configResponse.setNewConfig(false);
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

                config.token = "DISCORD.BOT.TOKEN";
                config.api_token = "API.AI.BOT.TOKEN";
                YamlWriter writer = new YamlWriter(new FileWriter(settings));
                writer.write(config);
                writer.close();
                configResponse.setNewConfig(true);
            }
        } catch (YamlException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return configResponse;
    }

    public class CONFIG_RESPONSE {
        private boolean isNewConfig;

        public CONFIG_RESPONSE() {
        }

        public boolean isNewConfig() {
            return isNewConfig;
        }

        public void setNewConfig(boolean newConfig) {
            isNewConfig = newConfig;
        }
    }
}
