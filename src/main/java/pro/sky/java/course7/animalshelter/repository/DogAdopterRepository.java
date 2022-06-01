package pro.sky.java.course7.animalshelter.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pro.sky.java.course7.animalshelter.model.DogAdopter;

public interface DogAdopterRepository extends JpaRepository<DogAdopter, Long> {
}
