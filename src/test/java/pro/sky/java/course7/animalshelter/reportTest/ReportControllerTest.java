package pro.sky.java.course7.animalshelter.reportTest;


import com.pengrad.telegrambot.model.File;
import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import pro.sky.java.course7.animalshelter.controller.ReportController;
import pro.sky.java.course7.animalshelter.model.Animal;
import pro.sky.java.course7.animalshelter.model.Report;
import pro.sky.java.course7.animalshelter.model.User;
import pro.sky.java.course7.animalshelter.repository.AnimalRepository;
import pro.sky.java.course7.animalshelter.repository.ReportRepository;
import pro.sky.java.course7.animalshelter.repository.UserRepository;
import pro.sky.java.course7.animalshelter.serviceimpl.AnimalServiceImpl;
import pro.sky.java.course7.animalshelter.serviceimpl.ReportServiceImpl;
import pro.sky.java.course7.animalshelter.serviceimpl.UserServiceImpl;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static pro.sky.java.course7.animalshelter.DataTest.*;


@WebMvcTest(controllers = ReportController.class)
public class ReportControllerTest {

    private Report report1;
    private User user1;
    private JSONObject reportObject1;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReportRepository reportRepository;
    @MockBean
    private UserRepository userRepository;
    @MockBean
    private AnimalRepository animalRepository;

    @SpyBean
    private ReportServiceImpl reportService;
    @SpyBean
    private UserServiceImpl userService;
    @SpyBean
    private AnimalServiceImpl animalService;

    @InjectMocks
    private ReportController reportController;

    @BeforeEach
    public void setUp() throws Exception {
        reportObject1 = new JSONObject();
        reportObject1.put("id", REPORT_ID);
        reportObject1.put("userId", USER_ID_1);
        reportObject1.put("reportText", REPORT_TEXT_1);
        reportObject1.put("filePath", FILE_PATH_1);
        reportObject1.put("fileSize", FILE_SIZE_1);
        reportObject1.put("preview", PREVIEW_1.toString());
        reportObject1.put("sentDate", SENT_DATE_1.toString());
        reportObject1.put("status", REPORT_STATUS_1.toString());

        Animal animal = new Animal(ANIMAL_ID, TYPE);

        user1 = new User(USER_NAME_1, USER_PHONE_1, USER_EMAIL_1);
        user1.setId(USER_ID_1);
        user1.setChatId(USER_CHAT_ID_1);
        user1.setStatus(User.UserStatus.ADOPTER_ON_TRIAL);
        user1.setAnimal(animal);

        report1 = new Report(REPORT_ID, USER_ID_1, REPORT_TEXT_1, FILE_PATH_1, FILE_SIZE_1, PREVIEW_1, SENT_DATE_1, REPORT_STATUS_1);
    }

    @Test
    public void getReportPreviewTest() throws Exception {

        when(reportRepository.findById(any(Long.class))).thenReturn(Optional.of(report1));
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/report/1/preview")
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(content().contentType(MediaType.IMAGE_JPEG_VALUE));
        verify(reportRepository, times(1)).findById(REPORT_ID);
    }

    @Test
    public void shouldThrowExceptionIfGetReportImageIsNotSuccess() {
        HttpServletResponse response = mock(HttpServletResponse.class);
        when(reportRepository.findById(any(Long.class))).thenReturn(null);
        Assertions.assertThrows(NullPointerException.class, () -> reportController.getReportImage(REPORT_ID, response));
    }

    @Test
    public void getReportTextTest() throws Exception {
        when(reportRepository.findById(any(Long.class))).thenReturn(Optional.of(report1));
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/report/1")
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(REPORT_ID))
                .andExpect(jsonPath("$.clientId").value(USER_ID_1))
                .andExpect(jsonPath("$.reportText").value(REPORT_TEXT_1))
                .andExpect(jsonPath("$.filePath").value(FILE_PATH_1))
                .andExpect(jsonPath("$.sentDate").value(SENT_DATE_1.toString()))
                .andExpect(jsonPath("$.status").value(REPORT_STATUS_1.toString()));
    }

    @Test
    public void getAllUserReports() throws Exception {

        when(reportRepository.findByUserId(any(Long.class))).thenReturn(Optional.of(List.of(report1)));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/report/getAll/1")
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id").value(REPORT_ID))
                .andExpect(jsonPath("$[0].clientId").value(USER_ID_1))
                .andExpect(jsonPath("$[0].reportText").value(REPORT_TEXT_1))
                .andExpect(jsonPath("$[0].filePath").value(FILE_PATH_1))
                .andExpect(jsonPath("$[0].fileSize").value(FILE_SIZE_1))
                .andExpect(jsonPath("$[0].sentDate").value(SENT_DATE_1.toString()))
                .andExpect(jsonPath("$[0].status").value(REPORT_STATUS_1.toString()));
    }
}
