package org.memes.context;



public class BotContext {

    private static String TOKEN;
    public static String getToken(){
        if(TOKEN == null){
            TOKEN = token();
        }
        return TOKEN;
    }
    private BotContext(){
    }
    private static String token(){
        String token = "6579662937:AAE-cek-Mh6NQdbsxLbprVP0lAojuycc78w";
        try {
            BotConnection connection = new BotConnection(token);
            return token;
        }
        catch (Exception e){
            System.out.println("bot Connection failed!");
            return null;
        }
    }
}
