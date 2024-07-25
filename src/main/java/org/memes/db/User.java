package org.memes.db;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class User {
    private Long chatID;
    private String nick;
    private boolean isSingedIn;
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

    public User(long chatID, String nick) {
        this.chatID = chatID;
        this.nick = nick;
        this.isSingedIn = true;
    }

    public ArrayList<Long> getChatsId() {
        return chatsId;
    }
}
