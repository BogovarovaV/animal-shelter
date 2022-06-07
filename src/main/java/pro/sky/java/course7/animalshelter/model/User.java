package pro.sky.java.course7.animalshelter.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "Client")
@SecondaryTable(name = "Dog_adopters")
@SecondaryTable(name ="Cat_adopters")
public class User {

    public enum UserStatus {

        USER,

        REQUESTED,

        TRIAL,

        TRIAL_14_MORE,

        TRIAL_30_MORE,

        CAT_ADOPTER,

        DOG_ADOPTER,

        VOLUNTEER,
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private long chatId;

    private String name;

    private String phoneNumber;

    private String email;

    @Enumerated(EnumType.STRING)
    private UserStatus status;

    @ManyToOne
    @JoinColumn (name = "animal_type", table = "Dog_adopters")
    @JoinColumn (name = "animal_type", table = "Cat_adopters")
    private AnimalTypes animalTypes;

    public User() {
    }

    public User(String name, String phoneNumber, String email) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    public AnimalTypes getAnimalTypes() {
        return animalTypes;
    }

    public void setAnimalTypes(AnimalTypes animalTypes) {
        this.animalTypes = animalTypes;
    }


    public UserStatus getStatus() {
        return status;
    }

    public void setStatus(UserStatus status) {
        this.status = status;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getChatId() {
        return chatId;
    }

    public void setChatId(long chatId) {
        this.chatId = chatId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return getId() == user.getId() && getChatId() == user.getChatId() && getName().equals(user.getName()) && getPhoneNumber().equals(user.getPhoneNumber()) && getEmail().equals(user.getEmail()) && getStatus() == user.getStatus() && getAnimalTypes().equals(user.getAnimalTypes());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getChatId(), getName(), getPhoneNumber(), getEmail(), getStatus(), getAnimalTypes());
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", chatId=" + chatId +
                ", name='" + name + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", email='" + email + '\'' +
                ", status=" + status +
                ", animalTypes=" + animalTypes +
                '}';
    }
}
