package pro.sky.java.course7.animalshelter.userTest;

import org.apache.commons.collections4.CollectionUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pro.sky.java.course7.animalshelter.model.Animal;
import pro.sky.java.course7.animalshelter.model.Report;
import pro.sky.java.course7.animalshelter.model.User;
import pro.sky.java.course7.animalshelter.repository.UserRepository;
import pro.sky.java.course7.animalshelter.serviceimpl.AnimalServiceImpl;
import pro.sky.java.course7.animalshelter.serviceimpl.UserServiceImpl;


import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static pro.sky.java.course7.animalshelter.DataTest.*;


@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    private User user1;
    private User user2;
    private Animal animal;

    @Mock
    private UserRepository userRepositoryMock;

    @Mock
    private AnimalServiceImpl animalServiceMock;

    @InjectMocks
    private UserServiceImpl out;

    @BeforeEach
    public void setUp() {
        user1 = new User(USER_NAME_1, USER_PHONE_1, USER_EMAIL_1);
        user2 = new User(USER_NAME_2, USER_PHONE_2, USER_EMAIL_2);
        animal = new Animal(ANIMAL_ID, TYPE);
        out = new UserServiceImpl(userRepositoryMock, animalServiceMock);
    }

    @Test
    public void saveTest() {
        when(userRepositoryMock.save(user1)).thenReturn(user1);
        assertEquals(user1, out.save(user1));
        verify(userRepositoryMock, times(1)).save(user1);
    }

    @Test
    public void editTest() {
        user2.setId(USER_ID_2);
        user2.setChatId(USER_CHAT_ID_2);
        user2.setStatus(USER_STATUS_2);
        user2.setAnimal(animal);
        when(userRepositoryMock.save(user2)).thenReturn(user2);
        assertEquals(user2, out.edit(USER_ID_2, USER_CHAT_ID_2, user2, USER_STATUS_2, TYPE));
    }

    @Test
    public void createUserByVolunteerTest() {
        user1.setAnimal(animal);
        when(userRepositoryMock.save(user1)).thenReturn(user1);
        assertEquals(user1, out.createUserByVolunteer(user1, TYPE));
    }

//    @Test
//    public void parseTest() {
//        assertTrue((out.parse(USER_MESSAGE_1, USER_CHAT_ID_1)).isPresent());
//        assertEquals(Optional.of(user1), out.parse(USER_MESSAGE_1, USER_CHAT_ID_1));
//    }


    @Test
    public void registrationUserTest() {
        user1.setId(USER_ID_1);
        user1.setChatId(USER_CHAT_ID_1);
        user1.setStatus(USER_STATUS_1);
        user1.setAnimal(animal);
        Optional<User> parseResult = out.parse(USER_MESSAGE_1, USER_CHAT_ID_1);
        when(userRepositoryMock.save(parseResult.get())).thenReturn(user1);
        assertEquals(user1, out.edit(USER_ID_1, USER_CHAT_ID_1, parseResult.get(), USER_STATUS_1, TYPE));
    }

    @Test
    public void getUserByIdTest() {
        when(userRepositoryMock.findById(USER_ID_1)).thenReturn(Optional.ofNullable(user1));
        assertEquals(user1, out.getUserById(USER_ID_1));
    }

    @Test
    public void getUserByChatIdTest() {
        when(userRepositoryMock.findUserByChatId(USER_CHAT_ID_1)).thenReturn(user1);
        assertEquals(user1, out.getUserByChatId(USER_CHAT_ID_1));
    }

    @Test
    public void deleteUserByIdTest() {
        doNothing().when(userRepositoryMock).deleteById(USER_CHAT_ID_2);
        assertDoesNotThrow(() -> out.deleteUserById(USER_CHAT_ID_2));
    }

    @Test
    public void getAllTest() {
        when(userRepositoryMock.findAll()).thenReturn(
                (List.of(user1, user2)));
        assertTrue(CollectionUtils.isEqualCollection(List.of(user1, user2), out.getAllUsers()));
    }

    @Test
    public void adopterOnTrialExistTest() {
        user2.setChatId(USER_CHAT_ID_2);
        user2.setStatus(USER_STATUS_3);
        when(userRepositoryMock.findUserByChatId(USER_CHAT_ID_2)).thenReturn(user2);
        assertTrue(out.adopterOnTrialExist(USER_CHAT_ID_2));

    }

    @Test
    public void getAdoptersWithEndOfTrialTest() {
        List<User> users = List.of(user1, user2);
        when(userRepositoryMock.findAdoptersWithEndOfTrial(USER_STATUS_3, TEST_TRIAL_DATE)).thenReturn(users);
        assertEquals(users, out.getAdoptersWithEndOfTrial(USER_STATUS_3, TEST_TRIAL_DATE));
    }

    @Test
    public void getAdoptersByReportStatusAndSentDateTest() {
        List<User> users = List.of(user1, user2);
        when(userRepositoryMock.findAdoptersByReportStatusAndSentDate(Report.ReportStatus.SENT, SENT_DATE)).thenReturn(users);
        assertEquals(users, out.getAdoptersByReportStatusAndSentDate(Report.ReportStatus.SENT, SENT_DATE));

    }

    @Test
    public void getAdoptersByStatusAndReportDateTest() {
        List<User> users = List.of(user1, user2);
        when(userRepositoryMock.findAdoptersByStatusAndReportDate(USER_STATUS_1, SENT_DATE)).thenReturn(users);
        assertEquals(users, out.getAdoptersByStatusAndReportDate(USER_STATUS_1, SENT_DATE));
    }

    @Test
    public void getAdoptersByStatusAndExtendedTrialTest() {
        user1.setStartTrialDate(START_TRIAL_DATE);
        user2.setStartTrialDate(START_TRIAL_DATE);
        user1.setStatus(USER_STATUS_3);
        user2.setStatus(USER_STATUS_3);
        List<User> users = List.of(user1, user2);
        when(userRepositoryMock.findAllAdopters(USER_STATUS_3)).thenReturn(users);
        assertEquals(users, out.getAdoptersByStatusAndExtendedTrial(USER_STATUS_3));
    }

}
