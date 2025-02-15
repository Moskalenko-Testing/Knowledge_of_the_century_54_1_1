package test;

import model.Book;
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
import utils.MyList;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

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
        User testUser = userService.registerUser("super@pupperrr.com", "Super12345+");
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
        User loggedInUser = userService.getUserByEmail("super@pupper.com");
        assertNotNull(loggedInUser);
        assertEquals("super@pupper.com", loggedInUser.getEmail());
        // Проверяем, что вход с неверным паролем НЕ проходит
        assertFalse(userService.loginUser("super@pupper.com", "WrongPassword"));
//        // Проверяем, что вход с правильным паролем работает
//        String newEmail = "super@pupperrr.com";
//        assertTrue(userService.loginUser(newEmail, "Super12345+"));
//        assertEquals(newEmail, userService.getActiveUser().getEmail());

    }

    @Test
    void updatePassword() {
        assertTrue(userService.updatePassword("super@pupper.com", "NewPass123+"));
        assertFalse(userService.loginUser("super@pupper.com", "Super12345+"));
        assertNotNull(userService.loginUser("super@pupper.com", "NewPass123+"));

    }

    @Test
    void getUserByEmail() {
        User user = userService.getUserByEmail("super@pupper.com");
        assertNotNull(user);
        assertEquals("super@pupper.com", user.getEmail());
        assertNull(userService.getUserByEmail("nonexistent@email.com"));

    }

    @Test
    void getActiveUser() {
        assertEquals("super@pupperrr.com", userService.getActiveUser().getEmail());
    }

    @Test
    void userBooks() throws CloneNotSupportedException, IOException, ParseException {


        boolean checkBook =  bookService.addBook("Test Book", "Author", new Date());
        assertTrue(checkBook);
        assertEquals(0,userService.getActiveUser().getUserBooks().size());
        MyList<Book> books = bookService.searchBookByTitle("Test Book");
        int testBookForReturnTest1 = bookService.getAllBooks().size();
        Book testBook = books.get(0);// TODO если есть время проверить
        bookService.borrowBook(testBook.getId());
        assertEquals(1,userService.getActiveUser().getUserBooks().size());
        bookService.returnBook(testBook.getId());
        assertEquals(0,userService.getActiveUser().getUserBooks().size());


        }

    @Test
    void deleteUser() {
        assertTrue(userService.deleteUser("super@pupper.com"));
        assertNull(userService.getUserByEmail("super@pupper.com"));
        assertFalse(userService.deleteUser("nonexistent@email.com"));

    }

    @Test
    void blockedUser() {
        assertTrue(userService.blockedUser("super@pupper.com"));
        assertTrue(userService.loginUser("super@pupper.com", "Super12345+"));
        assertEquals(userService.getActiveUser().getRole(), Role.BLOCKED);

    }

    @Test
    void allAllUsers() {
         MyList<User> users = userService.allAllUsers();
         int userSize = users.size();
         User user = userService.registerUser("super@papper.com", "Super12345+");
         assertEquals(userSize + 1, users.size());


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