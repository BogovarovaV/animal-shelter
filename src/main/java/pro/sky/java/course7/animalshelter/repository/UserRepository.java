package pro.sky.java.course7.animalshelter.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pro.sky.java.course7.animalshelter.model.User;

import java.time.LocalDate;
import java.util.List;


public interface UserRepository extends JpaRepository<User, Long> {

    @Query("select u from User u where u.id = ?1")
    User findUserById(long id);

    @Query("select u from User u where u.chatId = ?1")
    User findUserByChatId (long chatId);

    @Query("select u from User u where u.status = ?1 and u.endTrialDate = ?2")
    List<User> findAdoptersWithEndOfTrial (User.UserStatus status, LocalDate endTrialDate);

}
