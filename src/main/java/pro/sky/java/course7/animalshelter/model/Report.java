package pro.sky.java.course7.animalshelter.model;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;


@Entity
@Table(name = "report")
public class Report {

    public enum ReportStatus {

        REQUIRED_TEXT,

        REQUIRED_PHOTO,

        SENT,

        ACCEPTED,

        NEEDS_IMPROVEMENTS,

        DECLINED,
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private long id;

    private Long userChatId;

    private String reportText;

    private String filePath;

    private LocalDateTime sentDate;

//    @Lob
//    @Type(type = "org.hibernate.type.ImageType")
//    private byte[] data;

    @ManyToOne
    @JoinColumn(name = "userChatId", insertable = false, updatable = false)
    public User user;

    @Enumerated(EnumType.STRING)
    private Report.ReportStatus status = Report.ReportStatus.SENT;


    public Report() {
    }

    public Report(String reportText, String filePath, LocalDateTime sentDate) {
        this.reportText = reportText;
        this.filePath = filePath;
        this.sentDate = sentDate;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUserChatId() {
        return userChatId;
    }

    public void setUserChatId(long userChatId) {
        this.userChatId = userChatId;
    }

    public String getReportText() {
        return reportText;
    }

    public void setReportText(String reportText) {
        this.reportText = reportText;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public LocalDateTime getSentDate() {
        return sentDate;
    }

    public void setSentDate(LocalDateTime sentDate) {
        this.sentDate = sentDate;
    }

//    public byte[] getData() {
//        return data;
//    }
//
//    public void setData(byte[] data) {
//        this.data = data;
//    }

    public ReportStatus getStatus() {
        return status;
    }

    public void setStatus(ReportStatus status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Report)) return false;
        Report report = (Report) o;
        return getId() == report.getId() && getUserChatId() == report.getUserChatId() && getReportText().equals(report.getReportText()) && getFilePath().equals(report.getFilePath()) && getSentDate().equals(report.getSentDate()) && getStatus() == report.getStatus();
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(getId(), getUserChatId(), getReportText(), getFilePath(), getSentDate(), getStatus());
        return result;
    }

    @Override
    public String toString() {
        return "Report{" +
                "id=" + id +
                ", userChatId=" + userChatId +
                ", reportText='" + reportText + '\'' +
                ", filePath='" + filePath + '\'' +
                ", sentDate=" + sentDate +
                ", status=" + status +
                '}';
    }
}
