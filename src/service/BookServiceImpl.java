package service;

import model.Book;
import model.Role;
import repository.BookRepositoryInter;
import utils.MyArrayList;
import utils.MyList;

import java.util.Date;
import java.util.List;

public class BookServiceImpl implements BookService {
    private final BookRepositoryInter bookRepository;
    private final CustomerService customerService;

    public BookServiceImpl(BookRepositoryInter bookRepository, CustomerService customerService) {
        this.bookRepository = bookRepository;
        this.customerService = customerService;
    }

    @Override
    public boolean addBook(String title, String author, Date releaseDate) {
        if (customerService.getActiveCustomer().getRole() == Role.ADMIN) { // Книгу может добавлять только АДМ
            bookRepository.addBook(title, author, releaseDate);
            return true;
        }
        return false;
    }

    @Override
    public MyList<Book> getAllBooks() throws CloneNotSupportedException{
        return makeCloneList(bookRepository.getAllBooks());
    }

    @Override
    public Book getById(int id) throws CloneNotSupportedException {
        if (customerService.getActiveCustomer().getRole() == Role.ADMIN) { // Get только у админ
            return bookRepository.getById(id);
        }
        return null;
    }


    @Override
    public MyList<Book> searchBookByTitle(String title) throws CloneNotSupportedException {
        return makeCloneList(bookRepository.searchBookByTitle(title));
    }

    @Override
    public MyList<Book> searchBookByAuthor(String author) throws CloneNotSupportedException{
        return makeCloneList(bookRepository.searchBookByAuthor(author));
    }


    @Override
    public boolean returnBook(int id) {
        for (Book book : customerService.getActiveCustomer().getCustomerBooks()) {
            if (book.getId() == id) {
                customerService.getActiveCustomer().getCustomerBooks().remove(book);
                return bookRepository.returnBook(id);
            }
        }
        return false;
    }

    @Override
    public MyList<Book> getAvailableBooks() throws CloneNotSupportedException{
        return makeCloneList(bookRepository.getAvailableBooks());
    }

    @Override
    public MyList<Book> getBorrowedBooks() throws CloneNotSupportedException{
        return makeCloneList(bookRepository.getBorrowedBooks());
    }

    @Override
    public Book borrowBook(int id) throws CloneNotSupportedException {
            Role role = customerService.getActiveCustomer().getRole();
            switch (role) {
                case ADMIN -> customerService.getActiveCustomer().addCustomerBook(bookRepository.borrowBook(id));
                case USER -> {
                    Book borrowBook = bookRepository.borrowBook(id);
                    Book customerBook = (Book) borrowBook.clone();
                    return customerBook;
                }
            }
                    return null;
    }
    /*
    Метод сепарирования массива книг в зависимости от Роли Customer
     ADMIN -> return оригинал массива со ссылками на реальные книги
     USER -> Делаем глубокое копирование массива типа MyList<Book> и возвращаем только Клон
     BLOCKED -> null;

    * */

    private MyList<Book> makeCloneList(MyList<Book> books) throws CloneNotSupportedException {
        if (books != null) {
            Role role = customerService.getActiveCustomer().getRole();
            switch (role) {
                case ADMIN -> {return books;}
                case USER -> {
                    MyList<Book> tempBooks = new MyArrayList<>();
                    for (Book book : books) {
                        Book cloneBook = (Book) book.clone();
                        tempBooks.add(cloneBook);
                        return tempBooks;
                    }
                }
            }
        }
        return null;
    }
}
