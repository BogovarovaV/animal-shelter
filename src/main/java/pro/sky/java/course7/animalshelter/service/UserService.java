package pro.sky.java.course7.animalshelter.service;

import pro.sky.java.course7.animalshelter.model.User;

import java.util.Optional;

public interface UserService {
    User save(User user, Long chatId);

    Optional<User> parse(String userDataMessage);
}
