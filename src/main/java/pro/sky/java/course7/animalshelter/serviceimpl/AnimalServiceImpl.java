package pro.sky.java.course7.animalshelter.serviceimpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pro.sky.java.course7.animalshelter.model.Animal;
import pro.sky.java.course7.animalshelter.repository.AnimalRepository;
import pro.sky.java.course7.animalshelter.service.AnimalService;

@Service
public class AnimalServiceImpl implements AnimalService {

    private final AnimalRepository animalRepository;


    public AnimalServiceImpl(AnimalRepository animalRepository) {
        this.animalRepository = animalRepository;
    }

    @Override
    public Animal getAnimalByName(Animal.AnimalTypes type) {

        return animalRepository.getAnimalBy(type);
    }
}
