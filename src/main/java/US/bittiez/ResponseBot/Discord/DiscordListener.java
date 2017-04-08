package US.bittiez.ResponseBot.Discord;

import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.MessageReceivedEvent;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.MessageBuilder;
import sx.blah.discord.util.MissingPermissionsException;
import sx.blah.discord.util.RateLimitException;

public class DiscordListener {
    private APIAI apiai;
    private IDiscordClient client;
    private Settings config;

    public DiscordListener(Settings config, IDiscordClient client, APIAI apiai) {
        this.config = config;
        this.client = client;
        this.apiai = apiai;
    }

    @EventSubscriber
    public void onMessageReceivedEvent(MessageReceivedEvent event) {
        IMessage message = event.getMessage();
        IChannel channel = message.getChannel();

        if (config.ignoreUser.contains(message.getAuthor().getID()))
            return;

        if (message.getContent().toLowerCase().startsWith(config.prefix + "sayhello")) {
            doMessage(channel, String.format("Hello everyone! And a special hello to %s", message.getAuthor().getName()));
            return;
        }

        if (config.listenToChannels.contains(channel.getID()) || channel.isPrivate()) {
            if (message.getContent().startsWith("!")) {
                String response = apiai.getResponse(message.getContent().substring(config.prefix.length()));
                response = ChatColor.translateAlternateColorCodes('&', response);
                response = ChatColor.stripColor(response)
                        .replace("[PLAYER]", message.getAuthor().getName())
                        .replace("[DEFAULT]", "");
                doMessage(channel, response);
            }
        }
    }


    public void doMessage(IChannel channel, String content) {
        if (content.length() == 0)
            return;
        try {
            new MessageBuilder(client).withChannel(channel).withContent(content).build();
        } catch (RateLimitException e) {
            e.printStackTrace();
        } catch (DiscordException e) {
            e.printStackTrace();
        } catch (MissingPermissionsException e) {
            e.printStackTrace();
        }
    }
}
