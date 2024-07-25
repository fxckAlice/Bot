package org.memes.db;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

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
    private static String getMD5Hash(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");

            byte[] messageDigest = md.digest(input.getBytes());

            StringBuilder sb = new StringBuilder();
            for (byte b : messageDigest) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();

        } catch (NoSuchAlgorithmException e) {
            System.out.println("Encryption failed!");
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public Long getChatID() {
        return chatID;
    }

    public String getNick() {
        return nick;
    }

    public String getPassword() {
        return password;
    }

    public void setSingedIn(boolean singedIn) {
        isSingedIn = singedIn;
    }
    public User(User user){
        this(user.getChatID(), user.getNick(), user.getPassword(), user.isStarted());
    }

    public User(long chatID, String nick, String password, boolean started) {
        this.chatID = chatID;
        this.nick = nick;
        this.password = password;
        this.started = started;
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
}
