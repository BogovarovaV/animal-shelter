package pro.sky.java.course7.animalshelter.model;


import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "Cats_adopters")
public class CatAdopter extends User{

    public CatAdopter() {
    }
}
