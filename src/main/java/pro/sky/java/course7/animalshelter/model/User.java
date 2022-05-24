package pro.sky.java.course7.animalshelter.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class User {

    public enum UserStatus {
        REQUESTED,

        SHELTERED,
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private long chatId;
    private String name;

    private String phoneNumber;

    private String email;

    private int age;

    private String userMessage;

    @Enumerated(EnumType.STRING)
    private UserStatus status = UserStatus.REQUESTED;


    public User() {
    }

    public User(String name, String phoneNumber, String email, int age, Long id) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.age = age;
        this.id = id;
    }

    public String getUserMessage() {
        return userMessage;
    }

    public void setUserMessage(String userMessage) {
        this.userMessage = userMessage;
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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return getId() == user.getId() && getChatId() == user.getChatId() && getAge() == user.getAge() && getName().equals(user.getName()) && getPhoneNumber().equals(user.getPhoneNumber()) && getEmail().equals(user.getEmail()) && getUserMessage().equals(user.getUserMessage()) && getStatus() == user.getStatus();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getChatId(), getName(), getPhoneNumber(), getEmail(), getAge(), getUserMessage(), getStatus());
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", chatId=" + chatId +
                ", name='" + name + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", email='" + email + '\'' +
                ", age=" + age +
                ", userMessage='" + userMessage + '\'' +
                ", status=" + status +
                '}';
    }
}
