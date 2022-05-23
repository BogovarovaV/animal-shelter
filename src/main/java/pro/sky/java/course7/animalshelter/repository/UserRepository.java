package pro.sky.java.course7.animalshelter.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pro.sky.java.course7.animalshelter.model.User;

import java.util.Collection;
import java.util.List;


public interface UserRepository extends JpaRepository<User, Long > {
}
