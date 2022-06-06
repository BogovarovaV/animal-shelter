package pro.sky.java.course7.animalshelter.listener;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.File;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.PhotoSize;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.Keyboard;
import com.pengrad.telegrambot.model.request.KeyboardButton;
import com.pengrad.telegrambot.model.request.ReplyKeyboardMarkup;
import com.pengrad.telegrambot.request.GetFile;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.GetFileResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pro.sky.java.course7.animalshelter.model.Report;
import pro.sky.java.course7.animalshelter.model.User;
import pro.sky.java.course7.animalshelter.service.ReportService;
import pro.sky.java.course7.animalshelter.service.UserService;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import static pro.sky.java.course7.animalshelter.model.Report.ReportStatus.*;
import static pro.sky.java.course7.animalshelter.service.Constants.*;


@Service
public class AnimalShelterBotUpdatesListener implements UpdatesListener {

    private static final Logger logger = LoggerFactory.getLogger(AnimalShelterBotUpdatesListener.class);
    private Report report = new Report();
    private static boolean registrationRequired = false;

    private final TelegramBot animalShelterBot;
    private final UserService userService;
    private final ReportService reportService;

    public AnimalShelterBotUpdatesListener(TelegramBot animalShelterBot, UserService userService, ReportService reportService) {
        this.animalShelterBot = animalShelterBot;
        this.userService = userService;
        this.reportService = reportService;
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
            } else if (message != null && message.photo() != null) {
                if (report.getStatus().equals(REQUIRED_PHOTO)) {
                    logger.info("Photo has been sent by client");
                    List<PhotoSize> photos = List.of(message.photo());
                    File file = getFile(photos);
                    String fullPath = animalShelterBot.getFullFilePath(file);
                    LocalDateTime sentDate = LocalDateTime.now();
                    report.setSentDate(sentDate);
                    logger.info("Report was sent: " + sentDate);
                    report.setFilePath(fullPath);
                    logger.info("File path of report " + fullPath);
                    report.setStatus(SENT);
                    savingReport(extractChatId(message));
                } else {
                    handleMessage(UNKNOWN_FILE, extractChatId(message));
                }
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

    /**
     * Recognize and implement user's messages
     *
     * @param inputMessage - user's message sent to bot
     * @param chatId       - user's chat id
     */

    private void handleMessage(String inputMessage, long chatId) {
        SendMessage outputMessage;
        switch (inputMessage) {

            // common buttons

            case START_CMD:
                logger.info(START_CMD + " message has been received");
                outputMessage = new SendMessage(chatId, GREETINGS_TEXT)
                        .replyMarkup(startButtons());
                break;
            case SHELTER_INFO_CMD:
                logger.info(SHELTER_INFO_CMD + " message has been received");
                outputMessage = new SendMessage(chatId, CHOOSE_OPTION)
                        .replyMarkup(shelterInfoButtons());
                break;
            case BACK_TO_MAIN_MENU_CMD:
                logger.info(BACK_TO_MAIN_MENU_CMD + " message has been received");
                outputMessage = new SendMessage(chatId, CHOOSE_OPTION)
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
                registrationRequired = true;
                break;
            case CALL_VOLUNTEER_CMD:
                logger.info(CALL_VOLUNTEER_CMD + " message has been received");
                outputMessage = new SendMessage(chatId, CALL_VOLUNTEER_TEXT);
                break;
            case DOCUMENTS_CMD:
                logger.info(DOCUMENTS_CMD + " message has been received");
                outputMessage = new SendMessage(chatId, DOCUMENTS_TEXT);
                break;
            case HOW_TO_TAKE_CMD:
                logger.info(HOW_TO_TAKE_CMD + " message has been received");
                outputMessage = new SendMessage(chatId, "Кого бы вы хотели взять?")
                        .replyMarkup(chooseAnimal());
                break;
            case REFUSAL_REASONS_CMD:
                logger.info(REFUSAL_REASONS_CMD + " message has been received");
                outputMessage = new SendMessage(chatId, REFUSAL_REASONS_TEXT);
                break;
            case SEND_REPORT_CMD:
                logger.info(SEND_REPORT_CMD + " message has been received");
                outputMessage = new SendMessage(chatId, REPORT_FORM);
                report.setStatus(REQUIRED_TEXT);
                break;
            case UNKNOWN_FILE:
                logger.info(UNKNOWN_FILE + " message has been received");
                outputMessage = new SendMessage(chatId, UNKNOWN_FILE);
                break;

            // buttons for dogs

            case DOG_CMD:
            case BACK_TO_DOG_RECOMMENDATION_MENU_CMD:
                logger.info(DOG_CMD + "message has been received");
                outputMessage = new SendMessage(chatId, CHOOSE_OPTION)
                        .replyMarkup(dogRecommendationButtons());
                break;
            case MEET_THE_DOG_CMD:
                logger.info(MEET_THE_DOG_CMD + "message has been received");
                outputMessage = new SendMessage(chatId, MEET_THE_DOG_TEXT);
                break;
            case DOG_TRANSPORTING_AND_ADVICE_CMD:
            case BACK_TO_DOG_TRANSPORT_AND_ADVICE_MENU_CMD:
                logger.info(DOG_TRANSPORTING_AND_ADVICE_CMD + "message has been received");
                outputMessage = new SendMessage(chatId, CHOOSE_OPTION)
                        .replyMarkup(dogTransportAndAdviceDogButtons());
                break;
            case DOG_TRANSPORTING_CMD:
                logger.info(DOG_TRANSPORTING_CMD + "message has been received");
                outputMessage = new SendMessage(chatId, DOG_TRANSPORTING_TEXT);
                break;
            case DOG_ADVICE_CMD:
                logger.info(DOG_ADVICE_CMD + "message has been received");
                outputMessage = new SendMessage(chatId, DOG_COMMON_ADVICE_TEXT)
                        .replyMarkup(dogSpecificAdviceMenu());
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

            // buttons for cats

            case CAT_CMD:
            case BACK_TO_CAT_RECOMMENDATION_MENU_CMD:
                logger.info(CAT_CMD + "message has been received");
                outputMessage = new SendMessage(chatId, CHOOSE_OPTION)
                        .replyMarkup(catRecommendationButtons());
                break;
            case MEET_THE_CAT_CMD:
                logger.info(MEET_THE_CAT_CMD + "message has been received");
                outputMessage = new SendMessage(chatId, MEET_THE_CAT_TEXT);
                break;
            case CAT_TRANSPORTING_AND_ADVICE_CMD:
            case BACK_TO_CAT_TRANSPORT_AND_ADVICE_MENU_CMD:
                logger.info(CAT_TRANSPORTING_AND_ADVICE_CMD + "message has been received");
                outputMessage = new SendMessage(chatId, CHOOSE_OPTION)
                        .replyMarkup(catTransportAndAdviceDogButtons());
                break;
            case CAT_TRANSPORTING_CMD:
                logger.info(CAT_TRANSPORTING_CMD + "message has been received");
                outputMessage = new SendMessage(chatId, CAT_TRANSPORTING_TEXT);
                break;
            case CAT_ADVICE_CMD:
                logger.info(CAT_ADVICE_CMD + "message has been received");
                outputMessage = new SendMessage(chatId, CAT_COMMON_ADVICE_TEXT)
                        .replyMarkup(catSpecificAdviceMenu());
                break;
            case ADVICE_FOR_KITTEN_CMD:
                logger.info(ADVICE_FOR_KITTEN_CMD + "message has been received");
                outputMessage = new SendMessage(chatId, ADVICE_FOR_KITTEN_TEXT);
                break;
            case ADVICE_FOR_ADULT_CAT_CMD:
                logger.info(ADVICE_FOR_ADULT_CAT_CMD + "message has been received");
                outputMessage = new SendMessage(chatId, ADVICE_FOR_ADULT_CAT_TEXT);
                break;
            case ADVICE_FOR_SPECIAL_CAT_CMD:
                logger.info(ADVICE_FOR_SPECIAL_CAT_CMD + "message has been received");
                outputMessage = new SendMessage(chatId, ADVICE_FOR_SPECIAL_CAT_TEXT);
                break;
            case FELINOLOGIST_CMD:
            case BACK_TO_FELINOLOGIST_MENU_CMD:
                logger.info(FELINOLOGIST_CMD + "message has been received");
                outputMessage = new SendMessage(chatId, CHOOSE_OPTION)
                        .replyMarkup(fenologistMenu());
                break;
            case FELINOLOGIST_ADVICE_CMD:
                logger.info(FELINOLOGIST_ADVICE_CMD + "message has been received");
                outputMessage = new SendMessage(chatId, FELINOLOGIST_ADVICE_TEXT);
                break;
            case FELINOLOGIST_CONTACTS_CMD:
                logger.info(FELINOLOGIST_CONTACTS_CMD + "message has been received");
                outputMessage = new SendMessage(chatId, FELINOLOGIST_CONTACTS_TEXT);
                break;

            default:
                if (registrationRequired) {
                    logger.info("Registration data has been sent");
                    outputMessage = registrationUser(inputMessage, chatId);
                    registrationRequired = false;
                } else if (report.getStatus().equals(REQUIRED_TEXT)) {
                    outputMessage = new SendMessage(chatId, "Отлично, текст отчета у нас есть. Теперь пришлите фото питомца.\n" +
                            "\n ❗Внимание❗ Без фото отчет не будет принят к рассмотрению!");
                    report.setReportText(inputMessage);
                    report.setStatus(REQUIRED_PHOTO);
                } else {
                    logger.info(INVALID_NOTIFICATION_OR_CMD + " message has been received");
                    outputMessage = new SendMessage(chatId, INVALID_NOTIFICATION_OR_CMD);
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
     * Create animal options buttons
     *
     * @return buttons
     */

    private static ReplyKeyboardMarkup chooseAnimal() {
        logger.info("Choose animal Keyboard was called");
        return new ReplyKeyboardMarkup(
                new String[]{CAT_CMD, DOG_CMD},
                new String[]{BACK_TO_MAIN_MENU_CMD})
                .resizeKeyboard(true);
    }

    /**
     * Create "How to take a dog" menu buttons
     *
     * @return buttons
     */

    private static ReplyKeyboardMarkup dogRecommendationButtons() {
        logger.info("Dogs recommendations Keyboard was called");
        return new ReplyKeyboardMarkup(
                new String[]{MEET_THE_DOG_CMD, DOCUMENTS_CMD},
                new String[]{DOG_TRANSPORTING_AND_ADVICE_CMD, CYNOLOGIST_CMD},
                new String[]{REFUSAL_REASONS_CMD, CONTACT_ME_CMD},
                new String[]{CALL_VOLUNTEER_CMD, BACK_TO_MAIN_MENU_CMD})
                .resizeKeyboard(true);
    }

    /**
     * Create menu buttons with recommendations about dog's carriage and home improvement
     *
     * @return buttons
     */

    private static ReplyKeyboardMarkup dogTransportAndAdviceDogButtons() {
        logger.info("Dog's transportation and advice keyboard was called");
        return new ReplyKeyboardMarkup(
                new String[]{DOG_TRANSPORTING_CMD, DOG_ADVICE_CMD},
                new String[]{BACK_TO_DOG_RECOMMENDATION_MENU_CMD}
        )
                .resizeKeyboard(true);
    }

    /**
     * Create more menu buttons with home improvement advice for puppies, adults and disabled dogs
     *
     * @return buttons
     */

    private static ReplyKeyboardMarkup dogSpecificAdviceMenu() {
        logger.info("Specific advice keyboard was called");
        return new ReplyKeyboardMarkup(
                new String[]{ADVICE_FOR_PUPPY_CMD, ADVICE_FOR_ADULT_DOG_CMD, ADVICE_FOR_SPECIAL_DOG_CMD},
                new String[]{BACK_TO_DOG_TRANSPORT_AND_ADVICE_MENU_CMD}
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
                new String[]{BACK_TO_DOG_RECOMMENDATION_MENU_CMD}
        )
                .resizeKeyboard(true);
    }


    // cats

    /**
     * Create "How to take a cat" menu buttons
     *
     * @return buttons
     */
    private static ReplyKeyboardMarkup catRecommendationButtons() {
        logger.info("Cat recommendation Keyboard was called");
        return new ReplyKeyboardMarkup(
                new String[]{MEET_THE_CAT_CMD, DOCUMENTS_CMD},
                new String[]{CAT_TRANSPORTING_AND_ADVICE_CMD, FELINOLOGIST_CMD},
                new String[]{REFUSAL_REASONS_CMD, CONTACT_ME_CMD},
                new String[]{CALL_VOLUNTEER_CMD, BACK_TO_MAIN_MENU_CMD})
                .resizeKeyboard(true);
    }

    /**
     * Create menu buttons with recommendations about cat's carriage and home improvement
     *
     * @return buttons
     */

    private static ReplyKeyboardMarkup catTransportAndAdviceDogButtons() {
        logger.info("Cat's transportation and advice keyboard was called");
        return new ReplyKeyboardMarkup(
                new String[]{CAT_TRANSPORTING_CMD, CAT_ADVICE_CMD},
                new String[]{BACK_TO_CAT_RECOMMENDATION_MENU_CMD}
        )
                .resizeKeyboard(true);
    }

    /**
     * Create more menu buttons with home improvement advice for kittens, adults and disabled cats
     *
     * @return buttons
     */

    private static ReplyKeyboardMarkup catSpecificAdviceMenu() {
        logger.info("Specific advice keyboard was called");
        return new ReplyKeyboardMarkup(
                new String[]{ADVICE_FOR_KITTEN_CMD, ADVICE_FOR_ADULT_CAT_CMD, ADVICE_FOR_SPECIAL_CAT_CMD},
                new String[]{BACK_TO_CAT_TRANSPORT_AND_ADVICE_MENU_CMD}
        )
                .resizeKeyboard(true);
    }

    /**
     * Create menu buttons with cats fenologists contacts and recommendations
     *
     * @return buttons
     */

    private static ReplyKeyboardMarkup fenologistMenu() {
        logger.info("Cats fenologist keyboard was called");
        return new ReplyKeyboardMarkup(
                new String[]{FELINOLOGIST_ADVICE_CMD, FELINOLOGIST_CONTACTS_CMD},
                new String[]{BACK_TO_CAT_RECOMMENDATION_MENU_CMD}
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
                User.UserStatus currentStatus = userService.getUserByChatId(chatId).getStatus();
            //    userService.deleteUserByChatId(chatId);
                User editedUser = userService.edit(parseResult.get(),
                        userService.getUserByChatId(chatId).getId(),
                        chatId, currentStatus);
                logger.info("Client's data has been edited successfully:" + editedUser);
                outputMessage = new SendMessage(chatId, "Ваши данные успешно перезаписаны!");
            }
        } else {
            logger.info("Invalid registration data");
            outputMessage = new SendMessage(chatId, CONTACT_ME_TEXT);
        }
        return outputMessage;
    }

    private void savingReport(long chatId) {
        logger.info("Saving report in process");
        SendMessage reply;
        if (report.getStatus().equals(SENT)) {
            reportService.saveReport(report, chatId);
            logger.info("Client's report saved");
            reply = new SendMessage(chatId, "Спасибо! Ваш отчет отправлен волонтеру на проверку.");
        } else {
            reply = new SendMessage(chatId, INVALID_NOTIFICATION_OR_CMD);
        }
        try {
            animalShelterBot.execute(reply);
        } catch (Exception e) {
            logger.info("Exception was thrown in saving report method ");
            e.printStackTrace();
        }
    }
}

