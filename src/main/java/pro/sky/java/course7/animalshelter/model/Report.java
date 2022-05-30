package pro.sky.java.course7.animalshelter.model;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Objects;


@Entity
public class Report {

    public enum ReportStatus {

        SENT,

        ACCEPTED,

        NEEDS_IMPROVEMENTS,

        DECLINED,
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private long id;

    private long chatId;

    private String reportText;

    private String filePath;

    private long fileSize;

    private String mediaType;

    private LocalDateTime sentDate;

    @Lob
    @Type(type = "org.hibernate.type.ImageType")
    private byte[] data;


    @Enumerated(EnumType.STRING)
    private Report.ReportStatus status = Report.ReportStatus.SENT;


    public Report() {
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

    public String getMediaType() {
        return mediaType;
    }

    public void setMediaType(String mediaType) {
        this.mediaType = mediaType;
    }

    public LocalDateTime getSentDate() {
        return sentDate;
    }

    public void setSentDate(LocalDateTime sentDate) {
        this.sentDate = sentDate;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
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
        return getId() == report.getId() && getChatId() == report.getChatId() && getFileSize() == report.getFileSize() && getReportText().equals(report.getReportText()) && getFilePath().equals(report.getFilePath()) && getMediaType().equals(report.getMediaType()) && getSentDate().equals(report.getSentDate()) && Arrays.equals(getData(), report.getData()) && getStatus() == report.getStatus();
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(getId(), getChatId(), getReportText(), getFilePath(), getFileSize(), getMediaType(), getSentDate(), getStatus());
        result = 31 * result + Arrays.hashCode(getData());
        return result;
    }

    @Override
    public String toString() {
        return "Report{" +
                "id=" + id +
                ", chatId=" + chatId +
                ", reportText='" + reportText + '\'' +
                ", filePath='" + filePath + '\'' +
                ", fileSize=" + fileSize +
                ", mediaType='" + mediaType + '\'' +
                ", sentDate=" + sentDate +
                ", data=" + Arrays.toString(data) +
                ", status=" + status +
                '}';
    }
}
