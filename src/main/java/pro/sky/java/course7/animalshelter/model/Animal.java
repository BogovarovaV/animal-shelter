package pro.sky.java.course7.animalshelter.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name="animal")
public class Animal implements Serializable {

    public enum AnimalTypes {

        DOG,

        CAT,

        NO_ANIMAL
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @JsonIgnore
    private long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private AnimalTypes type;

    @OneToMany(mappedBy = "animal",cascade = CascadeType.ALL)
    @JsonIgnore
    public Set<User> users;

    public Animal() {
    }

    public Animal(long id, AnimalTypes type) {
        this.id = id;
        this.type = type;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public AnimalTypes getType() {
        return type;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Animal)) return false;
        Animal animal = (Animal) o;
        return id == animal.id && type == animal.type && Objects.equals(users, animal.users);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, type, users);
    }

    @Override
    public String toString() {
        return "Animal{" +
                "id=" + id +
                ", type=" + type +
                ", users=" + users +
                '}';
    }
}



