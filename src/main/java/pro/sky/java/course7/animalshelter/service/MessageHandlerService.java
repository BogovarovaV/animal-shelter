package pro.sky.java.course7.animalshelter.service;

import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.request.Keyboard;

public interface MessageHandlerService {
    void handleMessage(Message inputMessage, long chatId) ;

    void sendMessage(Long chatId, String inputMessage, Keyboard keyboard);

    void sendMessage(Long chatId, String inputMessage);

    void sendDocument(Long chatId, java.io.File file);

    void sendReminderAboutLackOfReport();

    void sendRemindersToVolunteerAboutEndOfTrial();

    void sendNotificationAboutSuccessReport();

    void sendNotificationAboutDeclinedReport();

    void sendNotificationAboutResultOfTrial();

}
