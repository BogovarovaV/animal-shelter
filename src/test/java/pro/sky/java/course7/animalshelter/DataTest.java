package pro.sky.java.course7.animalshelter;

import pro.sky.java.course7.animalshelter.model.Animal;
import pro.sky.java.course7.animalshelter.model.User;

import java.time.LocalDate;

import static pro.sky.java.course7.animalshelter.model.Animal.AnimalTypes.NO_ANIMAL;


public interface DataTest {

    long ANIMAL_ID = 1;
    Animal.AnimalTypes TYPE = NO_ANIMAL;

    long USER_ID_1 = 0;
    long USER_CHAT_ID_1 = 0;
    String USER_NAME_1 = "Джоуи Триббиани";
    String USER_PHONE_1 = "+79253572945";
    String USER_EMAIL_1 = "howyoudoing@gmail.com";
    User.UserStatus USER_STATUS_1 = User.UserStatus.GUEST;

    long USER_ID_2 = 1;
    long USER_CHAT_ID_2 = 1;
    String USER_NAME_2 = "Росс Геллер";
    String USER_PHONE_2 = "+71231231231";
    String USER_EMAIL_2 = "marriedalesbian@gmail.com";
    User.UserStatus USER_STATUS_2 = User.UserStatus.GUEST;


    String USER_MESSAGE_1 = "Джоуи Триббиани +79253572945 howyoudoing@gmail.com";


    User.UserStatus USER_STATUS_3 = User.UserStatus.ADOPTER_ON_TRIAL;
    LocalDate TEST_TRIAL_DATE = LocalDate.of(2022,12,1);


    String REPORT_TEXT_1 = "Все ок";
    String FILE_PATH_1 = "https://api.telegram.org/photo1.jpg";
    LocalDate SENT_DATE_1 = LocalDate.of(2022,1,1);

    String REPORT_TEXT_2 = "Все прекрасно";
    String FILE_PATH_2 = "https://api.telegram.org/photo2.jpg";
    LocalDate SENT_DATE_2 = LocalDate.of(2020,2,4);


}
