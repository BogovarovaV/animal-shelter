package pro.sky.java.course7.animalshelter.listener;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.User;
import com.pengrad.telegrambot.request.SendMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pro.sky.java.course7.animalshelter.service.UserService;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
public class AnimalShelterBotUpdatesListener implements UpdatesListener {

    private static final Logger logger = LoggerFactory.getLogger(AnimalShelterBotUpdatesListener.class);


    private final String START_CMD = "/start";

    private final String GREETINGS_TEXT = "Hello, dear friend!";

    private final String INVALID_NOTIFICATION_OR_CMD = "Invalid command or notification, please, start again";

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

    @Override
    public int process(List<Update> updates) {
        updates.forEach(update -> {
            logger.info("Processing update: {}", update);
            Message message = update.message();
            if (message.text().startsWith(START_CMD)) {
                logger.info(START_CMD + " command has been received");
                sendMessage(extractChatId(message), GREETINGS_TEXT);
            } else {
                sendMessage(extractChatId(message), INVALID_NOTIFICATION_OR_CMD);
            }
        });
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }


    private void sendMessage(Long chatId, String messageText) {
        SendMessage sendMessage = new SendMessage(chatId, messageText);
        animalShelterBot.execute(sendMessage);
    }

    private long extractChatId(Message message) {
        return message.chat().id();
    }

}
