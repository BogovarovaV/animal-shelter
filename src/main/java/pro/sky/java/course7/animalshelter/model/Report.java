package pro.sky.java.course7.animalshelter.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;


@Entity
@Table(name = "reporting")
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
    @Column(name = "id")
    private long id;

//    @Column(name = "client_id")
//    private Long clientId;

    @Column(name = "report_text")
    private String reportText;

    @Column(name = "file_path")
    private String filePath;

    @Column(name = "file_size")
    private long fileSize;

//    @Lob
//    private byte[] preview;

    @Column(name = "sent_date")
    private LocalDate sentDate;

    @ManyToOne
    @JoinColumn(name = "id_user", referencedColumnName = "id", insertable = false, updatable = false)
    public User user;

    @Enumerated(EnumType.STRING)
    private Report.ReportStatus status = Report.ReportStatus.SENT;


    public Report() {
    }

    public Report(long id, String reportText, String filePath, long fileSize, LocalDate sentDate, User user, ReportStatus status) {
        this.id = id;
        this.reportText = reportText;
        this.filePath = filePath;
        this.fileSize = fileSize;
        this.sentDate = sentDate;
        this.user = user;
        this.status = status;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public long getFileSize() {
        return fileSize;
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }

    public LocalDate getSentDate() {
        return sentDate;
    }

    public void setSentDate(LocalDate sentDate) {
        this.sentDate = sentDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

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
        return id == report.id && fileSize == report.fileSize && Objects.equals(reportText, report.reportText) && Objects.equals(filePath, report.filePath) && Objects.equals(sentDate, report.sentDate) && Objects.equals(user, report.user) && status == report.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, reportText, filePath, fileSize, sentDate, user, status);
    }

    @Override
    public String toString() {
        return "Report{" +
                "id=" + id +
                ", reportText='" + reportText + '\'' +
                ", filePath='" + filePath + '\'' +
                ", fileSize=" + fileSize +
                ", sentDate=" + sentDate +
                ", user=" + user +
                ", status=" + status +
                '}';
    }
}
