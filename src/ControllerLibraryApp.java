import model.Book;

import java.util.Date;

public class ControllerLibraryApp {
    public static void main(String[] args) {
        System.out.println("Hello world!");
        Book book = new Book("Java", "Ivanov", new Date(1970, 1, 1));
        System.out.println(book.getId());


    }
}
