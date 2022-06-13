package pro.sky.java.course7.animalshelter.serviceimpl;

import com.pengrad.telegrambot.model.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pro.sky.java.course7.animalshelter.model.Report;
import pro.sky.java.course7.animalshelter.repository.ReportRepository;
import pro.sky.java.course7.animalshelter.service.ReportService;
import pro.sky.java.course7.animalshelter.service.UserService;

import java.time.LocalDate;
import java.util.List;


@Service
@Transactional
public class ReportServiceImpl implements ReportService {

    @Value("report_photo")
    //("${report.photo.dir.path}$")
    private String coverDir;

    private final Logger logger = LoggerFactory.getLogger(ReportServiceImpl.class);
    private final ReportRepository repository;
    private final UserService userService;
    private Report report = new Report();

    public ReportServiceImpl(ReportRepository repository, UserService userService) {
        this.repository = repository;
        this.userService = userService;
    }

    @Override
    public Report saveReport(Report report) {
        Report savedReport = repository.save(report);
        logger.info("Client's report has been saved successfully: " + savedReport);
        return savedReport;
    }

    @Override
    public String handlePhoto(Message message, Integer fileSize, String filePath)  {

     //   User user = userService.getUserByChatId(message.chat().id());
     //   logger.info("User with id {} sent a report ", user.getId());

        report.setUser(userService.getUserByChatId(message.chat().id()));
        logger.info("chat id {} : ", userService.getUserByChatId(message.chat().id()).getChatId());

        logger.info("User id of the report {} ", report.getUser().getId());
        logger.info("User chat id of the report {} ", report.getUser().getChatId());
        logger.info("User name of the report {} ", report.getUser().getName());

        report.setFilePath(filePath);
        report.setFileSize(fileSize);
        report.setSentDate(LocalDate.now());

        saveReport(report);
        String outputMessage = "Спасибо! Ваш отчет отправлен волонтеру на проверку.";
        return outputMessage;
    }

//    @Override
//    public Path uploadPhotoFile(File reportFile, User user) throws IOException {
//        Path filePath = Path.of(coverDir, user.getId() + "." + report.getSentDate() + "." + getExtension(reportFile.filePath()));
//        report.setFilePath(reportFile.toString());
//        logger.info("Uploading photo with path {} : ");
//        Files.createDirectories(filePath.getParent());
//        Files.deleteIfExists(filePath);
//
//        try (InputStream is = reportFile.getInputStream();
//             OutputStream os = Files.newOutputStream(filePath, CREATE_NEW);
//             BufferedInputStream bis = new BufferedInputStream(is, 1024);
//             BufferedOutputStream bos = new BufferedOutputStream(os, 1024)
//        ) {
//            bis.transferTo(bos);
//
//
//        return filePath;
//    }

//    @Override
//    public byte[] generatePhotoPreview(Path filePath) throws IOException {
//        try (InputStream is = Files.newInputStream(filePath);
//             BufferedInputStream bis = new BufferedInputStream(is, 1024);
//             ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
//            BufferedImage image = ImageIO.read(bis);
//
//            int height = image.getHeight() / (image.getWidth() / 100);
//            BufferedImage preview = new BufferedImage(100, height, image.getType());
//            Graphics2D graphics = preview.createGraphics();
//            graphics.drawImage(image, 0, 0, 100, height, null);
//            graphics.dispose();
//
//            ImageIO.write(preview, getExtension(filePath.getFileName().toString()), baos);
//            return baos.toByteArray();



    @Override
    public List<Report> findByUserId(long userId) {
        return repository.findByUserId(userId).orElse(null);
    }

    @Override
    public Report findById(long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public Report findLastReportByUserId(long userId) {
        return repository.findLastReportByUserId(userId).orElse(null);
    }

    @Override
    public LocalDate getDateOfLastReportByUserId(long userId) {
        return repository.getDateOfLastReportByUserId(userId).orElse(null);
    }

    @Override
    public boolean reportWasSentToday(LocalDate messageDate, long userId) {
        return (getDateOfLastReportByUserId(userId) != null &&
                getDateOfLastReportByUserId(userId).equals(messageDate));
    }

    @Override
    public Report saveTextReport(Message inputMessage) {
        report.setReportText(inputMessage.text());
        return report;
    }

//    private String getExtension(String fileName) {
//        return fileName.substring(fileName.lastIndexOf(".") + 1);
//    }
}
