package pro.sky.java.course7.animalshelter.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pro.sky.java.course7.animalshelter.model.Report;
import pro.sky.java.course7.animalshelter.service.ReportService;

import java.util.List;

@RestController
@RequestMapping("/report")
public class ReportController {

//    private final ReportService reportService;
//
//    public ReportController(ReportService reportService) {
//        this.reportService = reportService;
//    }
//
//    @GetMapping("/{userChatId}")
//    public ResponseEntity getReportByUserChatId(@PathVariable Long userChatId) {
//        List<Report> report = reportService.getReportsByUserChatId(userChatId);
//        if (report == null) {
//            return ResponseEntity.notFound().build();
//        }
//        return ResponseEntity.ok(report);
//    }

}
