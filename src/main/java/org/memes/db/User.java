package org.memes.db;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class User {
    private boolean isSingedIn;
    private boolean nothingRequired;
    private boolean ifNicknameRequired;
    private boolean ifPhotoRequired;
    private boolean ifListenerRequired;
    private boolean ifRmListenerRequired;
    private boolean ifSteamOpened;
    private boolean started;
    private Long chatID;
    private String nick;
    private String password;

    public User(Long chatID, String nick, Boolean started) {
        this.chatID = chatID;
        this.nick = nick;
        this.started = started;
    }


    public boolean isIfListenerRequired() {
        return ifListenerRequired;
    }

    public boolean isNothingRequired() {
        return nothingRequired;
    }

    public boolean isIfNicknameRequired() {
        return ifNicknameRequired;
    }

    public boolean isIfPhotoRequired() {
        return ifPhotoRequired;
    }
    public boolean isIfRmListenerRequired() {
        return ifRmListenerRequired;
    }

    public void setIfSteamOpened(boolean ifSteamOpened) {
        this.ifSteamOpened = ifSteamOpened;
    }

    public boolean isIfSteamOpened() {
        return ifSteamOpened;
    }

    public Boolean isStarted() {
        return started;
    }


    private ArrayList<Long> chatsId;

    public Long getChatID() {
        return chatID;
    }

    public String getNick() {
        return nick;
    }

    public String getPassword() {
        return password;
    }

    public boolean isSingedIn() {
        return isSingedIn;
    }

    public void setSingedIn(boolean singedIn) {
        isSingedIn = singedIn;
    }
    public User(User user){
        this(user.getChatID(), user.getNick(), user.isStarted());
    }

    public User(long chatID, String nick) {
        this.chatID = chatID;
        this.nick = nick;
        this.isSingedIn = true;
    }

    public User(Long chatID) {
        User newUser = UserDao.getDao().getUserById(chatID);
        this.chatID = chatID;
        if(newUser != null) {
            this.nick = newUser.getNick();
            this.password = newUser.getPassword();
            this.started = newUser.isStarted();
            this.isSingedIn = true;
        }
        else{
            this.isSingedIn = false;
            this.started = true;
        }
    }

    public void ifNothingRequired() {
        if(!ifNicknameRequired && !ifPhotoRequired && !ifListenerRequired && !ifRmListenerRequired) {
            this.nothingRequired = true;
        }
        else{
            this.nothingRequired = false;
        }
    }
    public void setIfRmListenerRequired(boolean ifRmListenerRequired) {
        if(ifRmListenerRequired) {
            this.nothingRequired = false;
        }
        this.ifRmListenerRequired = ifRmListenerRequired;
    }
    public void setIfListenerRequired(boolean ifListenerRequired) {
        if(ifListenerRequired) {
            this.nothingRequired = false;
        }
        this.ifListenerRequired = ifListenerRequired;
    }

    public void setIfNicknameRequired(boolean ifNicknameRequired) {
        if(ifNicknameRequired) {
            this.nothingRequired = false;
        }
        this.ifNicknameRequired = ifNicknameRequired;
    }

    public void setIfPhotoRequired(boolean ifPhotoRequired) {
        if(ifPhotoRequired) {
            this.nothingRequired = false;
        }
        this.ifPhotoRequired = ifPhotoRequired;
    }

    public void setStarted(boolean started) {
        this.started = started;
    }

    public ArrayList<Long> getChatsId() {
        if( chatsId == null ) {chatsId = new ArrayList<>();}
        return chatsId;
    }
    public void newSession(){
        this.setIfNicknameRequired(false);
        this.setIfPhotoRequired(false);
        this.setIfListenerRequired(false);
        this.setIfRmListenerRequired(false);
        this.setIfSteamOpened(false);
        this.setStarted(true);
        this.ifNothingRequired();
    }
}
