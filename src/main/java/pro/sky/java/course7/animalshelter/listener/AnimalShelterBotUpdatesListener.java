package pro.sky.java.course7.animalshelter.listener;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.Keyboard;
import com.pengrad.telegrambot.model.request.KeyboardButton;
import com.pengrad.telegrambot.model.request.ReplyKeyboardMarkup;
import com.pengrad.telegrambot.request.SendMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pro.sky.java.course7.animalshelter.model.User;
import pro.sky.java.course7.animalshelter.service.UserService;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Optional;

import static pro.sky.java.course7.animalshelter.service.Constants.*;


@Service
public class AnimalShelterBotUpdatesListener implements UpdatesListener {

    private static final Logger logger = LoggerFactory.getLogger(AnimalShelterBotUpdatesListener.class);
    private static boolean registrationRequire = false;

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
                handleMessage(inputMessage, extractChatId(message));
            }
        });
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }


    /**
     * Recognize and implement user's messages
     *
     * @param inputMessage - user's message sent to bot
     * @param chatId       - user's chat id
     */

    private void handleMessage(String inputMessage, long chatId) {
        SendMessage outputMessage;
        switch (inputMessage) {
            case START_CMD:
                logger.info(START_CMD + " message has been received");
                outputMessage = new SendMessage(chatId, GREETINGS_TEXT)
                        // show start menu
                        .replyMarkup(startButtons());
                break;
            case SHELTER_INFO_CMD:
                logger.info(SHELTER_INFO_CMD + " message has been received");
                outputMessage = new SendMessage(chatId, CHOOSE_OPTION)
                        // show "About shelter" menu
                        .replyMarkup(shelterInfoButtons());
                break;
            case BACK_TO_MAIN_MENU_CMD:
                logger.info(BACK_TO_MAIN_MENU_CMD + " message has been received");
                outputMessage = new SendMessage(chatId, CHOOSE_OPTION)
                        // back to start menu
                        .replyMarkup(startButtons());
                break;
            case ABOUT_US_CMD:
                logger.info(ABOUT_US_CMD + " message has been received");
                outputMessage = new SendMessage(chatId, ABOUT_US_TEXT);
                break;
            case WORKING_HOURS_CMD:
                logger.info(WORKING_HOURS_CMD + " message has been received");
                outputMessage = new SendMessage(chatId, WORKING_HOURS_TEXT);
                break;
            case SAFETY_RECOMMENDATION_CMD:
                logger.info(SAFETY_RECOMMENDATION_CMD + " message has been received");
                outputMessage = new SendMessage(chatId, SAFETY_RECOMMENDATION_TEXT);
                break;
            case CONTACT_ME_CMD:
                logger.info(CONTACT_ME_CMD + " message has been received");
                outputMessage = new SendMessage(chatId, CONTACT_ME_TEXT);
                registrationRequire = true;
                break;
            case HOW_TO_TAKE_CMD:
            case BACK_TO_RECOMMENDATION_MENU_CMD:
                logger.info(HOW_TO_TAKE_CMD + "message has been received");
                outputMessage = new SendMessage(chatId, HOW_TO_TAKE_CMD)
                        .replyMarkup(recommendationButtons());
                break;
            case MEET_THE_DOG_CMD:
                logger.info(MEET_THE_DOG_CMD + "message has been received");
                outputMessage = new SendMessage(chatId, MEET_THE_DOG_TEXT);
                break;
            case DOCUMENTS_CMD:
                logger.info(DOCUMENTS_CMD + "message has been received");
                outputMessage = new SendMessage(chatId, DOCUMENTS_TEXT);
                break;
            case TRANSPORTING_AND_ADVICE_CMD:
            case BACK_TO_TRANSPORT_AND_ADVICE_MENU_CMD:
                logger.info(TRANSPORTING_AND_ADVICE_CMD + "message has been received");
                outputMessage = new SendMessage(chatId, CHOOSE_OPTION)
                        .replyMarkup(transportAndAdviceMenu());
                break;
            case TRANSPORTING_CMD:
                logger.info(TRANSPORTING_CMD + "message has been received");
                outputMessage = new SendMessage(chatId, TRANSPORTING_TEXT);
                break;
            case ADVICE_CMD:
                logger.info(ADVICE_CMD + "message has been received");
                outputMessage = new SendMessage(chatId, COMMON_ADVICE_TEXT)
                        .replyMarkup(specificAdviceMenu());
                break;
            case ADVICE_FOR_PUPPY_CMD:
                logger.info(ADVICE_FOR_PUPPY_CMD + "message has been received");
                outputMessage = new SendMessage(chatId, ADVICE_FOR_PUPPY_TEXT);
                break;
            case ADVICE_FOR_ADULT_DOG_CMD:
                logger.info(ADVICE_FOR_ADULT_DOG_CMD + "message has been received");
                outputMessage = new SendMessage(chatId, ADVICE_FOR_ADULT_DOG_TEXT);
                break;
            case ADVICE_FOR_SPECIAL_DOG_CMD:
                logger.info(ADVICE_FOR_SPECIAL_DOG_CMD + "message has been received");
                outputMessage = new SendMessage(chatId, ADVICE_FOR_SPECIAL_DOG_TEXT);
                break;
            case REFUSAL_REASONS_CMD:
                logger.info(REFUSAL_REASONS_CMD + "message has been received");
                outputMessage = new SendMessage(chatId, REFUSAL_REASONS_TEXT);
                break;
            case CYNOLOGIST_CMD:
            case BACK_TO_CYNOLOGIST_MENU_CMD:
                logger.info(CYNOLOGIST_CMD + "message has been received");
                outputMessage = new SendMessage(chatId, CHOOSE_OPTION)
                        .replyMarkup(cynologistMenu());
                break;
            case CYNOLOGIST_ADVICE_CMD:
                logger.info(CYNOLOGIST_ADVICE_CMD + "message has been received");
                outputMessage = new SendMessage(chatId, CYNOLOGIST_ADVICE_TEXT);
                break;
            case CYNOLOGIST_CONTACTS_CMD:
                logger.info(CYNOLOGIST_CONTACTS_CMD + "message has been received");
                outputMessage = new SendMessage(chatId, CYNOLOGIST_CONTACTS_TEXT);
                break;
            case CALL_VOLUNTEER_CMD:
                logger.info(CALL_VOLUNTEER_CMD + "message has been received");
                outputMessage = new SendMessage(chatId, CALL_VOLUNTEER_TEXT);
                break;
            default:
                if (registrationRequire) {
                    logger.info("Registration data has been sent");
                    outputMessage = registrationUser(inputMessage, chatId);
                } else {
                    logger.info(INVALID_NOTIFICATION_OR_CMD + " message has been received");
                    outputMessage = new SendMessage(chatId, INVALID_NOTIFICATION_OR_CMD + "\n\n" + CALL_VOLUNTEER_TEXT);
                }
        }
        try {
            animalShelterBot.execute(outputMessage);
        } catch (Exception e) {
            logger.info("Exception was thrown in handle message method ");
            e.printStackTrace();
        }
    }

    /**
     * Create main menu buttons
     *
     * @return buttons
     */

    private static Keyboard startButtons() {
        return new ReplyKeyboardMarkup(
                new KeyboardButton[]{
                        new KeyboardButton(SHELTER_INFO_CMD),
                        new KeyboardButton(HOW_TO_TAKE_CMD),
                },
                new KeyboardButton[]{
                        new KeyboardButton(SEND_REPORT_CMD),
                        new KeyboardButton(CALL_VOLUNTEER_CMD)
                })
                .resizeKeyboard(true);
    }

    /**
     * Create "About shelter" menu buttons
     *
     * @return buttons
     */
    private static ReplyKeyboardMarkup shelterInfoButtons() {
        logger.info("Shelter info keyboard was called");
        return new ReplyKeyboardMarkup(
                new String[]{ABOUT_US_CMD, WORKING_HOURS_CMD},
                new String[]{SAFETY_RECOMMENDATION_CMD, CONTACT_ME_CMD},
                new String[]{BACK_TO_MAIN_MENU_CMD})
                .resizeKeyboard(true);
    }

    /**
     * Create "How to take a dog" menu buttons
     *
     * @return buttons
     */
    private static ReplyKeyboardMarkup recommendationButtons() {
        logger.info("Recommendation Keyboard was called");
        return new ReplyKeyboardMarkup(
                new String[]{MEET_THE_DOG_CMD, DOCUMENTS_CMD},
                new String[]{TRANSPORTING_AND_ADVICE_CMD, CYNOLOGIST_CMD},
                new String[]{REFUSAL_REASONS_CMD, CONTACT_ME_CMD},
                new String[]{CALL_VOLUNTEER_CMD, BACK_TO_MAIN_MENU_CMD})
                .resizeKeyboard(true);
    }

    /**
     * Create menu buttons with recommendations about dog's carriage and home improvement
     *
     * @return buttons
     */
    private static ReplyKeyboardMarkup transportAndAdviceMenu() {
        logger.info("Transportation and advice keyboard was called");
        return new ReplyKeyboardMarkup(
                new String[]{TRANSPORTING_CMD, ADVICE_CMD},
                new String[]{BACK_TO_RECOMMENDATION_MENU_CMD}
        )
                .resizeKeyboard(true);
    }

    /**
     * Create more menu buttons with home improvement advice for puppies, adults and disabled dogs
     *
     * @return buttons
     */

    private static ReplyKeyboardMarkup specificAdviceMenu() {
        logger.info("Specific advice keyboard was called");
        return new ReplyKeyboardMarkup(
                new String[]{ADVICE_FOR_PUPPY_CMD, ADVICE_FOR_ADULT_DOG_CMD, ADVICE_FOR_SPECIAL_DOG_CMD},
                new String[]{BACK_TO_TRANSPORT_AND_ADVICE_MENU_CMD}
        )
                .resizeKeyboard(true);
    }

    /**
     * Create menu buttons with cynologists contacts and recommendations
     *
     * @return buttons
     */

    private static ReplyKeyboardMarkup cynologistMenu() {
        logger.info("Cynologist keyboard was called");
        return new ReplyKeyboardMarkup(
                new String[]{CYNOLOGIST_ADVICE_CMD, CYNOLOGIST_CONTACTS_CMD},
                new String[]{BACK_TO_RECOMMENDATION_MENU_CMD}
        )
                .resizeKeyboard(true);
    }

    /**
     * Define user's chat id
     *
     * @param message - user's message sent to bot
     * @return chat id number
     */

    private long extractChatId(Message message) {
        return message.chat().id();
    }

    /**
     * Save parsing result of user's data
     *
     * @param inputMessage - user's message with his data
     * @param chatId       - chat id number
     * @return message from bot to user
     */

    private SendMessage registrationUser(String inputMessage, Long chatId) {
        SendMessage outputMessage;
        Optional<User> parseResult = userService.parse(inputMessage);
        if (parseResult.isPresent()) {
            if (userService.getUserByChatId(chatId) == null) {
                logger.info("Parse result is valid");
                userService.save(parseResult.get(), chatId);
                outputMessage = new SendMessage(chatId, SUCCESS_SAVING_TEXT);
            } else {
                logger.info("Data is already exists, it will be restored");
                userService.deleteUserByChatId(chatId);
                userService.save(parseResult.get(), chatId);
                outputMessage = new SendMessage(chatId, "Ваши данные успешно перезаписаны" );
            }
        } else {
            logger.info("Invalid registration data");
            outputMessage = new SendMessage(chatId, CONTACT_ME_TEXT );
        }
        return outputMessage;
    }

}

