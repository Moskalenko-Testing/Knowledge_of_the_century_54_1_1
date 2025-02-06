package service;

import model.Book;
import utils.MyList;

import java.util.Date;

public interface BookService {
    boolean addBook(String title, String author, Date releaseDate);
    MyList<Book> getAllBooks() throws CloneNotSupportedException;
    Book getById(int id) throws CloneNotSupportedException;
    MyList<Book> searchBookByTitle(String title) throws CloneNotSupportedException;
    MyList<Book> searchBookByAuthor(String author) throws CloneNotSupportedException;
    Book borrowBook(int id) throws CloneNotSupportedException;
    Book returnBook(int id) throws CloneNotSupportedException;
    MyList<Book> getAvailableBooks() throws CloneNotSupportedException;
    MyList<Book> getBorrowedBooks() throws CloneNotSupportedException;
    Book deleteBook(int id) throws CloneNotSupportedException;
    Book reserveBookByID(int Id) throws CloneNotSupportedException;
}
