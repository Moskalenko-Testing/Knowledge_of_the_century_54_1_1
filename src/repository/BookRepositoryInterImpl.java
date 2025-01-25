package repository;

import model.Book;
import utils.MyArrayList;
import utils.MyList;

import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

public class BookRepositoryInterImpl implements BookRepositoryInter {
    private MyList<Book> books = new MyArrayList<>();
    private final AtomicInteger idGenerator = new AtomicInteger(1);

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
    public boolean borrowBook(int id) {
        for (Book book : books) {
            if (book.getId() == id && !book.isBorrowed()) {
                book.setBorrowed(true);
                return true;
            }
        }
        return false;
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
}
