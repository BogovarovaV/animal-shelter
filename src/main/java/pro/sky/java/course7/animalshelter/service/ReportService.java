package pro.sky.java.course7.animalshelter.service;

import pro.sky.java.course7.animalshelter.model.Report;

import java.util.List;

public interface ReportService {

    List<Report> getReportsByUserChatId(Long chatId);

    Report saveReport(Report report, long userChatId);
}
