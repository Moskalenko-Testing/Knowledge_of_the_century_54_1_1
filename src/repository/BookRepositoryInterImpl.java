package repository;

import model.Book;
import utils.MyArrayList;
import utils.MyList;

import javax.xml.crypto.Data;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

public class BookRepositoryInterImpl implements BookRepositoryInter {
    private MyList<Book> books = new MyArrayList<>();
    private final AtomicInteger idGenerator = new AtomicInteger(1);

    public BookRepositoryInterImpl() {
       MyList<Book> books = new MyArrayList<>();
       addTestBooks();

    }
    private void addTestBooks() {
        Book testBook1 = new Book(idGenerator.getAndIncrement(),"TestBook1", "TestAuthor1", new Date(2011, 3, 1));
        Book testBook2 = new Book(idGenerator.getAndIncrement(),"TestBook2", "TestAuthor2", new Date(1975, 3, 10));
        Book testBook3 = new Book(idGenerator.getAndIncrement(),"TestBook3", "TestAuthor3", new Date(2024, 3, 30));
        books.add(testBook1);
        books.add(testBook2);
        books.add(testBook3);


    };

    @Override
    public Book addBook(String title, String author, Date releaseDate) {
        Book book = new Book(idGenerator.getAndIncrement(), title, author, releaseDate);
        books.add(book);
        return book;
    }

    @Override
    public MyList<Book> getAllBooks() {

        return books;
    }

    @Override
    public Book getById(int id) {
        for (Book book : books) {
            if (book.getId() == id) {
                return book;
            }
        }
        return null;
    }

    @Override
    public MyList<Book> searchBookByTitle(String title) {
        MyList<Book> result = new MyArrayList<>();
        for (Book book : books) {
            if (book.getTitle().equalsIgnoreCase(title)) {
                result.add(book);
            }
        }
        return result;
    }

    @Override
    public MyList<Book> searchBookByAuthor(String author) {
        MyList<Book> result = new MyArrayList<>();
        for (Book book : books) {
            if (book.getAuthor().equalsIgnoreCase(author)) {
                result.add(book);
            }
        }
        return result;
    }

    @Override
    public Book borrowBook(int id) {
        for (Book book : books) {
            if (book.getId() == id && !book.isBorrowed()) {
                book.setBorrowed(true);
                return book;
            }
        }
        return null;
    }

    @Override
    public boolean returnBook(int id) {
        for (Book book : books) {
            if (book.getId() == id) {
                book.setBorrowed(false);
                return true;
            }
        }
        return false;
    }

    @Override
    public MyList<Book> getAvailableBooks() {
        MyList<Book> availableBooks = new MyArrayList<>();
        for (Book book : books) {
            if (!book.isBorrowed()) {
                availableBooks.add(book);
            }
        }
        return availableBooks;
    }

    @Override
    public MyList<Book> getBorrowedBooks() {
        MyList<Book> borrowedBooks = new MyArrayList<>();
        for (Book book : books) {
            if (book.isBorrowed()) {
                borrowedBooks.add(book);
            }
        }
        return borrowedBooks;
    }

    @Override
    public Book editBook(int id) {
        return getById(id);
    }
}
