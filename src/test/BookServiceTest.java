package test;

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
    }

    @Test
    void addBook() throws CloneNotSupportedException {
        assertEquals(0, bookService.getAllBooks().size());
        bookService.addBook("TestBook", "TestAuthor", new Date());
        assertEquals(1, bookService.getAllBooks().size());

        
    }

    @Test
    void getAllBooks() {

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