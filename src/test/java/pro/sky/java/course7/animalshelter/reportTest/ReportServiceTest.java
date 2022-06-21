package pro.sky.java.course7.animalshelter.reportTest;

import org.apache.commons.collections4.CollectionUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pro.sky.java.course7.animalshelter.model.Report;
import pro.sky.java.course7.animalshelter.model.User;
import pro.sky.java.course7.animalshelter.repository.ReportRepository;
import pro.sky.java.course7.animalshelter.service.UserService;
import pro.sky.java.course7.animalshelter.serviceimpl.ReportServiceImpl;
//import pro.sky.java.course7.animalshelter.model.Report;
//import pro.sky.java.course7.animalshelter.repository.ReportRepository;
//import pro.sky.java.course7.animalshelter.serviceimpl.ReportServiceImpl;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static pro.sky.java.course7.animalshelter.DataTest.*;

@ExtendWith(MockitoExtension.class)
public class ReportServiceTest {

    private Report report1;
    private Report report2;

    @Mock
    private ReportRepository reportRepositoryMock;

    @Mock
    private UserService userServiceMock;

    @InjectMocks
    private ReportServiceImpl out;

    @BeforeEach
    public void setUp() {
        report1 = new Report();
        report2 = new Report();
        report1.setClientId(USER_ID_1);
        report2.setClientId(USER_ID_1);
        out = new ReportServiceImpl(reportRepositoryMock, userServiceMock);
    }

    @Test
    public void saveReportTest() {
        when(reportRepositoryMock.save(report1)).thenReturn(report1);
        assertEquals(report1, out.saveReport(report1));
        verify(reportRepositoryMock, times(1)).save(report1);
    }

//    @Test
//    public void handlePhotoTest() throws IOException {
//        report1.setClientId(USER_ID_1);
//        report1.setReportText(REPORT_TEXT_1);
//        report1.setFilePath(FILE_PATH_1);
//        report1.setFileSize(FILE_SIZE_1);
//        report1.setSentDate(LocalDate.now());
//        report1.setPreview(out.generatePhotoPreview(FILE_PATH_1));
//    }

    @Test
    public void downloadFileTest() {
    }

    @Test
    public void generatePhotoPreviewTest() {
    }

    @Test
    public void getReportsByUserIdTest() {
        List<Report> reports = List.of(report1,report2);
        when(reportRepositoryMock.findByUserId(any(Long.class))).
                thenReturn(Optional.of(Optional.of(reports).orElse(null)));
        assertEquals(reports, out.getReportsByUserId(USER_ID_1));
    }

    @Test
    public void getByIdTest() {
        report1.setId(REPORT_ID);
        when(reportRepositoryMock.findById(any(Long.class))).
                thenReturn(Optional.of(Optional.of(report1).orElse(null)));
        assertEquals(report1, out.getById(REPORT_ID));

    }

    @Test
    public void findLastReportByUserIdTest() {
    }

    @Test
    public void getDateOfLastReportByUserIdTest() {
    }

    @Test
    public void reportWasSentTodayTest() {
    }

    @Test
    public void countUserReportsTest() {
    }


}
