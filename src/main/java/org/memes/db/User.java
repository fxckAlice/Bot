package org.memes.db;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class User {
    private boolean singedIN;
    private boolean nothingRequired;
    private boolean ifNicknameRequired;
    private boolean ifPhotoRequired;
    private boolean started;
    private Long chatID;
    private String nick;
    private String password;

    public boolean isSingedIN() {
        return singedIN;
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

    public Boolean isStarted() {
        return started;
    }

    private boolean isSingedIn;
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
        this(user.getChatID(), user.getNick(), user.getPassword(), user.isStarted());
    }

    public User(long chatID, String nick) {
        this.chatID = chatID;
        this.nick = nick;
        this.isSingedIn = true;
    }

    public User(Long chatID) {
        User newUser = UserDao.getDao().getUserById(chatID);
        if(newUser != null) {
            this.chatID = chatID;
            this.nick = newUser.getNick();
            this.password = newUser.getPassword();
            this.started = newUser.isStarted();
            this.isSingedIn = true;
        }
    }

    public ArrayList<Long> getChatsId() {
        return chatsId;
    }
}
