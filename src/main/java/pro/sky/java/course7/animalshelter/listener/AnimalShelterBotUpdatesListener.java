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
import pro.sky.java.course7.animalshelter.model.Report;
import pro.sky.java.course7.animalshelter.service.MessageHandlerService;
import pro.sky.java.course7.animalshelter.service.UserService;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.util.List;

import static pro.sky.java.course7.animalshelter.constants.Constants.UNKNOWN_FILE;


@Service
public class AnimalShelterBotUpdatesListener implements UpdatesListener {

    private static final Logger logger = LoggerFactory.getLogger(AnimalShelterBotUpdatesListener.class);
    private Report report = new Report();

    private final TelegramBot animalShelterBot;
    private final UserService userService;


    private final MessageHandlerService messageHandler;

    public AnimalShelterBotUpdatesListener(TelegramBot animalShelterBot, UserService userService, MessageHandlerService messageHandler) {
        this.animalShelterBot = animalShelterBot;
        this.userService = userService;
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
            if (message != null && message.text() != null) {
                String inputMessage = message.text();
                messageHandler.handleMessage(inputMessage, extractChatId(message));
            } else if (message != null && message.photo() != null) {
                //       if (report.getStatus().equals(REQUIRED_PHOTO)) {
                logger.info("Photo has been sent by quest");
                List<PhotoSize> photos = List.of(message.photo());
                File file = getFile(photos);
                String fullPath = animalShelterBot.getFullFilePath(file);
                LocalDate sentDate = LocalDate.now();
                //          report.setSentDate(sentDate);
                logger.info("Report was sent: " + sentDate);
                //        report.setFilePath(fullPath);
                logger.info("File path of report " + fullPath);
                //         report.setStatus(SENT);
                //         savingReport(extractChatId(message));
            } else {
                messageHandler.handleMessage(UNKNOWN_FILE, extractChatId(message));
                //     }
            }
        });
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }

    private File getFile(List<PhotoSize> photos) {
        GetFile request = new GetFile(photos.get(0).fileId());
        GetFileResponse getFileResponse = animalShelterBot.execute(request);
        File file = getFileResponse.file();
        return file;
    }

    private long extractChatId(Message message) {
        return message.chat().id();
    }
}

