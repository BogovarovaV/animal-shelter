package pro.sky.java.course7.animalshelter.service;

import com.pengrad.telegrambot.model.request.Keyboard;

public interface MessageHandlerService {
    void handleMessage(String inputMessage, long chatId);

    void sendMessage(long chatId, String inputMessage, Keyboard keyboard);

    void sendMessage(long chatId, String inputMessage);
}
