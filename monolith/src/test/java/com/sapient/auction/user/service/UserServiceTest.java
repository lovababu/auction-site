package com.sapient.auction.user.service;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.spi.LoggingEvent;
import ch.qos.logback.core.Appender;
import com.sapient.auction.user.entity.User;
import com.sapient.auction.user.exception.UserAlreadyExistException;
import com.sapient.auction.user.exception.UserNotFoundException;
import com.sapient.auction.user.repository.UserRepository;
import com.sapient.auction.user.service.impl.UserServiceImpl;
import com.sapient.auction.user.util.PasswordUtil;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.slf4j.LoggerFactory;

import java.security.NoSuchAlgorithmException;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

/**
 * Unit tests for the UserService.java to test the business, by Mock the UserRepository.
 * <p>
 * Created by dpadal on 11/18/2016.
 */
@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {


    @InjectMocks
    private UserService userService = new UserServiceImpl();

    @Mock
    private UserRepository userRepository;

    @Mock
    private Appender mockAppender;

    @Captor
    private ArgumentCaptor<LoggingEvent> captorLoggingEvent;

    @Before
    public void before() {
        MockitoAnnotations.initMocks(this);

        ch.qos.logback.classic.Logger logger =
                (ch.qos.logback.classic.Logger) LoggerFactory.getLogger(ch.qos.logback.classic.Logger.ROOT_LOGGER_NAME);
        when(mockAppender.getName()).thenReturn("MOCK");
        logger.addAppender(mockAppender);

        Mockito.when(userRepository.register(any(User.class))).thenReturn(fakeUser("dpadala@sapient.com"));

        Mockito.when(userRepository.isUserAlreadyExist(anyString())).thenAnswer(invocationOnMock -> {
            String userId = invocationOnMock.getArgumentAt(0, String.class);
            if (userId.contains("Exist")) {
                return true;
            } else {
                return false;
            }
        });

        Mockito.when(userRepository.login(anyString(), anyString())).thenAnswer(invocationOnMock -> {
            String userId = invocationOnMock.getArgumentAt(0, String.class);

            if (userId.contains("NotExist")) {
                return null;
            } else {
                return new User();
            }
        });
    }

    @Test
    public void testRegister() throws UserAlreadyExistException, UserNotFoundException, NoSuchAlgorithmException {
        User fakeUser = fakeUser("dpadala@sapient.com");
        userService.register(fakeUser);

        Mockito.verify(userRepository, times(1)).isUserAlreadyExist(fakeUser.getEmail());
        Mockito.verify(userRepository, times(1)).register(fakeUser);
        assertEquals(PasswordUtil.hash("123456789"), fakeUser.getPassword());

        User user = userService.login(fakeUser);
        assertNotNull(user);
        Mockito.verify(userRepository, times(1)).login(fakeUser.getEmail(), fakeUser.getPassword());

        Mockito.verify(mockAppender, times(2)).doAppend(captorLoggingEvent.capture());
        final List<LoggingEvent> loggingEvents = captorLoggingEvent.getAllValues();

        //Check log level is correct
        assertEquals(loggingEvents.get(0).getLevel(), Level.INFO);
        //Check the message being logged is correct
        assertEquals(loggingEvents.get(0).getFormattedMessage(),
                String.format("User %s stored in db.", fakeUser.getEmail()));

        //Check log level is correct
        assertEquals(loggingEvents.get(1).getLevel(), Level.INFO);
        //Check the message being logged is correct
        assertEquals(loggingEvents.get(1).getFormattedMessage(),
                String.format("Looking up for the user with email: %s, pwd: %s", fakeUser.getEmail(),
                        fakeUser.getPassword()));


    }

    @Test(expected = UserAlreadyExistException.class)
    public void testRegisterFailed() throws UserAlreadyExistException, NoSuchAlgorithmException {
        User fakeUser = fakeUser("dpadalaExist@sapient.com");
        userService.register(fakeUser);
        Mockito.verify(userRepository, times(1)).isUserAlreadyExist(fakeUser.getEmail());
    }

    @Test
    public void testLogin() throws UserNotFoundException {
        User fakeUser = fakeUser("dpadala@sapient.com");
        User user = userService.login(fakeUser);
        assertNotNull(user);
        Mockito.verify(userRepository, times(1)).login(fakeUser.getEmail(), fakeUser.getPassword());
        Mockito.verify(mockAppender, times(1)).doAppend(captorLoggingEvent.capture());
        final LoggingEvent loggingEvents = captorLoggingEvent.getValue();

        //Check log level is correct
        assertEquals(loggingEvents.getLevel(), Level.INFO);
        //Check the message being logged is correct
        assertEquals(loggingEvents.getFormattedMessage(),
                String.format("Looking up for the user with email: %s, pwd: %s", fakeUser.getEmail(),
                        fakeUser.getPassword()));

    }

    @Test(expected = UserNotFoundException.class)
    public void testLoginFailed() throws UserNotFoundException {
        User fakeUser = fakeUser("dpadalNotExist@sapient.com");
        User user = userService.login(fakeUser);
        assertNull(user);
        Mockito.verify(userRepository, times(1)).login(fakeUser.getEmail(), fakeUser.getPassword());

        Mockito.verify(mockAppender, times(1)).doAppend(captorLoggingEvent.capture());
        final LoggingEvent loggingEvents = captorLoggingEvent.getValue();

        //Check log level is correct
        assertEquals(loggingEvents.getLevel(), Level.INFO);
        //Check the message being logged is correct
        assertEquals(loggingEvents.getFormattedMessage(),
                String.format("Looking up for the user with email: %s, pwd: %s", fakeUser.getEmail(),
                        fakeUser.getPassword()));
    }


    private User fakeUser(String email) {
        User user = new User();
        user.setPassword("123456789");
        user.setFirstName("Durga");
        user.setLastName("Lovababu");
        user.setContact("8123717649");
        user.setEmail(email);
        user.setAddress("Bangalore");

        return user;
    }


    @After
    public void tearDown() {
        userService = null;
        userRepository = null;
    }
}
