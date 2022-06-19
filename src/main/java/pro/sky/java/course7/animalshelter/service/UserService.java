package pro.sky.java.course7.animalshelter.service;

import com.pengrad.telegrambot.model.Message;
import pro.sky.java.course7.animalshelter.model.Animal;
import pro.sky.java.course7.animalshelter.model.Report;
import pro.sky.java.course7.animalshelter.model.User;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface UserService {

    User save(User user);

    User edit(Long id, Long chatId, User user, User.UserStatus status, Animal.AnimalTypes type);

    User createUserByVolunteer(User user, Animal.AnimalTypes type);

    Optional<User> parse(String userDataMessage, long chatId);

    String registrationUser(Message inputMessage);

    User getUserById(long id);

    User getUserByChatId(long chatId);

    void deleteUserById(long id);

    Collection<User> getAllUsers();

    List<User> getAllAdopters(User.UserStatus status);

    boolean adopterOnTrialExist(long id);

    List<User> getAdoptersWithEndOfTrial(User.UserStatus status, LocalDate endTrialDate);

    List<User> findAdoptersByReportStatusAndSentDate(Report.ReportStatus reportStatus, LocalDate sentDate);

    List<User>findAdoptersByStatusAndReportDate(User.UserStatus status, LocalDate sentDate);

    List<User> findAdoptersByStatusAndExtendedTrial(User.UserStatus status);
}
