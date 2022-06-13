package pro.sky.java.course7.animalshelter.service;

import com.pengrad.telegrambot.model.File;
import com.pengrad.telegrambot.model.Message;
import org.springframework.web.multipart.MultipartFile;
import pro.sky.java.course7.animalshelter.model.Report;
import pro.sky.java.course7.animalshelter.model.User;

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

    String handlePhoto(Message message, Integer fileSize, String filePath);
}
