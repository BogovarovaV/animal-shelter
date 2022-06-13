package pro.sky.java.course7.animalshelter.listener;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.File;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.PhotoSize;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.GetFile;
import com.pengrad.telegrambot.response.GetFileResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
//import pro.sky.java.course7.animalshelter.model.Report;
import pro.sky.java.course7.animalshelter.service.MessageHandlerService;
//import pro.sky.java.course7.animalshelter.service.ReportService;
import pro.sky.java.course7.animalshelter.service.ReportService;
import pro.sky.java.course7.animalshelter.service.UserService;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.util.List;

import static pro.sky.java.course7.animalshelter.constants.Constants.UNKNOWN_FILE;
//import static pro.sky.java.course7.animalshelter.model.Report.ReportStatus.REQUIRED_PHOTO;
//import static pro.sky.java.course7.animalshelter.model.Report.ReportStatus.SENT;


@Service
public class AnimalShelterBotUpdatesListener implements UpdatesListener {

    private static final Logger logger = LoggerFactory.getLogger(AnimalShelterBotUpdatesListener.class);


    private final TelegramBot animalShelterBot;
    private final UserService userService;
    private final ReportService reportService;


    private final MessageHandlerService messageHandler;

    public AnimalShelterBotUpdatesListener(TelegramBot animalShelterBot,
                                           UserService userService,
                                           ReportService reportService,
                                           MessageHandlerService messageHandler) {
        this.animalShelterBot = animalShelterBot;
        this.userService = userService;
        this.reportService = reportService;
        this.messageHandler = messageHandler;
    }

    @PostConstruct
    public void init() {
        animalShelterBot.setUpdatesListener(this);
    }

    /**
     * Check and process chat's updates
     *
     * @param updates used for receiving and checking different types of updates
     * @return confirmed all updates
     */

    @Override
    public int process(List<Update> updates) {
        updates.forEach(update -> {
            logger.info("Processing update: {}", update);
            Message message = update.message();
            // check if the update has a message and message has text
            if (message != null ) {
                messageHandler.handleMessage(message, extractChatId(message));
            }
        });
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }

    private long extractChatId(Message message) {
        return message.chat().id();
    }
}

