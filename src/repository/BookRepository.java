package repository;

import model.Book;
import utils.MyList;

import java.util.Date;

public interface BookRepository {

    Book saveBook(String title, String author, Date releaseDate);

    MyList<Book> getAllBooks();

    Book getById(int id);

    Book deleteBook(int id);
}

