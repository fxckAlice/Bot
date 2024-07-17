package org.memes.context;

import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;

public class BotConnection{
    private final String testChatId = "6838778724";
    private final String token;
    public BotConnection(String token) throws Exception {
        super();
        this.token = token;
        checkConnection();
    }
    public void checkConnection() throws Exception{
        final TelegramClient client = new OkHttpTelegramClient(token);
        SendMessage message = SendMessage
                .builder()
                .chatId(Long.parseLong(testChatId))
                .text("Bot has been deployed!")
                .build();
        client.execute(message);
        System.out.println("Bot is connected");
    }
}
