package pro.sky.java.course7.animalshelter.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pro.sky.java.course7.animalshelter.model.Report;
import pro.sky.java.course7.animalshelter.service.ReportService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.List;

@RestController
@RequestMapping("/report")
public class ReportController {

    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping(value = "/{id}/preview")
    public ResponseEntity getReportPreview(@PathVariable Long id) {
        Report report = reportService.getById(id);
        if (report == null) {
            return ResponseEntity.notFound().build();
        } else {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_JPEG);
            headers.setContentLength(report.getPreview().length);
            return ResponseEntity.status(HttpStatus.OK).headers(headers).body(report.getPreview());
        }
    }

    @GetMapping(value = "/{id}/image")
    public void getReportImage(@PathVariable Long id, HttpServletResponse response) throws IOException {
        Report report = reportService.getById(id);
        URL url = new URL(report.getFilePath());
        try (InputStream is = url.openStream();
             OutputStream os = response.getOutputStream()) {
            response.setContentType(MediaType.IMAGE_JPEG_VALUE);
            response.setContentLength(report.getFileSize());
            is.transferTo(os);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Report> getReportText(@PathVariable Long id) {
        Report report = reportService.getById(id);
        if (report == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(report);
    }

    @GetMapping("/getAll/{userId}")
    public ResponseEntity<List<Report>> getAllUserReports(@PathVariable Long userId) {
        List<Report> reportsList = reportService.getReportsByUserId(userId);
        if (reportsList.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(reportsList);
    }
}

