package org.memes.db;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

public class User {
    private long chatID;
    private String nick;
    private String email;
    private String password;
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

    public long getChatID() {
        return chatID;
    }

    public String getNick() {
        return nick;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public User(long chatID, String nick, String email, String password) {
        this.chatID = chatID;
        this.nick = nick;
        this.email = email;
        this.password = password;
    }
}
