package pro.sky.java.course7.animalshelter;

import pro.sky.java.course7.animalshelter.model.Animal;
import pro.sky.java.course7.animalshelter.model.Report;
import pro.sky.java.course7.animalshelter.model.User;

import java.time.LocalDate;

import static pro.sky.java.course7.animalshelter.model.Animal.AnimalTypes.NO_ANIMAL;


public interface DataTest {

    Long ANIMAL_ID = 1L;
    Animal.AnimalTypes TYPE = NO_ANIMAL;

    LocalDate SENT_DATE = LocalDate.now().minusDays(1);

    Long USER_ID_1 = null;
    Long USER_CHAT_ID_1 = null;
    Long USER_ID_3 = 1L;
    Long USER_CHAT_ID_3 = 1L;
    String USER_NAME_1 = "Джоуи Триббиани";
    String USER_PHONE_1 = "+79253572945";
    String USER_EMAIL_1 = "howyoudoing@gmail.com";
    User.UserStatus USER_STATUS_1 = User.UserStatus.GUEST;

    Long USER_ID_2 = 2L;
    Long USER_CHAT_ID_2 = 2L;
    String USER_NAME_2 = "Росс Геллер";
    String USER_PHONE_2 = "+71231231231";
    String USER_EMAIL_2 = "marriedalesbian@gmail.com";
    User.UserStatus USER_STATUS_2 = User.UserStatus.GUEST;


    String USER_MESSAGE_1 = "Джоуи Триббиани +79253572945 howyoudoing@gmail.com";


    User.UserStatus USER_STATUS_3 = User.UserStatus.ADOPTER_ON_TRIAL;
    LocalDate TEST_TRIAL_DATE = LocalDate.of(2022,12,1);
    LocalDate START_TRIAL_DATE = LocalDate.now().minusDays(30);


    Long REPORT_ID = 1L;
    String REPORT_TEXT_1 = "Все ок";
    String FILE_PATH_1 = "https://api.telegram.org/photo1.jpg";
    LocalDate SENT_DATE_1 = LocalDate.of(2022,1,1);
    Integer FILE_SIZE_1 = 1024;
    Report.ReportStatus REPORT_STATUS_1 = Report.ReportStatus.SENT;

    String REPORT_TEXT_2 = "Все прекрасно";
    String FILE_PATH_2 = "https://api.telegram.org/photo2.jpg";
    LocalDate SENT_DATE_2 = LocalDate.of(2020,2,4);


}
