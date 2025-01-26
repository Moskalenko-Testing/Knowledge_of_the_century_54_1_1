package repository;

import model.Book;
import utils.MyList;

import java.util.Date;

public interface BookRepositoryInter {

    Book addBook(String title, String author, Date releaseDate);

    MyList<Book> getAllBooks();

    Book getById(int id);

    MyList<Book> searchBookByTitle(String title);

    MyList<Book> searchBookByAuthor(String author);

    Book borrowBook(int id);

    boolean returnBook(int id);

    MyList<Book> getAvailableBooks();

    MyList<Book> getBorrowedBooks();
    Book editBook(int id);

}

