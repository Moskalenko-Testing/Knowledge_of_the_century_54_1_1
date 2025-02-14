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
import view.WelcomeMenu;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
        User testUser = userService.registerUser("super@pupperrrr.com", "Super12345+");
        testUser.setRole(Role.ADMIN);
        userService.setActiveUser(testUser);
    }

    @Test
    void addBook() throws CloneNotSupportedException, IOException, ParseException {
        //        //For Role = Admin
        int test1 = bookService.getAllBooks().size();
        bookService.addBook("TestBook", "TestAuthor", new Date());
        assertEquals(test1 + 1, bookService.getAllBooks().size());
        Book testBook = bookService.deleteBook(test1 + 1);
        assertEquals("TestBook", testBook.getTitle());
        assertEquals("TestAuthor", testBook.getAuthor());
        assertEquals(test1, bookService.getAllBooks().size());
        assertNotEquals(test1 + 1, bookService.getAllBooks().size());
        //For Role == User
        userService.getActiveUser().setRole(Role.USER);
        bookService.addBook("TestBook2", "TestAuthor2", new Date());
        assertEquals(test1, bookService.getAllBooks().size());
        assertNotEquals(test1 + 1, bookService.deleteBook(test1 + 1));

    }

    @Test
    void getById() throws CloneNotSupportedException {
        Book book1 = bookService.getById(1);
        Book book2 = bookService.getById(1);
        assertEquals(book1, book2);
        //Проверка на запрет данного метода для Роли USER
        userService.getActiveUser().setRole(Role.USER);
        Book book3 = bookService.getById(1);
        assertEquals(book1, book3);
    }


    @Test
    void searchBookByTitle() throws CloneNotSupportedException, ParseException, IOException {
        String title = "TestBook";
        bookService.addBook(title, "TestAuthor", new Date());
        bookService.addBook(title, "TestAuthor2", new Date());
        Date releaseDate = new SimpleDateFormat("yyyy").parse("1234");
        bookService.addBook(title, "TestAuthor", releaseDate);
        MyList<Book> books = bookService.searchBookByTitle(title);

        assertEquals(3, books.size());
        assertEquals(13, bookService.getAllBooks().size());
        //Test with ActiveUser.setRole == ROLE.User
        bookService.addBook(title, "TestAuthor", new Date());
        bookService.addBook(title, "TestAuthor2", new Date());
        Date releaseDateUser = new SimpleDateFormat("yyyy").parse("2023");
        bookService.addBook(title, "TestAuthor", releaseDateUser);
        MyList<Book> booksUser = bookService.searchBookByTitle(title);
        assertEquals(6, booksUser.size());
        assertEquals(16, bookService.getAllBooks().size());

    }

    @Test
    void searchBookByAuthor() throws CloneNotSupportedException, ParseException, IOException {
        String author = "TestAuthor";
        bookService.addBook("TestBook",author, new Date());
        bookService.addBook("TestBook2", author, new Date());
        Date releaseDate = new SimpleDateFormat("yyyy").parse("1009");
        bookService.addBook("TestBook", author, releaseDate);
        MyList<Book> books = bookService.searchBookByAuthor(author);
        assertEquals(3, books.size());
        assertEquals(13, bookService.getAllBooks().size());
        //Test with ActiveUser.setRole == ROLE.User
        bookService.addBook("TestBook", author, new Date());
        bookService.addBook("TestBook2", author, new Date());
        Date releaseDateUser = new SimpleDateFormat("yyyy").parse("2025");
        bookService.addBook("TestBook", author, releaseDateUser);
        MyList<Book> booksUser = bookService.searchBookByAuthor(author);
        assertEquals(6, booksUser.size());
        assertEquals(16, bookService.getAllBooks().size());

    }

    @Test
    void borrowBook() throws CloneNotSupportedException, IOException, ParseException {
        int borrowedTestArraySize = bookService.getBorrowedBooks().size();
        bookService.addBook("TestBook", "TestAuthor", new Date());
        assertEquals(borrowedTestArraySize, bookService.getBorrowedBooks().size());

        Book testBook = bookService.borrowBook(bookService.getAllBooks().size());
        assertEquals("TestBook", testBook.getTitle());
        assertEquals("TestAuthor", testBook.getAuthor());
        assertEquals(borrowedTestArraySize + 1, bookService.getBorrowedBooks().size());
        assertNotEquals(bookService.getBorrowedBooks().size(),bookService.getAvailableBooks());



    }

    @Test
    void returnBook() throws CloneNotSupportedException, IOException, ParseException {

        int initialSize = bookService.getAvailableBooks().size();

        bookService.addBook("TestBook", "TestAuthor", new Date());
        assertEquals(initialSize + 1, bookService.getAvailableBooks().size());

        int testBookForReturnTest = bookService.getAllBooks().size();
        Book borrowedBook = bookService.borrowBook(testBookForReturnTest);
        assertEquals("TestBook", borrowedBook.getTitle());
        assertEquals("TestAuthor", borrowedBook.getAuthor());
        assertEquals(initialSize, bookService.getAvailableBooks().size());

        Book returnedBook = bookService.returnBook(borrowedBook.getId());
        assertEquals(initialSize + 1, bookService.getAvailableBooks().size());
        assertEquals("TestBook", returnedBook.getTitle());
        assertEquals("TestAuthor", returnedBook.getAuthor());

        assertNull(bookService.returnBook(100));



    }
    @Test
    void getAllBooksWithChangeRoleAndCloneTest() throws CloneNotSupportedException {
        MyList<Book> booksAdmin = bookService.getAllBooks();
        MyList<Book> booksAdmin2 = bookService.getAllBooks();
        userService.getActiveUser().setRole(Role.USER);
        MyList<Book> booksUser = bookService.getAllBooks();
//        System.out.println(booksAdmin.get(0));
//        System.out.println(booksAdmin2.get(0));
//        System.out.println(booksUser.get(0));
        assertEquals(booksAdmin.get(0), booksAdmin2.get(0));
        assertNotEquals(booksAdmin.get(0), booksUser.get(0));
        assertEquals(booksAdmin.get(0).getId(), booksUser.get(0).getId());
    }
}