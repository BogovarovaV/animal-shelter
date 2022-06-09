package pro.sky.java.course7.animalshelter.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pro.sky.java.course7.animalshelter.model.Animal;
import pro.sky.java.course7.animalshelter.model.User;

import java.util.Set;


public interface UserRepository extends JpaRepository<User, Long> {

    User findUserByChatId(long chatId);

//    @Query("SELECT g FROM  Guest  WHERE g.animal_type = :type")
//    Set<User> findUserByAnimalType (Animal.AnimalTypes type);

}
