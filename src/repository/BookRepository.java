package repository;

import model.Book;
import utils.MyList;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;

public interface BookRepository {
    // CRUD

    Book saveBook(String title, String author, Date releaseDate) throws IOException, ParseException;

    MyList<Book> getAllBooks();

    Book getById(int id);

    Book deleteBook(int id);
}

