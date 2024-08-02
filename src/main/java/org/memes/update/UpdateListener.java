package org.memes.update;

import org.memes.db.User;
import org.memes.db.UserDao;
import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
import org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.PhotoSize;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.message.Message;
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
        Long chatId = update.getMessage().getChatId();
        if(update.hasMessage() && update.getMessage().hasPhoto()){
            if(user.isIfStreamOpened()){
                List<PhotoSize> photos = update.getMessage().getPhoto();
                String fId = photos.stream().max(Comparator.comparing(PhotoSize::getFileSize)).map(PhotoSize::getFileId).orElse("");
                String caption = update.getMessage().getCaption();
                SendPhoto sendPhoto = SendPhoto
                        .builder()
                        .chatId(chatId)
                        .photo(new InputFile(fId))
                        .caption(caption)
                        .build();
                try {
                    client.execute(sendPhoto);
                } catch (TelegramApiException e) {
                    System.out.println("Send failed!");
                }
            }
        }
        else if (update.hasMessage() && update.getMessage().hasText()){
            String message = update.getMessage().getText();
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
                UserDao.getDao().updateInfoFromUsers(user);
            }
            else if(message.equals("/setNickname")){
                String setNicknameMessage;
                if(user.isNothingRequired() && user.getNick() == null && user.isStarted()){
                    setNicknameMessage = "Choose your nickname!";
                    user.setIfNicknameRequired(true);
                }
                else{
                    if(user.getNick() != null){
                        setNicknameMessage = "Nickname already taken!";
                    }
                    else if(!user.isNothingRequired()){
                        setNicknameMessage = "Finish previous action!";
                    }
                    else{
                        setNicknameMessage = "Start your session before any action!";
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
            else if(message.equals("/addListener")){
                String addListenerMessage;
                if(user.isNothingRequired() && user.isSingedIn() && user.isStarted()){
                    addListenerMessage = "Enter nickname of your new listener!";
                    user.setIfListenerRequired(true);
                }
                else{
                    if(!user.isSingedIn()){
                        addListenerMessage = "Please set a nickname!";
                    }
                    else if(!user.isNothingRequired()){
                        addListenerMessage = "Finish previous action!";
                    }
                    else{
                        addListenerMessage = "Start your session before any action!";
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
                UserDao.getDao().updateInfoFromUsers(user);
            }
            else if(message.equals("/removeListener")){
                String removeListenerMessage;
                if(user.isNothingRequired() && user.isSingedIn() && user.isStarted()){
                    ArrayList<Long> arr = user.getChatsId();
                    if(!arr.isEmpty()){
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
                    else if(!user.isNothingRequired()){
                        removeListenerMessage = "Finish previous action!";
                    }
                    else{
                        removeListenerMessage = "Start your session before any action!";
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
                UserDao.getDao().updateInfoFromUsers(user);
            }
            else if(message.equals("/startStream")){
                String startStreamMessage;
                if(user.isNothingRequired() && user.isSingedIn() && user.isStarted() && !user.isIfStreamOpened()){
                    user.setIfStreamOpened(true);
                    startStreamMessage = "Streaming started!";
                }
                else{
                    if(!user.isSingedIn()){
                        startStreamMessage = "Please set a nickname!";
                    } else if (!user.isNothingRequired()) {
                        startStreamMessage = "Finish previous action!";
                    } else if (user.isIfStreamOpened()) {
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
                UserDao.getDao().updateInfoFromUsers(user);
            }
            else if(message.equals("/stopStream")){
                String stopStreamMessage;
                if(user.isNothingRequired() && user.isSingedIn() && user.isStarted() && user.isIfStreamOpened()){
                    user.setIfStreamOpened(false);
                    stopStreamMessage = "Streaming stopped!";
                }
                else{
                    if(!user.isSingedIn()){
                        stopStreamMessage = "Please set a nickname!";
                    } else if (!user.isNothingRequired()) {
                        stopStreamMessage = "Finish previous action!";
                    } else if (!user.isIfStreamOpened()) {
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
                UserDao.getDao().updateInfoFromUsers(user);
            }
            else if(message.equals("/stop")){
                user.newSession();
                user.setStarted(false);
                UserDao.getDao().updateInfoFromUsers(user);
            }
            else if(user.isIfNicknameRequired()){
                String textMessage;
                if(UserDao.getDao().ifUniqueByNickname(message)){
                    user.setNick(message);
                    textMessage = "Nickname has been changed!";
                    UserDao.getDao().updateInfoFromUsers(user);
                }
                else{
                    textMessage = "This nickname is already in use!";
                }
                SendMessage sendMessage = SendMessage
                        .builder()
                        .chatId(chatId)
                        .text(textMessage)
                        .build();
                try {
                    client.execute(sendMessage);
                } catch (TelegramApiException e) {
                    System.out.println("Send failed!");
                    e.printStackTrace();
                }
                user.setIfNicknameRequired(false);
                user.ifNothingRequired();
            }
            else if(user.isIfListenerRequired()){
                String textMessage;
                if(!UserDao.getDao().ifUniqueByNickname(message)){
                    Long listenersID = UserDao.getDao().getUserByNickname(message).getChatID();
                    if(!user.getChatsId().contains(listenersID)){
                        UserDao.getDao().createListener(chatId, listenersID);
                        textMessage = "Listener has been added!";
                    }
                    else {
                        textMessage = "This listener is already in your list!";
                    }
                }
                else {
                    textMessage = "There is no users with this nickname!";
                }
                SendMessage sendMessage = SendMessage
                        .builder()
                        .chatId(chatId)
                        .text(textMessage)
                        .build();
                try {
                    client.execute(sendMessage);
                } catch (TelegramApiException e) {
                    System.out.println("Send failed!");
                    e.printStackTrace();
                }
                user.setIfListenerRequired(false);
                user.ifNothingRequired();
            }
            else if(user.isIfRmListenerRequired()){
                String textMessage;
                if(user.getChatsId() != null){
                    Long userId = UserDao.getDao().getUserByNickname(message).getChatID();
                    if(user.getChatsId().contains(userId)){
                        UserDao.getDao().deleteListenerByChatId(chatId, userId);
                        textMessage = "Listener has been deleted!";
                    }
                    else {
                        textMessage = "Wrong nickname!";
                    }
                }
                else{
                    textMessage = "Your listener`s list is empty";
                }
                SendMessage sendMessage = SendMessage
                        .builder()
                        .chatId(chatId)
                        .text(textMessage)
                        .build();
                try {
                    client.execute(sendMessage);
                } catch (TelegramApiException e) {
                    System.out.println("Send failed!");
                    e.printStackTrace();
                }
            }
            else if(user.isIfStreamOpened()){
                for(Long id : user.getChatsId()){
                    SendMessage sendMessage = SendMessage
                            .builder()
                            .chatId(chatId)
                            .text(message)
                            .build();
                    try{
                        client.execute(sendMessage);
                    } catch (TelegramApiException e) {
                        System.out.println("Send failed!");
                    }
                }
            }

        }
        UserDao.getDao().updateInfoFromUsers(user);
    }
}
