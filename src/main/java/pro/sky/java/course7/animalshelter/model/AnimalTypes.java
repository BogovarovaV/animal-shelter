package pro.sky.java.course7.animalshelter.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "Dog_adopters")
@SecondaryTable(name = "Cat_adopters")
public class AnimalTypes {

    public enum AnimalType {

        DOG,

        CAT,
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private Long userChatId;



    @Column(table = "Dog_adopters")
    @Enumerated(EnumType.STRING)
    private final AnimalType type = AnimalType.DOG;

    @Column(table = "Cat_adopters")
    @Enumerated(EnumType.STRING)
    private final AnimalType type2 = AnimalType.CAT;

    @JsonIgnore
    @Column(table = "Dog_adopters")
    @OneToMany(mappedBy = "Dog_adopters")
    @JoinColumn(name = "chat_id", insertable = false, updatable = false)
    public Set<User> users;

    @JsonIgnore
    @Column(table = "Cat_adopters")
    @OneToMany(mappedBy = "Cat_adopters")
    @JoinColumn(name = "chat_id", insertable = false, updatable = false)
    public Set<User> users2;


    public AnimalTypes() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Long getUserChatId() {
        return userChatId;
    }

    public void setUserChatId(Long userChatId) {
        this.userChatId = userChatId;
    }

    public AnimalType getType() {
        return type;
    }

    public AnimalType getType2() {
        return type2;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public Set<User> getUsers2() {
        return users2;
    }

    public void setUsers2(Set<User> users2) {
        this.users2 = users2;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AnimalTypes)) return false;
        AnimalTypes that = (AnimalTypes) o;
        return getId() == that.getId() && getUserChatId().equals(that.getUserChatId()) && getType() == that.getType() && getType2() == that.getType2() && getUsers().equals(that.getUsers()) && getUsers2().equals(that.getUsers2());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getUserChatId(), getType(), getType2(), getUsers(), getUsers2());
    }

    @Override
    public String toString() {
        return "AnimalTypes{" +
                "id=" + id +
                ", userChatId=" + userChatId +
                ", type=" + type +
                ", type2=" + type2 +
                ", users=" + users +
                ", users2=" + users2 +
                '}';
    }
}



