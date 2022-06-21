package pro.sky.java.course7.animalshelter.serviceimpl;

import com.pengrad.telegrambot.model.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pro.sky.java.course7.animalshelter.model.Report;
import pro.sky.java.course7.animalshelter.repository.ReportRepository;
import pro.sky.java.course7.animalshelter.service.ReportService;
import pro.sky.java.course7.animalshelter.service.UserService;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.Properties;


@Service
@Transactional
public class ReportServiceImpl implements ReportService {

    private final Logger logger = LoggerFactory.getLogger(ReportServiceImpl.class);
    private final ReportRepository repository;
    private final UserService userService;

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
    public Report handlePhoto(Message message, Integer fileSize, String filePath, String reportText) {
        Report report = new Report();
        report.setClientId(userService.getUserByChatId(message.chat().id()).getId());
        report.setReportText(reportText);
        report.setFilePath(filePath);
        report.setFileSize(fileSize);
        report.setSentDate(LocalDate.now());
        try {
            report.setPreview(generatePhotoPreview(filePath));
        } catch (Exception e) {
            e.printStackTrace();
        }
        saveReport(report);
        return report;
    }

    @Override
    public File downloadFile(String filePath, Message message) {
        java.io.File file = null;
        try {
            Properties sysProps = System.getProperties();
            URL url = new URL(filePath);
            InputStream in = url.openStream();
            String directoryPath = sysProps.getProperty("file.separator")
                    + sysProps.getProperty("user.home") + sysProps.getProperty("file.separator") +
                    "Documents" + sysProps.getProperty("file.separator") + "dev";
            java.io.File directory = new java.io.File(directoryPath);

            String pathToFile = directoryPath + sysProps.getProperty("file.separator")
                    + userService.getUserByChatId(message.chat().id()).getId() + "."
                    + LocalDate.now() + "."
                    + filePath.substring(filePath.lastIndexOf("/") + 1);

            if (!directory.exists()) {
                directory.mkdirs();
            }
            file = new java.io.File(pathToFile);
            file.createNewFile();

            FileOutputStream os = new FileOutputStream(file);
            int read;
            byte[] bytes = new byte[10000];
            while ((read = in.read(bytes)) != -1) {
                os.write(bytes, 0, read);
            }
            os.flush();
            os.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return file;
    }

    @Override
    public byte[] generatePhotoPreview(String filePath) throws IOException {
        URL url = new URL(filePath);
        try (
                InputStream is = url.openStream();
                BufferedInputStream bis = new BufferedInputStream(is, 1024);
                ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            BufferedImage image = ImageIO.read(bis);

            int height = image.getHeight() / (image.getWidth() / 100);
            BufferedImage preview = new BufferedImage(100, height, image.getType());
            Graphics2D graphics = preview.createGraphics();
            graphics.drawImage(image, 0, 0, 100, height, null);
            graphics.dispose();

            ImageIO.write(preview, getExtension(filePath), baos);
            return baos.toByteArray();
        }
    }

    @Override
    public List<Report> getReportsByUserId(Long userId) {
        return repository.findByUserId(userId).orElse(null);
    }

    @Override
    public Report getById(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public Report findLastReportByUserId(Long userId) {
        return repository.findLastReportByUserId(userId).orElse(null);
    }

    @Override
    public LocalDate getDateOfLastReportByUserId(Long userId) {
        return repository.getDateOfLastReportByUserId(userId).orElse(null);
    }

    @Override
    public boolean reportWasSentToday(LocalDate messageDate, Long userId) {
        return (getDateOfLastReportByUserId(userId) != null &&
                getDateOfLastReportByUserId(userId).equals(messageDate));
    }

    @Override
    public Integer countUserReports(Long id) {
       return repository.countReportsByClientId(id).orElse(null);
    }

    private String getExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }

}
