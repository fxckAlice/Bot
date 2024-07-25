package org.memes.update;

import org.memes.db.User;
import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
import org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.PhotoSize;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;

import java.util.Comparator;
import java.util.List;

public class UpdateListener implements LongPollingSingleThreadUpdateConsumer {

    private final TelegramClient client = new OkHttpTelegramClient("6579662937:AAE-cek-Mh6NQdbsxLbprVP0lAojuycc78w");
    @Override
    public void consume(Update update) {
        User user = new User(update.getMessage().getChatId());
        if (update.hasMessage() && update.getMessage().hasText()){
            String message = update.getMessage().getText();
            Long chatId = update.getMessage().getChatId();
            if(message.equals("/start")){
                if(!started){
                    nothingRequired = true;
                    ifNicknameRequired = false;
                    ifPhotoRequired = false;
                    started = true;
                }
                else{
                    SendMessage alreadyStartedMessage = SendMessage
                            .builder()
                            .chatId(chatId)
                            .text("Bot already started!")
                            .build();
                    try{
                        client.execute(alreadyStartedMessage);
                    }
                    catch (TelegramApiException e){
                        System.out.println("Send failed!");
                        e.printStackTrace();
                    }
                }
            }
            if(message.equals("/setNickname")){

            }
        }

    }
}
