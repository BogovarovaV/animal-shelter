package pro.sky.java.course7.animalshelter.service;
import com.pengrad.telegrambot.request.SendMessage;
import pro.sky.java.course7.animalshelter.model.Animal;
import pro.sky.java.course7.animalshelter.model.User;

import java.util.Collection;
import java.util.Optional;

public interface UserService {

    User createUser(User user);

    User save(User user, long chatId);

    User edit(User user, long id, long chatId, User.UserStatus status);

    Optional<User> parse(String userDataMessage);

    String registrationUser(String inputMessage, Long chatId);

    User getUserById(long id);

    User getUserByChatId(long chatId);

    void deleteUserById(long id);

    void deleteUserByChatId(long chatId);

    Collection<User> getAllUsers();

}
