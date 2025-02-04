package repository;

import model.Book;
import org.junit.jupiter.params.shadow.com.univocity.parsers.csv.CsvParser;
import utils.MyArrayList;
import utils.MyList;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

public class BookRepositoryImpl implements BookRepository {
    private MyList<Book> books = new MyArrayList<>();
    private final AtomicInteger idGenerator = new AtomicInteger(1);

    public BookRepositoryImpl() throws IOException, ParseException {
       MyList<Book> books = new MyArrayList<>();
       addTestBooks();

    }
    private void addTestBooks() throws IOException, ParseException {
        String row;
        BufferedReader reader = new BufferedReader(new FileReader("books.csv"));
        while ((row = reader.readLine()) != null) {
                String[] fields = row.split(";");
                String title = fields[0];
                String author = fields[1];
                Date releaseDate = new SimpleDateFormat("yyyy").parse(fields[2]);
                saveBook(title, author, releaseDate);
        }
    }
    @Override
    public Book saveBook (String title, String author, Date releaseDate) {
        for (Book book : books) {
            if (book.getTitle().equals(title) && book.getAuthor().equals(author) && book.getReleaseDate().equals(releaseDate)) {
                return book;
            }
        }
        Book newBook = new Book(idGenerator.getAndIncrement(), title, author, releaseDate);
        books.add(newBook);
        return newBook;
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
    public Book deleteBook(int id) {
        for (Book book : books) {
            if (book.getId() == id) {
                books.remove(book);
                return book;
            }
        }
        return null;
    }
}