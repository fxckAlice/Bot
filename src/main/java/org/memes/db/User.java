package org.memes.db;

import java.util.ArrayList;

public class User {
    private Boolean isSingedIn;
    private Boolean nothingRequired;
    private Boolean ifNicknameRequired;
    private Boolean ifListenerRequired;
    private Boolean ifRmListenerRequired;
    private Boolean ifStreamOpened;
    private Boolean started;
    private final Long chatID;
    private String nick;

    public User(Long chatID, String nick, Boolean started) {
        this.chatID = chatID;
        this.nick = nick;
        this.started = started;
    }


    public Boolean isIfListenerRequired() {
        return ifListenerRequired;
    }

    public Boolean isNothingRequired() {
        return nothingRequired;
    }

    public Boolean isIfNicknameRequired() {
        return ifNicknameRequired;
    }

    public Boolean isIfRmListenerRequired() {
        return ifRmListenerRequired;
    }

    public void setIfStreamOpened(boolean ifStreamOpened) {
        this.ifStreamOpened = ifStreamOpened;
    }

    public Boolean isIfStreamOpened() {
        return ifStreamOpened;
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
            this.started = newUser.isStarted();
            this.ifNicknameRequired = newUser.isIfNicknameRequired();
            this.ifListenerRequired = newUser.isIfListenerRequired();
            this.ifRmListenerRequired = newUser.isIfRmListenerRequired();
            this.isSingedIn = newUser.getNick() != null;
            this.chatsId = UserDao.getDao().getListenersListById(chatID);
        }
        else{
            newSession();
            this.isSingedIn = false;
            //TODO: POst new User
        }
    }

    public void ifNothingRequired() {
        if(!ifNicknameRequired && !ifListenerRequired && !ifRmListenerRequired) {
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

    public void setNick(String nick) {
        this.nick = nick;
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
        this.setIfListenerRequired(false);
        this.setIfRmListenerRequired(false);
        this.setIfStreamOpened(false);
        this.setStarted(true);
        this.ifNothingRequired();
    }
}
