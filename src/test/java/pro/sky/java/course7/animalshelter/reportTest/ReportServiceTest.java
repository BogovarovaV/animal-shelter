package pro.sky.java.course7.animalshelter.reportTest;

import org.apache.commons.collections4.CollectionUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pro.sky.java.course7.animalshelter.model.Report;
import pro.sky.java.course7.animalshelter.repository.ReportRepository;
import pro.sky.java.course7.animalshelter.serviceimpl.ReportServiceImpl;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static pro.sky.java.course7.animalshelter.DataTest.*;

@ExtendWith(MockitoExtension.class)
public class ReportServiceTest {

//    private Report report1;
//    private Report report2;
//
//    @Mock
//    private ReportRepository reportRepositoryMock;
//
//    @InjectMocks
//    private ReportServiceImpl out;
//
//    @BeforeEach
//    public void setUp() {
//        report1 = new Report(REPORT_TEXT_1, FILE_PATH_1, SENT_DATE_1);
//        report1.setUserChatId(USER_CHAT_ID_1);
//        report2 = new Report(REPORT_TEXT_1, FILE_PATH_2, SENT_DATE_2);
//        report2.setUserChatId(USER_CHAT_ID_2);
//        out = new ReportServiceImpl(reportRepositoryMock);
//    }
//
//    @Test
//    public void getReportsByUserChatIdTest() {
//        when(reportRepositoryMock.findByUserChatId(any(Long.class))).thenReturn(
//                (List.of(report1, report2)));
//        assertTrue(CollectionUtils.isEqualCollection(List.of(report1, report2), out.getReportsByUserChatId(USER_CHAT_ID_1)));
//    }
//
//    @Test
//    public void saveReportTest() {
//        when(reportRepositoryMock.save(report1)).thenReturn(report1);
//        assertEquals(report1, out.saveReport(report1, USER_CHAT_ID_1));
//        verify(reportRepositoryMock, times(1)).save(report1);
//    }


}
