package org.memes.update;

import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
import org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;

public class UpdateListener implements LongPollingSingleThreadUpdateConsumer {

    @Override
    public void consume(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()){
            TelegramClient client = new OkHttpTelegramClient("6579662937:AAE-cek-Mh6NQdbsxLbprVP0lAojuycc78w");
            SendMessage message = SendMessage
                    .builder()
                    .chatId(update.getMessage().getChatId())
                    .text(update.getMessage().getChatId().toString())
                    .build();
            try {
                client.execute(message);
            } catch (TelegramApiException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
