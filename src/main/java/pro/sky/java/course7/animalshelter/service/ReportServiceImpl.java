package pro.sky.java.course7.animalshelter.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pro.sky.java.course7.animalshelter.model.Report;
import pro.sky.java.course7.animalshelter.repository.ReportRepository;

@Service
public class ReportServiceImpl implements ReportService {

    private final Logger logger = LoggerFactory.getLogger(ReportServiceImpl.class);
    private final ReportRepository repository;

    public ReportServiceImpl(ReportRepository repository) {
        this.repository = repository;
    }

//    @Override
//    public Report getReportByUserId(Long id) {
//        logger.info("Was invoked method to find a report by User's Id");
//        return this.repository.findReportByUserId(id).orElse(null);
//    }

    @Override
    public Report saveReport(Report report, long chatId) {
        report.setUserChatId(chatId);
        Report savedReport = repository.save(report);
        logger.info("Client's report has been saved successfully: " + savedReport);
        return savedReport;
    }
}
