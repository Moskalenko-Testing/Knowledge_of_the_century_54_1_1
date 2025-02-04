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
import view.WelcomeMenu;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class BookServiceTest {
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
    void addBook() throws CloneNotSupportedException {
        //For Role = Admin
        assertEquals(10, bookService.getAllBooks().size());
        bookService.addBook("TestBook", "TestAuthor", new Date());
        assertEquals(11, bookService.getAllBooks().size());
        Book testBook = bookService.deleteBook(11);
        assertEquals("TestBook", testBook.getTitle());
        assertEquals("TestAuthor", testBook.getAuthor());
        assertEquals(10, bookService.getAllBooks().size());
        assertEquals(null,bookService.deleteBook(100));
        //For Role == User
        userService.getActiveUser().setRole(Role.USER);
        bookService.addBook("TestBook2", "TestAuthor2", new Date());
        assertEquals(10, bookService.getAllBooks().size());
        assertEquals(null, bookService.deleteBook(1));

    }

    @Test
    void getById() {
    }

    @Test
    void searchBookByTitle() {
    }

    @Test
    void searchBookByAuthor() {
    }

    @Test
    void borrowBook() {
    }

    @Test
    void returnBook() {
    }

    @Test
    void getAvailableBooks() {
    }

    @Test
    void getBorrowedBooks() {
    }
}