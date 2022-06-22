package pro.sky.java.course7.animalshelter.reportTest;

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

import java.io.*;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
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
    public void testShouldSaveReport() {
        when(reportRepositoryMock.save(report1)).thenReturn(report1);
        assertEquals(report1, out.saveReport(report1));
        verify(reportRepositoryMock, times(1)).save(report1);
    }

    @Test
    public void testShouldThrowNullPointerExceptionAfterHandlePhoto()  {
        assertThrows(NullPointerException.class,
                () -> out.handlePhoto(REPORT_MESSAGE,FILE_SIZE_1,FILE_PATH_1, REPORT_TEXT_1));
    }

    @Test
    public void testShouldThrowExceptionWhileDownloadFile() {
        User user = new User();
        user.setId(USER_ID_1);
        user.setChatId(USER_CHAT_ID_1);
        when(userServiceMock.getUserByChatId(USER_CHAT_ID_1)).thenReturn(user);
        when(userServiceMock.getUserByChatId(REPORT_MESSAGE.chat().id()).getId()).thenReturn(USER_ID_1);
        assertThrows(NullPointerException.class,
                () -> out.downloadFile(FILE_PATH_2, REPORT_MESSAGE));
    }

    @Test
    public void testShouldThrowIOExceptionGeneratePhotoPreview() {
        assertThrows(IOException.class,
                () -> out.generatePhotoPreview(FILE_PATH_2));
    }

    @Test
    public void testShouldGetReportsByUserId() {
        List<Report> reports = List.of(report1,report2);
        when(reportRepositoryMock.findByUserId(any(Long.class))).
                thenReturn(Optional.of(Optional.of(reports).orElse(null)));
        assertEquals(reports, out.getReportsByUserId(USER_ID_1));
    }

    @Test
    public void testShouldGetById() {
        report1.setId(REPORT_ID);
        when(reportRepositoryMock.findById(any(Long.class))).
                thenReturn(Optional.of(Optional.of(report1).orElse(null)));
        assertEquals(report1, out.getById(REPORT_ID));

    }

    @Test
    public void testShouldGetLastReportByUserId() {
        when(reportRepositoryMock.findLastReportByUserId(USER_ID_1)).thenReturn(Optional.ofNullable(report1));
        assertEquals(report1, out.getLastReportByUserId(USER_ID_1));
    }

    @Test
    public void testShouldGetDateOfLastReportByUserId() {
        when(reportRepositoryMock.findDateOfLastReportByUserId(USER_ID_1)).thenReturn(Optional.of(SENT_DATE_1));
        assertEquals(SENT_DATE_1, out.getDateOfLastReportByUserId(USER_ID_1));
    }

    @Test
    public void testShouldCheckIfReportWasSentToday() {
        when(reportRepositoryMock.findDateOfLastReportByUserId(USER_ID_1)).thenReturn(Optional.of(SENT_DATE_1));
        assertTrue(out.reportWasSentToday(SENT_DATE_1, USER_ID_1));

    }

    @Test
    public void testShouldCountUserReports() {
        when(reportRepositoryMock.countReportsByClientId(USER_ID_1)).thenReturn(Optional.of(1));
        assertEquals(1, out.countUserReports(USER_ID_1));
    }


}
