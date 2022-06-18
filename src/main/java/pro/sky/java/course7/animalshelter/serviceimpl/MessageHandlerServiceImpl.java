package pro.sky.java.course7.animalshelter.serviceimpl;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.File;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.PhotoSize;
import com.pengrad.telegrambot.model.request.Keyboard;
import com.pengrad.telegrambot.model.request.KeyboardButton;
import com.pengrad.telegrambot.model.request.ReplyKeyboardMarkup;
import com.pengrad.telegrambot.request.GetFile;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.GetFileResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pro.sky.java.course7.animalshelter.listener.AnimalShelterBotUpdatesListener;
import pro.sky.java.course7.animalshelter.model.Animal;
import pro.sky.java.course7.animalshelter.model.User;
import pro.sky.java.course7.animalshelter.service.AnimalService;
import pro.sky.java.course7.animalshelter.service.MessageHandlerService;
import pro.sky.java.course7.animalshelter.service.ReportService;
import pro.sky.java.course7.animalshelter.service.UserService;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

import static pro.sky.java.course7.animalshelter.constants.Constants.*;
import static pro.sky.java.course7.animalshelter.model.Animal.AnimalTypes.DOG;

@Service
public class MessageHandlerServiceImpl implements MessageHandlerService {

    private static final Logger logger = LoggerFactory.getLogger(AnimalShelterBotUpdatesListener.class);

    private final TelegramBot animalShelterBot;
    private final UserService userService;
    private final ReportService reportService;
    private final AnimalService animalService;
    private static boolean registrationRequired = false;
    private static int sendingReportStatus = 0; // 1 - user pushed button "send report", //2 - user sent text, // 3 - user sent photo

    public MessageHandlerServiceImpl(TelegramBot animalShelterBot, UserService userService, ReportService reportService, AnimalService animalService) {
        this.animalShelterBot = animalShelterBot;
        this.userService = userService;
        this.reportService = reportService;
        this.animalService = animalService;
    }

    @Override
    public void handleMessage(Message inputMessage, long chatId) {
        if (inputMessage.text() != null) {
            switch (inputMessage.text()) {

                // common buttons

                case START_CMD:
                    sendMessage(chatId, GREETINGS_TEXT, chooseShelter());
                    break;
                case BACK_TO_CHOOSE_ANIMAL:
                    sendMessage(chatId, CHOOSE_OPTION, chooseShelter());
                    break;
                case DOG_SHELTER_CMD:
                    Animal.AnimalTypes type = DOG;
                case BACK_TO_DOG_START_MENU_CMD:
                    sendMessage(chatId, "Добро пожаловать в приют для собак!\n" + CHOOSE_OPTION, dogStartMenuButtons());
                    break;
                case DOG_SHELTER_INFO_CMD:
                    sendMessage(chatId, CHOOSE_OPTION, dogShelterInfoButtons());
                    break;
                case DOG_ABOUT_US_CMD:
                    sendMessage(chatId, DOG_ABOUT_US_TEXT);
                    break;
                case DOG_WORKING_HOURS_CMD:
                    sendMessage(chatId, DOG_WORKING_HOURS_TEXT);
                    break;
                case DOG_SECURITY_CONTACT_CMD:
                    sendMessage(chatId, DOG_SECURITY_CONTACT_TEXT);
                    break;
                case DOG_SAFETY_RECOMMENDATION_CMD:
                    sendMessage(chatId, DOG_SAFETY_RECOMMENDATION_TEXT);
                    break;
                case CONTACT_ME_CMD:
                    sendMessage(chatId, CONTACT_ME_TEXT);
                    registrationRequired = true;
                    break;
                case CALL_VOLUNTEER_CMD:
                    String userContact = inputMessage.chat().username();
                    if (userContact == null) {
                        sendMessage(chatId, "Пожалуйста, отправьте ваш номер телефона формате +79991234567 без пробелов.");
                    } else {
                        sendMessage(chatId, CALL_VOLUNTEER_TEXT);
                        sendMessage(VOLUNTEERS_CHAT_ID, "Пожалуйста, свяжитесь с пользователем: https://t.me/" + userContact);
                    }
                    break;
                case HOW_TO_TAKE_DOG_CMD:
                case BACK_TO_DOG_RECOMMENDATION_MENU_CMD:
                    sendMessage(chatId, CHOOSE_OPTION, dogRecommendationButtons());
                    break;
                case MEET_THE_DOG_CMD:
                    sendMessage(chatId, MEET_THE_DOG_TEXT);
                    break;
                case DOCUMENTS_CMD:
                    sendMessage(chatId, DOCUMENTS_TEXT);
                    break;
                case DOG_TRANSPORTING_AND_ADVICE_CMD:
                case BACK_TO_DOG_TRANSPORT_AND_ADVICE_MENU_CMD:
                    sendMessage(chatId, CHOOSE_OPTION, dogTransportAndAdviceDogButtons());
                    break;
                case DOG_TRANSPORTING_CMD:
                    sendMessage(chatId, DOG_TRANSPORTING_TEXT);
                    break;
                case DOG_ADVICE_CMD:
                    sendMessage(chatId, DOG_COMMON_ADVICE_TEXT, dogSpecificAdviceMenu());
                    break;
                case ADVICE_FOR_PUPPY_CMD:
                    sendMessage(chatId, ADVICE_FOR_PUPPY_TEXT);
                    break;
                case ADVICE_FOR_ADULT_DOG_CMD:
                    sendMessage(chatId, ADVICE_FOR_ADULT_DOG_TEXT);
                    break;
                case ADVICE_FOR_SPECIAL_DOG_CMD:
                    sendMessage(chatId, ADVICE_FOR_SPECIAL_DOG_TEXT);
                    break;
                case CYNOLOGIST_CMD:
                case BACK_TO_CYNOLOGIST_MENU_CMD:
                    sendMessage(chatId, CHOOSE_OPTION, cynologistMenu());
                    break;
                case CYNOLOGIST_ADVICE_CMD:
                    sendMessage(chatId, CYNOLOGIST_ADVICE_TEXT);
                    break;
                case CYNOLOGIST_CONTACTS_CMD:
                    sendMessage(chatId, CYNOLOGIST_CONTACTS_TEXT);
                    break;
                case REFUSAL_REASONS_CMD:
                    sendMessage(chatId, REFUSAL_REASONS_TEXT);
                    break;
                case UNKNOWN_FILE:
                    sendMessage(chatId, UNKNOWN_FILE);
                    break;
                case SEND_REPORT_CMD:
                    sendingReportStatus = 1;
                    if (userService.adopterOnTrialExist(chatId)) {
                        if (!reportService.reportWasSentToday(LocalDate.now(), userService.getUserByChatId(chatId).getId())) {
                            sendMessage(chatId, REPORT_FORM);
                        } else {
                            sendMessage(chatId, "Вы уже направляли сегодня отчет");
                        }
                    } else {
                        sendMessage(chatId, "Вероятно, вас нет в моей базе данных усыновителей питомцев. \n" +
                                "\nПожалуйста, нажмите в меню кнопку \"Позвать волонтера\" и мы обязательно с вами свяжемся");
                    }
                    break;


                //cat commands

                case CAT_SHELTER_CMD:
                case BACK_TO_CAT_START_MENU_CMD:
                    sendMessage(chatId, "Добро пожаловать в приют для кошек!\n" + CHOOSE_OPTION, catStartMenuButtons());
                    break;
                case CAT_SHELTER_INFO_CMD:
                    sendMessage(chatId, CHOOSE_OPTION, catShelterInfoButtons());
                    break;
                case CAT_ABOUT_US_CMD:
                    sendMessage(chatId, CAT_ABOUT_US_TEXT);
                    break;
                case CAT_WORKING_HOURS_CMD:
                    sendMessage(chatId, CAT_WORKING_HOURS_TEXT);
                    break;
                case CAT_SECURITY_CONTACT_CMD:
                    sendMessage(chatId, CAT_SECURITY_CONTACT_TEXT);
                    break;
                case CAT_SAFETY_RECOMMENDATION_CMD:
                    sendMessage(chatId, CAT_SAFETY_RECOMMENDATION_TEXT);
                    break;
                case HOW_TO_TAKE_CAT_CMD:
                case BACK_TO_CAT_RECOMMENDATION_MENU_CMD:
                    sendMessage(chatId, CHOOSE_OPTION, catRecommendationButtons());
                    break;
                case MEET_THE_CAT_CMD:
                    sendMessage(chatId, MEET_THE_CAT_TEXT);
                    break;
                case CAT_TRANSPORTING_AND_ADVICE_CMD:
                case BACK_TO_CAT_TRANSPORT_AND_ADVICE_MENU_CMD:
                    sendMessage(chatId, CHOOSE_OPTION, catTransportAndAdviceDogButtons());
                    break;
                case CAT_TRANSPORTING_CMD:
                    sendMessage(chatId, CAT_TRANSPORTING_TEXT);
                    break;
                case CAT_ADVICE_CMD:
                    sendMessage(chatId, CAT_COMMON_ADVICE_TEXT, catSpecificAdviceMenu());
                    break;
                case ADVICE_FOR_KITTEN_CMD:
                    sendMessage(chatId, ADVICE_FOR_KITTEN_TEXT);
                    break;
                case ADVICE_FOR_ADULT_CAT_CMD:
                    sendMessage(chatId, ADVICE_FOR_ADULT_CAT_TEXT);
                    break;
                case ADVICE_FOR_SPECIAL_CAT_CMD:
                    sendMessage(chatId, ADVICE_FOR_SPECIAL_CAT_TEXT);
                    break;
                case FELINOLOGIST_CMD:
                case BACK_TO_FELINOLOGIST_MENU_CMD:
                    sendMessage(chatId, CHOOSE_OPTION, felinologystMenu());
                    break;
                case FELINOLOGIST_ADVICE_CMD:
                    sendMessage(chatId, FELINOLOGIST_ADVICE_TEXT);
                    break;
                case FELINOLOGIST_CONTACTS_CMD:
                    sendMessage(chatId, FELINOLOGIST_CONTACTS_TEXT);
                    break;

                default:
                    if (inputMessage.text().startsWith("+") && inputMessage.text().length() == 12) {
                        sendMessage(VOLUNTEERS_CHAT_ID, "Пожалуйста, свяжитесь с пользователем: https://t.me/" + inputMessage.text());
                        sendMessage(chatId, "Спасибо, волонтер свяжется с вами в ближайшее время");
                    } else if (registrationRequired) {
                        logger.info("Registration data has been sent");
                        sendMessage(chatId, userService.registrationUser(inputMessage));
                        sendMessage(VOLUNTEERS_CHAT_ID, "Пользователь " + userService.getUserByChatId(chatId).getName() +
                                " (" + userService.getUserByChatId(chatId).getPhoneNumber() + ") оставил контакты для связи.");
                        registrationRequired = false;
                    } else if (sendingReportStatus == 1) {
                        sendMessage(chatId, PHOTO_REPORT_REQUIRED);
                        reportService.saveTextReport(inputMessage);
                        sendingReportStatus = 2;
                    } else {
                        sendMessage(chatId, INVALID_NOTIFICATION_OR_CMD);
                    }
            }

        } else if (inputMessage.photo() != null && sendingReportStatus == 2) {
            File file = getFile(inputMessage);
            String filePath = animalShelterBot.getFullFilePath(file);
            Integer fileSize = file.fileSize();
            String outputMessage = null;
            try {
                outputMessage = reportService.handlePhoto(inputMessage, fileSize, filePath);
            } catch (IOException e) {
                e.printStackTrace();
            }
            sendMessage(chatId, outputMessage);
            sendingReportStatus = 0;

        } else {
            sendMessage(chatId, UNKNOWN_FILE);
        }
    }

    private File getFile(Message inputMessage) {
        List<PhotoSize> photos = List.of(inputMessage.photo());
        PhotoSize photo = photos.stream()
                .max(Comparator.comparing(PhotoSize::fileSize)).orElse(null);
        GetFile request = new GetFile(photo.fileId());
        GetFileResponse getFileResponse = animalShelterBot.execute(request);
        File file = getFileResponse.file();
        return file;
    }

    @Override
    public void sendMessage(long chatId, String inputMessage, Keyboard keyboard) {
        SendMessage outputMessage = new SendMessage(chatId, inputMessage)
                .replyMarkup(keyboard);
        try {
            animalShelterBot.execute(outputMessage);
        } catch (Exception e) {
            logger.info("Exception was thrown in sendMessage method with keyboard ");
            e.printStackTrace();
        }
    }

    @Override
    public void sendMessage(long chatId, String inputMessage) {
        SendMessage outputMessage = new SendMessage(chatId, inputMessage);
        try {
            animalShelterBot.execute(outputMessage);
        } catch (Exception e) {
            logger.info("Exception was thrown in sendMessage message method ");
            e.printStackTrace();
        }
    }

    private static ReplyKeyboardMarkup chooseShelter() {
        logger.info("Choose shelter keyboard was called");
        return new ReplyKeyboardMarkup(
                new String[]{CAT_SHELTER_CMD, DOG_SHELTER_CMD})
                .resizeKeyboard(true)
                .selective(true);
    }

    /**
     * Create main menu buttons for dog shelter
     *
     * @return buttons
     */

    private static Keyboard dogStartMenuButtons() {
        return new ReplyKeyboardMarkup(
                new KeyboardButton[]{
                        new KeyboardButton(DOG_SHELTER_INFO_CMD),
                        new KeyboardButton(HOW_TO_TAKE_DOG_CMD),
                },
                new KeyboardButton[]{
                        new KeyboardButton(SEND_REPORT_CMD),
                        new KeyboardButton(CALL_VOLUNTEER_CMD)
                }, new KeyboardButton[]{
                new KeyboardButton(BACK_TO_CHOOSE_ANIMAL)
        })
                .resizeKeyboard(true);
    }

    /**
     * Create "About shelter" menu buttons
     *
     * @return buttons
     */

    private static ReplyKeyboardMarkup dogShelterInfoButtons() {
        logger.info("Dog shelter info keyboard was called");
        return new ReplyKeyboardMarkup(
                new String[]{DOG_ABOUT_US_CMD, DOG_WORKING_HOURS_CMD},
                new String[]{DOG_SAFETY_RECOMMENDATION_CMD, DOG_SECURITY_CONTACT_CMD},
                new String[]{CONTACT_ME_CMD, CALL_VOLUNTEER_CMD},
                new String[]{BACK_TO_DOG_START_MENU_CMD})
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
                new String[]{CALL_VOLUNTEER_CMD, BACK_TO_DOG_START_MENU_CMD})
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

    private static Keyboard catStartMenuButtons() {
        return new ReplyKeyboardMarkup(
                new KeyboardButton[]{
                        new KeyboardButton(CAT_SHELTER_INFO_CMD),
                        new KeyboardButton(HOW_TO_TAKE_CAT_CMD),
                },
                new KeyboardButton[]{
                        new KeyboardButton(SEND_REPORT_CMD),
                        new KeyboardButton(CALL_VOLUNTEER_CMD)
                }, new KeyboardButton[]{
                new KeyboardButton(BACK_TO_CHOOSE_ANIMAL)
        })
                .resizeKeyboard(true);
    }

    /**
     * Create "About shelter" menu buttons
     *
     * @return buttons
     */

    private static ReplyKeyboardMarkup catShelterInfoButtons() {
        logger.info("Cat shelter info keyboard was called");
        return new ReplyKeyboardMarkup(
                new String[]{CAT_ABOUT_US_CMD, CAT_WORKING_HOURS_CMD},
                new String[]{CAT_SAFETY_RECOMMENDATION_CMD, CAT_SECURITY_CONTACT_CMD},
                new String[]{CONTACT_ME_CMD, CALL_VOLUNTEER_CMD},
                new String[]{BACK_TO_CAT_START_MENU_CMD})
                .resizeKeyboard(true);
    }

    /**
     * Create "How to take a cat" menu buttons
     *
     * @return buttons
     */

    private static ReplyKeyboardMarkup catRecommendationButtons() {
        logger.info("Cats recommendations Keyboard was called");
        return new ReplyKeyboardMarkup(
                new String[]{MEET_THE_CAT_CMD, DOCUMENTS_CMD},
                new String[]{CAT_TRANSPORTING_AND_ADVICE_CMD, FELINOLOGIST_CMD},
                new String[]{REFUSAL_REASONS_CMD, CONTACT_ME_CMD},
                new String[]{CALL_VOLUNTEER_CMD, BACK_TO_CAT_START_MENU_CMD})
                .resizeKeyboard(true);
    }

    /**
     * Create menu buttons with recommendations about dog's carriage and home improvement
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
        logger.info("Specific cat advice keyboard was called");
        return new ReplyKeyboardMarkup(
                new String[]{ADVICE_FOR_KITTEN_CMD, ADVICE_FOR_ADULT_CAT_CMD, ADVICE_FOR_SPECIAL_CAT_CMD},
                new String[]{BACK_TO_CAT_TRANSPORT_AND_ADVICE_MENU_CMD}
        )
                .resizeKeyboard(true);
    }

    /**
     * Create menu buttons with felinologists contacts and recommendations
     *
     * @return buttons
     */

    private static ReplyKeyboardMarkup felinologystMenu() {
        logger.info("Cynologist keyboard was called");
        return new ReplyKeyboardMarkup(
                new String[]{FELINOLOGIST_ADVICE_CMD, FELINOLOGIST_CONTACTS_CMD},
                new String[]{BACK_TO_CAT_RECOMMENDATION_MENU_CMD}
        )
                .resizeKeyboard(true);
    }

}
