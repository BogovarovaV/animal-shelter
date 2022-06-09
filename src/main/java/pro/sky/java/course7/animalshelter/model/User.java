package pro.sky.java.course7.animalshelter.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "quest")
public class User {

    public enum UserStatus {

        GUEST,
        ADOPTER_ON_TRIAL,
        OWNER
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "chatId")
    private long chatId;

    @Column(name = "name")
    private String name;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "email")
    private String email;

    @ManyToOne
    @JoinColumn(name = "animal_type", referencedColumnName = "type")
    private Animal animal;

    @Enumerated(EnumType.STRING)
    private UserStatus status;


    public User() {
    }

    public User(String name, String phoneNumber, String email) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    public User(String name, String phoneNumber, String email, Animal animal) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.animal = animal;
    }

    public Animal getAnimal() {
        return animal;
    }

    public void setAnimal(Animal animal) {
        this.animal = animal;
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
        return getId() == user.getId() && getChatId() == user.getChatId() && getName().equals(user.getName()) && getPhoneNumber().equals(user.getPhoneNumber()) && getEmail().equals(user.getEmail()) && getStatus() == user.getStatus() && getAnimal().equals(user.getAnimal());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getChatId(), getName(), getPhoneNumber(), getEmail(), getStatus(), getAnimal());
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
                ", animal=" + animal +
                '}';
    }
}
