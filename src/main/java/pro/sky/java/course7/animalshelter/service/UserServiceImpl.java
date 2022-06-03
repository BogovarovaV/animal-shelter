package pro.sky.java.course7.animalshelter.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pro.sky.java.course7.animalshelter.model.User;
import pro.sky.java.course7.animalshelter.repository.UserRepository;

import java.util.Collection;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static pro.sky.java.course7.animalshelter.model.User.UserStatus.USER;

@Service
public class UserServiceImpl implements UserService {

    private final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    private static final String REGEX_BOT_MESSAGE = "([\\W+]+)(\\s)(\\+7\\d{3}[-.]?\\d{3}[-.]?\\d{4})(\\s)([a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+)";

    private final UserRepository repository;

    public UserServiceImpl(UserRepository repository) {
        this.repository = repository;
    }

    /**
     * Create a user in repository trough swagger or postman, without bot
     *
     * @param user - created by volunteer
     * @return saved user, filled non-automatically
     */

    @Override
    public User createUser(User user) {
        logger.info("Was invoked method to create a user by volunteer");
        return repository.save(user);
    }

    @Override
    public User editUser(User user, User.UserStatus status) {
        logger.info("Was invoked method to create a user by volunteer");
     //   User result = repository.save(user);
        user.setStatus(status);
        logger.info("status in editUser" + status);
        return repository.save(user);
    }

    /**
     * Save created user in repository
     *
     * @param user   - created user
     * @param chatId - user's chat id
     * @return savedUser - user's data which was saved in repository
     */

    @Override
    public User save(User user, long chatId) {
        user.setChatId(chatId);
        user.setStatus(USER);
        User savedUser = repository.save(user);
        return savedUser;
    }

    @Override
    public User edit(User user, long chatId, User.UserStatus status) {
        user.setChatId(chatId);
        user.setStatus(status);
        User editedUser = repository.save(user);
        logger.info("Current status3: " + status);
        logger.info("Client's data has been edited successfully: " + editedUser);
        return editedUser;
    }

    /**
     * Parsing user's data to name, phone number, email
     *
     * @param userDataMessage received message from user
     * @return parsing result
     */

    @Override
    public Optional<User> parse(String userDataMessage) {
        logger.info("Parsing method has been called");
        Pattern pattern = Pattern.compile(REGEX_BOT_MESSAGE);
        Matcher matcher = pattern.matcher(userDataMessage);
        User result = null;
        try {
            if (matcher.find()) {
                String name = matcher.group(1);
                String phoneNumber = matcher.group(3);
                String email = matcher.group(5);
                result = new User(name, phoneNumber, email);
            }
        } catch (Exception e) {
            logger.error("Failed to parse user's data: " + userDataMessage, e);
        }
        return Optional.ofNullable(result);
    }

    @Override
    public User getUserById(long id) {
        logger.info("Was invoked method to find a user by Id");
        return repository.findById(id).orElse(null);
    }

    @Override
    public User getUserByChatId(long chatId) {
        logger.info("Was invoked method to find user by chatId");
        return repository.findUserByChatId(chatId);
    }

    @Override
    public void deleteUserById(long id) {
        logger.info("Was invoked method to delete a client by Id");
        repository.deleteById(id);
    }


    @Override
    public void deleteUserByChatId(long chatId) {
        logger.info("Was invoked method to delete a client by ChatId");
        repository.deleteById(repository.findUserByChatId(chatId).getId());
    }

    @Override
    public Collection<User> getAllUsers() {
        logger.info("Was invoked method to get a list of all users");
        return repository.findAll();
    }

}
