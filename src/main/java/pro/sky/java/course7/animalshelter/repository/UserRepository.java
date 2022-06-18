package pro.sky.java.course7.animalshelter.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import pro.sky.java.course7.animalshelter.model.Animal;
import pro.sky.java.course7.animalshelter.model.User;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;


public interface UserRepository extends JpaRepository<User, Long> {

    @Query("select u from User u where u.id = ?1")
    User findUserById(long id);

    @Query("select u from User u where u.chatId = ?1")
    User findUserByChatId (long chatId);

    @Query("select u from User u where u.status = ?1")
    List<User> findAllAdopters (User.UserStatus status);

}
