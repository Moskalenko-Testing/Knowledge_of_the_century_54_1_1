import model.Book;
import repository.BookRepositoryInter;
import repository.BookRepositoryInterImpl;
import repository.UserRepository;
import repository.UserRepositoryImpl;
import service.BookService;
import service.BookServiceImpl;
import service.UserService;
import service.UserServiceImpl;
import utils.MyList;

import java.util.Date;

public class ControllerLibraryApp {
    public static void main(String[] args) throws CloneNotSupportedException {
//        Book book = new Book(1, "Java", "Ivanov", new Date(1970, 1, 1));
//        System.out.println(book);
//        Book book2 = (Book) book.clon();
//        System.out.println(book2);
//        book.setAuthor("Pushkin");
//        System.out.println("=================");
//        System.out.println(book);
//        System.out.println(book2);
        BookRepositoryInter bookRepositoryInter = new BookRepositoryInterImpl();
        UserRepository userRepository = new UserRepositoryImpl();
        UserService userService = new UserServiceImpl(userRepository);
        System.out.println(bookRepositoryInter.getAllBooks().size());
        MyList<Book> newTestBooks = bookRepositoryInter.getAllBooks();
        for (Book book : newTestBooks) {
            System.out.println(book);
        }
        BookService service = new BookServiceImpl(bookRepositoryInter, userService);
        service.addBook("TestBook4", "TestAuthor 4", new Date());
        System.out.println("Book added successfully =======================");
        for (Book book : newTestBooks) {
            System.out.println(book);
        }
        System.out.println("================================================");
        System.out.println(service.getById(4));
        System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++");
        System.out.println(service.borrowBook(4));
        System.out.println(userService.getActiveCustomer().getCustomerBooks().size());
        System.out.println(service.borrowBook(1));
        System.out.println(userService.getActiveCustomer().getCustomerBooks().size());
        System.out.println("================================================");
        MyList<Book> newTestBooks2 = userService.getActiveCustomer().getCustomerBooks();
        for (Book book : newTestBooks2) {
            System.out.println(book);
        }
        System.out.println("============)))))))))+++++++++++++");
        MyList<Book> testBookByAuthor =  service.searchBookByAuthor("TestAuthor2");
        for (Book book : testBookByAuthor) {
            System.out.println(book + " search By Author");
        }
        System.out.println("=================================");
        MyList<Book> borrowBooks = service.getBorrowedBooks();
        System.out.println("+++++++++++++++++++++++++++++++++");
        for (Book book : borrowBooks) {
            System.out.println(book + " borrow Book");
        }
        System.out.println(userService.getActiveCustomer().getCustomerBooks().size());
        System.out.println(service.returnBook(1) + " Return Book 1");
        System.out.println(userService.getActiveCustomer().getCustomerBooks().size());
        System.out.println(service.returnBook(2) + " Borrow Book 2");
        System.out.println(userService.getActiveCustomer().getCustomerBooks().size());
        System.out.println(service.returnBook(4) + " Return Book 4");
        System.out.println(userService.getActiveCustomer().getCustomerBooks().size());
        System.out.println(service.getAvailableBooks().size());




    }
}
