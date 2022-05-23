package pro.sky.java.course7.animalshelter.listener;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.ReplyKeyboardMarkup;
import com.pengrad.telegrambot.request.SendMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pro.sky.java.course7.animalshelter.service.UserService;

import javax.annotation.PostConstruct;
import java.util.List;

import static pro.sky.java.course7.animalshelter.service.Constants.*;
import static pro.sky.java.course7.animalshelter.service.Constants.SHELTER_INFO_CMD;


@Service
public class AnimalShelterBotUpdatesListener implements UpdatesListener {

    private static final Logger logger = LoggerFactory.getLogger(AnimalShelterBotUpdatesListener.class);

    private final TelegramBot animalShelterBot;
    private final UserService userService;

    public AnimalShelterBotUpdatesListener(TelegramBot animalShelterBot, UserService userService) {
        this.animalShelterBot = animalShelterBot;
        this.userService = userService;
    }

    @PostConstruct
    public void init() {
        animalShelterBot.setUpdatesListener(this);
    }

    /**
     * Method for receiving replies from the bot whenever we send our message
     *
     * @param updates used for receiving and checking different types of updates
     */

    @Override
    public int process(List<Update> updates) {
        updates.forEach(update -> {
            logger.info("Processing update: {}", update);
            Message message = update.message();
            // check if the update has a message and message has text
            if (message != null && message.text() != null) {
                String command = message.text();
                executeCommand(command, extractChatId(message));
                // check if the update has any callback
            } else if (update.callbackQuery().data() != null) {
                executeCommand(update.callbackQuery().data(), update.callbackQuery().from().id());
            }
        });
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }

    /**
     * Method for recognition and implementation of posted command
     *
     * @param command - name of handled command
     */

    private void executeCommand(String command, long chatId) {
        SendMessage outputMessage;
        switch (command) {
            case START_CMD:
                logger.info(START_CMD + " command has been received");
                outputMessage = new SendMessage(chatId, GREETINGS_TEXT)
                        // show start menu
                        .replyMarkup(startButtons());
                break;
            case SHELTER_INFO_CMD:
                logger.info(SHELTER_INFO_CMD + " command has been received");
                outputMessage = new SendMessage(chatId, "О приюте")
                        // show "About shelter" menu
                        .replyMarkup(infoButtons());
                break;
            case BACK_TO_START_TEXT:
                logger.info(BACK_TO_START_CMD + " command has been received");
                outputMessage = new SendMessage(chatId, BACK_TO_START_TEXT)
                        // back to start menu
                        .replyMarkup(startButtons());
                break;
            case ABOUT_US_CMD:
                logger.info(ABOUT_US_CMD + " command has been received");
                outputMessage = new SendMessage(chatId, ABOUT_US_TEXT);
                break;
            case WORKING_HOURS_CMD:
                logger.info(WORKING_HOURS_CMD + " command has been received");
                outputMessage = new SendMessage(chatId, WORKING_HOURS_TEXT);
                break;
            case SAFETY_RECOMMENDATION_CMD:
                logger.info(SAFETY_RECOMMENDATION_CMD + " command has been received");
                outputMessage = new SendMessage(chatId, SAFETY_RECOMMENDATION_TEXT);
                break;
            case CONTACT_ME_CMD:
                logger.info(CONTACT_ME_CMD + " command has been received");
                outputMessage = new SendMessage(chatId, CONTACT_ME_TEXT);
                break;
            default:
                logger.info("Unknown command has been received");
                outputMessage = new SendMessage(chatId, INVALID_NOTIFICATION_OR_CMD);
        }
        try {
            animalShelterBot.execute(outputMessage);
        } catch (Exception e) {
            logger.info("Exception was thrown in execution command method ");
            e.printStackTrace();
        }
    }


    /**
     * Method for creation start menu with buttons
     */

    private static ReplyKeyboardMarkup startButtons() {
        return new ReplyKeyboardMarkup(
                "О приюте",
                "Как забрать собаку",
                "Прислать отчет",
                "Позвать волонтера")
                .oneTimeKeyboard(true)
                .resizeKeyboard(true)
                .selective(true);
    }

    /**
     * Method for creation "About shelter" menu with buttons
     */

    private static ReplyKeyboardMarkup infoButtons() {
        return new ReplyKeyboardMarkup(
                "Кто мы",
                "Адрес",
                "Безопасность",
                "Связаться со мной",
                "В главное меню")
                .resizeKeyboard(true);
    }

    /**
     * Method for extraction user's chatId
     *
     * @param message received message from user
     */

    private long extractChatId(Message message) {
        return message.chat().id();
    }
}

