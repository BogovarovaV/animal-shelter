package pro.sky.java.course7.animalshelter.userServiceTests;

import org.apache.commons.collections4.CollectionUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pro.sky.java.course7.animalshelter.model.User;
import pro.sky.java.course7.animalshelter.repository.UserRepository;
import pro.sky.java.course7.animalshelter.service.UserServiceImpl;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static pro.sky.java.course7.animalshelter.DataTest.*;


@ExtendWith(MockitoExtension.class)
public class UserServiceMockTest {

    private User user1;
    private User user2;

    @Mock
    private UserRepository userRepositoryMock;

    @InjectMocks
    private UserServiceImpl out;

    @BeforeEach
    public void setUp() {
        user1 = new User(USER_NAME_1, USER_PHONE_1, USER_EMAIL_1);
        user2 = new User(USER_NAME_2, USER_PHONE_2, USER_EMAIL_2);
        out = new UserServiceImpl(userRepositoryMock);
    }

    @Test
    public void saveTest() {
        when(userRepositoryMock.save(user1)).thenReturn(user1);
        assertEquals(user1, out.save(user1, USER_CHAT_ID_1));
        verify(userRepositoryMock, times(1)).save(user1);
    }

    @Test
    public void parseTest() {
        assertTrue((out.parse(USER_MESSAGE_1)).isPresent());
        assertEquals(Optional.of(user1), out.parse(USER_MESSAGE_1));
    }

    @Test
    public void getUserByChatIdTest() {
        when(userRepositoryMock.findUserByChatId(USER_CHAT_ID_1)).thenReturn(user1);
        assertEquals(user1, out.getUserByChatId(USER_CHAT_ID_1));
    }

    @Test
    public void getAllTest() {
        when(userRepositoryMock.findAll()).thenReturn(
                (List.of(user1, user2)));
        assertTrue(CollectionUtils.isEqualCollection(List.of(user1, user2), out.getAllUsers()));
    }

    @Test
    public void deleteUserByChatIdTest() {
        when(userRepositoryMock.findUserByChatId(USER_CHAT_ID_2)).thenReturn(user2);
        assertDoesNotThrow(() -> out.deleteUserByChatId(USER_CHAT_ID_2));
    }
}
