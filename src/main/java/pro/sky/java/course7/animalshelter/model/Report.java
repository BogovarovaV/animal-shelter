package pro.sky.java.course7.animalshelter.model;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Objects;


@Entity
@Table(name = "reporting")
public class Report {

    public enum ReportStatus {

        SENT,
        ACCEPTED,
        DECLINED,
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name = "id_user")
    private Long clientId;

    @Column(name = "report_text")
    private String reportText;

    @Column(name = "file_path")
    private String filePath;

    @Column(name = "file_size")
    private Integer fileSize;

    @Lob
    @Column(name = "preview")
    private byte[] preview;

    @Column(name = "sent_date")
    private LocalDate sentDate;

    @ManyToOne
    @JoinColumn(name = "id_user", insertable = false, updatable = false)
    public User user;

    @Enumerated(EnumType.STRING)
    private Report.ReportStatus status = Report.ReportStatus.SENT;


    public Report() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
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

    public Integer getFileSize() {
        return fileSize;
    }

    public void setFileSize(Integer fileSize) {
        this.fileSize = fileSize;
    }

    public LocalDate getSentDate() {
        return sentDate;
    }

    public void setSentDate(LocalDate sentDate) {
        this.sentDate = sentDate;
    }

    public ReportStatus getStatus() {
        return status;
    }

    public void setStatus(ReportStatus status) {
        this.status = status;
    }

    public byte[] getPreview() {
        return preview;
    }

    public void setPreview(byte[] preview) {
        this.preview = preview;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Report)) return false;
        Report report = (Report) o;
        return id.equals(report.id) && fileSize.equals(report.fileSize) && Objects.equals(clientId, report.clientId) && Objects.equals(reportText, report.reportText) && Objects.equals(filePath, report.filePath) && Arrays.equals(preview, report.preview) && Objects.equals(sentDate, report.sentDate) && Objects.equals(user, report.user) && status == report.status;
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(id, clientId, reportText, filePath, fileSize, sentDate, user, status);
        result = 31 * result + Arrays.hashCode(preview);
        return result;
    }

    @Override
    public String toString() {
        return "Report{" +
                "id=" + id +
                ", clientId=" + clientId +
                ", reportText='" + reportText + '\'' +
                ", filePath='" + filePath + '\'' +
                ", fileSize=" + fileSize +
                ", preview=" + Arrays.toString(preview) +
                ", sentDate=" + sentDate +
                ", user=" + user +
                ", status=" + status +
                '}';
    }
}
