package pro.sky.java.course7.animalshelter.serviceimpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pro.sky.java.course7.animalshelter.model.Report;
import pro.sky.java.course7.animalshelter.repository.ReportRepository;
import pro.sky.java.course7.animalshelter.service.ReportService;

import java.util.List;

@Service
public class ReportServiceImpl implements ReportService {

    private final Logger logger = LoggerFactory.getLogger(ReportServiceImpl.class);
    private final ReportRepository repository;

    public ReportServiceImpl(ReportRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Report> getReportsByUserChatId(Long userChatId) {
        logger.info("Was invoked method to find a report by User's Id");
        return this.repository.findByUserChatId(userChatId);
    }

    @Override
    public Report saveReport(Report report, long chatId) {
        report.setUserChatId(chatId);
        Report savedReport = repository.save(report);
        logger.info("Client's report has been saved successfully: " + savedReport);
        return savedReport;
    }
}
