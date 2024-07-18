package org.memes.db;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class User {
    private Long chatID;
    private String nick;
    private String password;
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

    public boolean isSingedIn() {
        return isSingedIn;
    }

    public void setSingedIn(boolean singedIn) {
        isSingedIn = singedIn;
    }

    public User(long chatID, String nick, String password) {
        this.chatID = chatID;
        this.nick = nick;
        this.password = password;
        this.isSingedIn = true;
    }
}
