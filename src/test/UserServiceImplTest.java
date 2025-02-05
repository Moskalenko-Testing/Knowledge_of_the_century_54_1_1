package test;

import model.Role;
import model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.BookRepository;
import repository.BookRepositoryImpl;
import repository.UserRepository;
import repository.UserRepositoryImpl;
import service.BookService;
import service.BookServiceImpl;
import service.UserService;
import service.UserServiceImpl;

import java.io.IOException;
import java.text.ParseException;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceImplTest {
    BookRepository bookRepository;
    UserRepository userRepository;
    UserService userService;
    BookService bookService;

    @BeforeEach
    void setUp() throws IOException, ParseException {
        bookRepository = new BookRepositoryImpl();
        userRepository = new UserRepositoryImpl();
        userService = new UserServiceImpl(userRepository, bookRepository);
        bookService = new BookServiceImpl(bookRepository, userService);
        User testUser = userService.registerUser("super@pupper.com", "Super12345+");
        testUser.setRole(Role.ADMIN);
        userService.setActiveUser(testUser);

    }

    @Test
    void registerUser() {
        User user = userService.registerUser("super@pupper.com", "Super12345+");
        assertEquals(null, user);
        String testEmail = "santa@barbara.com";
        User user1 = userService.registerUser(testEmail, "Super12345+");
        assertEquals(user1.getEmail(),testEmail);
    }

    @Test
    void loginUser() {
    }

    @Test
    void updatePassword() {
    }

    @Test
    void getUserByEmail() {
    }

    @Test
    void getActiveUser() {
        assertEquals("super@pupper.com", userService.getActiveUser().getEmail());
    }

    @Test
    void userBooks() {
    }

    @Test
    void deleteUser() {
    }

    @Test
    void blockedUser() {
    }

    @Test
    void allAllUsers() {
    }

    @Test
    void setActiveUser() {
        User testUserNext = userService.registerUser("super@pupperpupper.com", "Super12345+");
        testUserNext.setRole(Role.ADMIN);
        userService.setActiveUser(testUserNext);
        String testEmail = userService.getUserByEmail("super@pupper.com").getEmail();
        assertNotEquals(userService.getActiveUser().getEmail(), testEmail);

    }
}