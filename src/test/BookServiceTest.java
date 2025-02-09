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
        User testUser = userService.registerUser("super@pupper.com", "Super12345+");
        testUser.setRole(Role.ADMIN);
        userService.setActiveUser(testUser);
    }

    @Test
    void addBook() throws CloneNotSupportedException, IOException, ParseException {
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
    void getById() throws CloneNotSupportedException {
        Book book1 = bookService.getById(1);
        Book book2 = bookService.getById(1);
        assertEquals(book1, book2 );
        //Проверка на запрет данного метода для Роли USER
        userService.getActiveUser().setRole(Role.USER);
        Book book3 = bookService.getById(1);
        assertNotEquals(book1, book3 );
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
        bookService.addBook("TestBook", "TestAuthor", new Date());
        assertEquals(0, bookService.getBorrowedBooks().size());
        Book testBook = bookService.borrowBook(11);
        assertEquals("TestBook", testBook.getTitle());
        assertEquals("TestAuthor", testBook.getAuthor());
        assertEquals(10, bookService.getAvailableBooks().size());
        assertEquals(1, bookService.getBorrowedBooks().size());

        Book borrowBookUnrealId = bookService.borrowBook(100);
        assertEquals(null, borrowBookUnrealId);
        assertEquals(1, bookService.getBorrowedBooks().size());
        assertEquals(1, userService.getActiveUser().getUserBooks().size());
        // Role User
        userService.getActiveUser().setRole(Role.USER);
        assertEquals(Book.class, bookService.borrowBook(2).getClass());
        assertEquals(2, bookService.getBorrowedBooks().size());
        assertEquals(2, userService.getActiveUser().getUserBooks().size());
        assertEquals(9, bookService.getAvailableBooks().size());


    }

    @Test
    void returnBook() throws CloneNotSupportedException, IOException, ParseException {
        assertEquals(10, bookService.getAvailableBooks().size());
        bookService.addBook("TestBook", "TestAuthor", new Date());
        assertEquals(11, bookService.getAvailableBooks().size());
        bookService.borrowBook(11);
        assertEquals(10, bookService.getAvailableBooks().size());
        assertEquals("TestBook", bookService.getBorrowedBooks().get(0).getTitle());
        assertEquals("TestAuthor", bookService.getBorrowedBooks().get(0).getAuthor());
        assertEquals(1, userService.getActiveUser().getUserBooks().size());
        Book testBook = bookService.returnBook(11);
        assertEquals(11, bookService.getAvailableBooks().size());
        assertEquals("TestBook", testBook.getTitle());
        assertEquals("TestAuthor", testBook.getAuthor());
        assertEquals(0, bookService.getBorrowedBooks().size());
        assertEquals(0, userService.getActiveUser().getUserBooks().size());
        Book borrowBookUnrealId = bookService.returnBook(100);
        assertEquals(null,borrowBookUnrealId);
        //Role User

        userService.getActiveUser().setRole(Role.USER);
        Book bookTestUser = bookService.borrowBook(1);
        assertEquals(1, bookService.getBorrowedBooks().size());
        Book bookTestReturnUser = bookService.returnBook(1);
        assertEquals(Book.class, bookTestReturnUser.getClass());
        Book testBookReturnUserUnrealId = bookService.returnBook(100);
        assertEquals(null, testBookReturnUserUnrealId);

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