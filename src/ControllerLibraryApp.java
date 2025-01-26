import model.Book;

import java.util.Date;

public class ControllerLibraryApp {
    public static void main(String[] args) throws CloneNotSupportedException {
        System.out.println("Hello world!");
        Book book = new Book(1, "Java", "Ivanov", new Date(1970, 1, 1));
        System.out.println(book);
        Book book2 = (Book) book.clone();
        System.out.println(book2);
        book.setAuthor("Pushkin");
        System.out.println("=================");
        System.out.println(book);
        System.out.println(book2);


    }
}
