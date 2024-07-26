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

import java.util.ArrayList;
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
                if(!user.isStarted()){
                    user.newSession();
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
                String setNicknameMessage;
                if(user.isNothingRequired() && user.getNick() == null){
                    setNicknameMessage = "Choose your nickname!";
                    user.setIfNicknameRequired(true);
                }
                else{
                    if(user.getNick() != null){
                        setNicknameMessage = "Nickname already taken!";
                    }
                    else{
                        setNicknameMessage = "Finish previous action!";
                    }
                }
                SendMessage sendErrMessage = SendMessage
                        .builder()
                        .chatId(user.getChatID())
                        .text(setNicknameMessage)
                        .build();
                try{
                    client.execute(sendErrMessage);
                }
                catch (TelegramApiException e){
                    System.out.println("Send failed!");
                }
            }
            if(message.equals("/addListener")){
                String addListenerMessage;
                if(user.isNothingRequired() && user.isSingedIn()){
                    addListenerMessage = "Enter nickname of your new listener!";
                    user.setIfListenerRequired(true);
                }
                else{
                    if(!user.isSingedIn()){
                        addListenerMessage = "Please set a nickname!";
                    }
                    else{
                        addListenerMessage = "Finish previous action!";
                    }
                }
                SendMessage sendMessage = SendMessage
                        .builder()
                        .chatId(user.getChatID())
                        .text(addListenerMessage)
                        .build();
                try{
                    client.execute(sendMessage);
                }
                catch (TelegramApiException e){
                    System.out.println("Send failed!");
                }
            }
            if(message.equals("/removeListener")){
                String removeListenerMessage;
                if(user.isNothingRequired() && user.isSingedIn()){
                    ArrayList<Long> arr = user.getChatsId();
                    if(arr.size() > 0){
                        removeListenerMessage = "Your listeners list: ";
                        for(Long l : arr){
                            removeListenerMessage += new User(l).getNick() + "  ";
                        }
                        user.setIfRmListenerRequired(true);
                    }
                    else{
                        removeListenerMessage = "Your listeners list is empty";
                    }
                }
                else{
                    if(!user.isSingedIn()){
                        removeListenerMessage = "Please set a nickname!";
                    }
                    else{
                        removeListenerMessage = "Finish previous action!";
                    }
                }
                SendMessage sendMessage = SendMessage
                        .builder()
                        .chatId(user.getChatID())
                        .text(removeListenerMessage)
                        .build();
                try{
                    client.execute(sendMessage);
                }
                catch (TelegramApiException e){
                    System.out.println("Send failed!");
                }
            }
            if(message.equals("/startStream")){
                String startStreamMessage;
                if(user.isNothingRequired() && user.isSingedIn() && user.isStarted() && !user.isIfSteamOpened()){
                    user.setIfSteamOpened(true);
                    startStreamMessage = "Streaming started!";
                }
                else{
                    if(!user.isSingedIn()){
                        startStreamMessage = "Please set a nickname!";
                    } else if (!user.isNothingRequired()) {
                        startStreamMessage = "Finish previous action!";
                    } else if (user.isIfSteamOpened()) {
                        startStreamMessage = "Steam`s already opened!";

                    } else{
                        startStreamMessage = "Start your session before starting streaming!";
                    }
                }
                SendMessage sendMessage = SendMessage
                        .builder()
                        .chatId(chatId)
                        .text(startStreamMessage)
                        .build();
                try{
                    client.execute(sendMessage);
                }
                catch (TelegramApiException e){
                    System.out.println("Send failed!");
                }
            }
            if(message.equals("/stopStream")){
                String stopStreamMessage;
                if(user.isNothingRequired() && user.isSingedIn() && user.isStarted() && user.isIfSteamOpened()){
                    user.setIfSteamOpened(false);
                    stopStreamMessage = "Streaming stopped!";
                }
                else{
                    if(!user.isSingedIn()){
                        stopStreamMessage = "Please set a nickname!";
                    } else if (!user.isNothingRequired()) {
                        stopStreamMessage = "Finish previous action!";
                    } else if (!user.isIfSteamOpened()) {
                        stopStreamMessage = "Steam`s already stop!";

                    } else{
                        stopStreamMessage = "Start your session before starting streaming!";
                    }
                }
                SendMessage sendMessage = SendMessage
                        .builder()
                        .chatId(chatId)
                        .text(stopStreamMessage)
                        .build();
                try{
                    client.execute(sendMessage);
                }
                catch (TelegramApiException e){
                    System.out.println("Send failed!");
                }
            }
            if(message.equals("/stop")){
                user.newSession();
                user.setStarted(false);
            }
        }

    }
}
