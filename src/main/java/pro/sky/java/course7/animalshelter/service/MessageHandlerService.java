package pro.sky.java.course7.animalshelter.service;

import com.pengrad.telegrambot.model.File;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.request.Keyboard;

import java.net.MalformedURLException;

public interface MessageHandlerService {
    void handleMessage(Message inputMessage, long chatId) ;

    void sendMessage(long chatId, String inputMessage, Keyboard keyboard);

    void sendMessage(long chatId, String inputMessage);

    void sendDocument(long chatId, java.io.File file);
}
