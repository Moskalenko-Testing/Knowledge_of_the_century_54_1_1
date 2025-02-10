package view;

import model.Book;
import model.MenuMain;
import repository.BookRepository;
import repository.UserRepository;
import service.BookService;
import service.UserService;
import utils.MyList;

import java.io.IOException;
import java.text.ParseException;
import java.util.Scanner;

public class MenuUserBooksImpl extends MenuMain implements MenuUserBooks {
    UserService userService;
    BookService bookService;
    UserRepository userRepository;
    BookRepository bookRepository;

    public MenuUserBooksImpl(UserService userService, BookService bookService, UserRepository userRepository, BookRepository bookRepository) {
        super();
        this.userService = userService;
        this.bookService = bookService;
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;

        addAllTitles();
    }
    private void addAllTitles() {
        menuTitle.put(1, "Просмотр всех книг" );
        menuTitle.put(2, "Поиск книг по названию" );
        menuTitle.put(3, "Поиск книг по автору" );
        menuTitle.put(4, "Просмотр доступных книг" );
        menuTitle.put(5, "Просмотр моих книг");
        menuTitle.put(6, "Просмотр моих зарезервированных книг");
        menuTitle.put(7, "Взять книгу");
        menuTitle.put(8, "Вернуть книгу");
        menuTitle.put(9, "Забронировать книгу");
        menuTitle.put(10, "Logout");
        menuTitle.put(11, "Вернуться в предыдущее меню");
    }
    public void startMenu() throws CloneNotSupportedException, IOException, ParseException {
        printMenu();
        int result = scanMenu(11);
        switch (result) {
            case 1 -> showAllBooks();
            case 2 -> getBookByTitle();
            case 3 -> getBookByAuthor();
            case 4 -> getAvailableBooks();
            case 5 -> showMyBooks();
            case 6 -> showMyReservBooks();
            case 7-> borrowBookByID();
            case 8 -> returnBookByID();
            case 9 -> reserveBookByID();
            case 10 -> logout();
            case 11-> returnLastMenu();
        }
    }

    @Override
    public void showAllBooks() throws CloneNotSupportedException, IOException, ParseException {
        MyList<Book> books = bookService.getAllBooks();
        System.out.println(books.size());
        if (books != null && books.size() > 0) {
            for (Book book : books) {
                System.out.println(book);
            }
            startMenu();
        }
        else {
            System.out.println("Ни одной книги в Библиотеке нет!");
            startMenu();
        }
    }

    @Override
    public void getBookByTitle() throws CloneNotSupportedException, IOException, ParseException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите Название книги");
        String title = scanner.nextLine();
        MyList<Book> books = bookService.searchBookByTitle(title);
        if (books != null && books.size() > 0) {
            for (Book book : books) {
                System.out.println(book);

            }
            startMenu();
        } else {
            System.out.println("Нет книги с таким названием!");
            startMenu();
        }


    }

    @Override
    public void getBookByAuthor() throws CloneNotSupportedException, IOException, ParseException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите Автора книги");
        String author = scanner.nextLine();
        MyList<Book> books = bookService.searchBookByAuthor(author);
        if (books != null && books.size() > 0) {
            for (Book book : books) {
                System.out.println(book);

            }
            startMenu();
        } else {
            System.out.println("Нет соответсвующего Автора!");
            startMenu();
        }



    }

    @Override
    public void getAvailableBooks() throws CloneNotSupportedException, IOException, ParseException {
        MyList<Book> books = bookService.getAvailableBooks();
        if (books != null && books.size() > 0) {
            for (Book book : books) {
                System.out.println(book);
            }
            startMenu();
        } else {
            System.out.println("Нет доступных книг");
            startMenu();
        }
    }

    @Override
    public void showMyBooks() throws CloneNotSupportedException, IOException, ParseException {
        MyList<Book> books = userService.getActiveUser().getUserBooks();
        if (books != null && books.size() > 0) {
            for (Book book : books) {
                System.out.println(book);
            }
            startMenu();
        } else {
            System.out.println("У Вас нет книг");
            startMenu();
        }

    }

    @Override
    public void showMyReservBooks() throws CloneNotSupportedException, IOException, ParseException {
        MyList<Book> books = userService.getActiveUser().getReservationBooks();
        if (books != null && books.size() > 0) {
            for (Book book : books) {
                System.out.println(book);
            }
            startMenu();
        } else {
            System.out.println("У Вас нет зарезервированных книг");
            startMenu();
        }

    }

    @Override
    public void borrowBookByID() throws CloneNotSupportedException, IOException, ParseException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите ID книги");
        int idBook = scanner.nextInt();
        scanner.nextLine();
        Book book = bookService.borrowBook(idBook);
        if (book != null) {
            System.out.println("Вы получили Книгу: " + book.getTitle() + " " + book.getAuthor());
            startMenu();
        } else {
            System.out.println("Что то пошло не так 404");
            startMenu();
        }
    }

    @Override
    public void returnBookByID() throws CloneNotSupportedException, IOException, ParseException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите ID книги");
        int idBook = scanner.nextInt();
        scanner.nextLine();
        Book book = bookService.returnBook(idBook);
        if (book instanceof Book) {
            System.out.println("Вы вернули Книгу: " + book.getTitle() + " " + book.getAuthor());
            startMenu();
        } else {
            System.out.println("Что то пошло не так 404");
            startMenu();
        }
    }

    @Override
    public void reserveBookByID() throws CloneNotSupportedException, IOException, ParseException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите ID книги");
        int idBook = scanner.nextInt();
        scanner.nextLine();
        Book book = bookService.reserveBookByID(idBook);
        if (book instanceof Book) {
            System.out.println("Вы забронировали Книгу: " + book.getTitle() + " " + book.getAuthor());
            startMenu();
        } else {
            System.out.println("Что то пошло не так 404");
            startMenu();
        }


    }

    @Override
    public void logout() throws IOException, CloneNotSupportedException {
        if(bookService.logout() && userService.logout()) {
            System.out.println("Logout! Массив Книг обновлен!");
            System.exit(0);
        }
        System.out.println("Массив Книг не был корректно обновлен!" );
    }

    @Override
    public void returnLastMenu() throws CloneNotSupportedException, IOException, ParseException {
        MenuUserImpl menuUser = new MenuUserImpl(userService, bookService, userRepository, bookRepository);
        menuUser.startMenu();
    }
}
