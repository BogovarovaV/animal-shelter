package pro.sky.java.course7.animalshelter.service;

import com.pengrad.telegrambot.model.Message;
import pro.sky.java.course7.animalshelter.model.Report;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.List;

public interface ReportService {

    List<Report> findByUserId(long userId);

    Report findById(long id);

    Report findLastReportByUserId(long userId);

    LocalDate getDateOfLastReportByUserId(long userId);

    Report saveReport(Report report);

    boolean reportWasSentToday(LocalDate messageDate, long userId);

    Report saveTextReport(Message inputMessage);

    String handlePhoto(Message message, Integer fileSize, String filePath) throws IOException;

    File downloadFile(String filePath, Message message) throws IOException;

    byte[] generatePhotoPreview(String filePath) throws IOException;

}
