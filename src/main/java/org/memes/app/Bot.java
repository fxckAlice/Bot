package org.memes.app;

import org.memes.context.BotContext;
import org.memes.db.UserDao;
import org.memes.update.UpdateListener;
import org.telegram.telegrambots.longpolling.TelegramBotsLongPollingApplication;


public class Bot {
    private final String token = BotContext.getToken();
    private final UserDao dao = UserDao.getDao();

    public Bot(){
        try(TelegramBotsLongPollingApplication botApp = new TelegramBotsLongPollingApplication()) {
            //TODO: Makes dependency injection ^
            botApp.registerBot(token, new UpdateListener());

            Thread.currentThread().join();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
