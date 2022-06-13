package pro.sky.java.course7.animalshelter.model;


import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "client")
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

    @Column(name = "chat_id")
    private long chatId;

    @Column(name = "name")
    private String name;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "email")
    private String email;

    @Enumerated(EnumType.STRING)
    private UserStatus status;

    @ManyToOne
    @JoinColumn(name = "animal_type", referencedColumnName = "type")
    private Animal animal;

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    private List<Report> reportList;

    @Column(name = "start_trial_date")
    private LocalDate startTrialDate;

    @Column (name = "end_trial_date")
    private LocalDate endTrialDate;

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

//    public List<Report> getReportList() {
//        return reportList;
//    }

 //   public void setReportList(List<Report> reportList) {
//        this.reportList = reportList;
//    }

    public LocalDate getStartTrialDate() {
        return startTrialDate;
    }

    public void setStartTrialDate(LocalDate startTrialDate) {
        this.startTrialDate = startTrialDate;
    }

    public LocalDate getEndTrialDate() {
        return endTrialDate;
    }

    public void setEndTrialDate(LocalDate endTrialDate) {
        this.endTrialDate = endTrialDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return id == user.id && chatId == user.chatId && Objects.equals(name, user.name) && Objects.equals(phoneNumber, user.phoneNumber) && Objects.equals(email, user.email) && status == user.status && Objects.equals(animal, user.animal)// && Objects.equals(reportList, user.reportList)
         && Objects.equals(startTrialDate, user.startTrialDate) && Objects.equals(endTrialDate, user.endTrialDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, chatId, name, phoneNumber, email, status, animal,// reportList,
                 startTrialDate, endTrialDate);
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
 //               ", reportList=" + reportList +
                ", startTrialDate=" + startTrialDate +
                ", endTrialDate=" + endTrialDate +
                '}';
    }
}
