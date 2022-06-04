package pro.sky.java.course7.animalshelter;

import pro.sky.java.course7.animalshelter.model.User;

import java.time.LocalDateTime;

public interface DataTest {

    long USER_ID_1 = 1L;
    long USER_CHAT_ID_1 = 123L;
    String USER_NAME_1 = "Джоуи Триббиани";
    String USER_PHONE_1 = "+79253572945";
    String USER_EMAIL_1 = "howyoudoing@gmail.com";
    User.UserStatus USER_STATUS_1 = User.UserStatus.CAT_ADOPTER;

    long USER_ID_2 = 2L;
    long USER_CHAT_ID_2 = 321L;
    String USER_NAME_2 = "Росс Геллер";
    String USER_PHONE_2 = "+71231231231";
    String USER_EMAIL_2 = "marriedalesbian@gmail.com";
    User.UserStatus USER_STATUS_2 = User.UserStatus.DOG_ADOPTER;

    String USER_MESSAGE_1 = "Джоуи Триббиани +79253572945 howyoudoing@gmail.com";


    String REPORT_TEXT_1 = "Все ок";
    String FILE_PATH_1 = "https://api.telegram.org/photo1.jpg";
    LocalDateTime SENT_DATE_1 = LocalDateTime.of(2022,01,01,15,15);

    String REPORT_TEXT_2 = "Все прекрасно";
    String FILE_PATH_2 = "https://api.telegram.org/photo2.jpg";
    LocalDateTime SENT_DATE_2 = LocalDateTime.of(2020,02,04,11,10);


}
