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
import java.util.Date;
import java.util.Scanner;

public class MenuBooksAdminImpl extends MenuMain implements MenuBooksAdmin {
    UserService userService;
    BookService bookService;
    UserRepository userRepository;
    BookRepository bookRepository;

    public MenuBooksAdminImpl(UserService userService, BookService bookService, UserRepository userRepository, BookRepository bookRepository) {
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
        menuTitle.put(5, "Просмотр взятых книг");
        menuTitle.put(6, "Взять книгу");
        menuTitle.put(7, "Вернуть книгу");
        menuTitle.put(8, "Удалить книгу");
        menuTitle.put(9, "Редактировать книгу");
        menuTitle.put(10, "Добавить книгу");
        menuTitle.put(11, "Забронировать книгу");
        menuTitle.put(12, "Logout");
        menuTitle.put(13, "Вернуться в предыдущее меню");
    }
    public void startMenu() throws CloneNotSupportedException, IOException, ParseException {
        printMenu();
        int result = scanMenu(13);
        switch (result) {
            case 1 -> showAllBooks();
            case 2 -> getBookByTitle();
            case 3 -> getBookByAuthor();
            case 4 -> getAvailableBooks();
            case 5 -> getBorrowedBooks();
            case 6 -> borrowBookByID();
            case 7 -> returnBookByID();
            case 8 -> deleteBookByID();
            case 9 -> editBookByID();
            case 10 -> addBook();
            case 11 -> reserveBookByID();
            case 12 -> logout();
            case 13-> returnLastMenu();
        }
    }



    @Override
    public void showAllBooks() throws CloneNotSupportedException, IOException, ParseException {
        MyList<Book> books = bookRepository.getAllBooks();
        if (books != null && books.size() > 0) {
            for (Book book : books) {
                System.out.println(book);
            }
            startMenu();
        } else {
            System.out.println("Нет книг в библиотеке!");
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
    public void getBorrowedBooks() throws CloneNotSupportedException, IOException, ParseException {
        MyList<Book> books = bookService.getBorrowedBooks();
        if (books != null && books.size() > 0) {
            for (Book book : books) {
                System.out.println(book);
            }
            startMenu();
        } else {
            System.out.println("Нет выданных книг");
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
    public void deleteBookByID() throws CloneNotSupportedException, IOException, ParseException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите ID книги");
        int idBook = scanner.nextInt();
        scanner.nextLine();
        Book book = bookService.deleteBook(idBook);
        if (book instanceof Book) {
            System.out.println("Вы удалили Книгу: " + book.getTitle() + " " + book.getAuthor());
            startMenu();
        } else {
            System.out.println("Что то пошло не так 404");
            startMenu();
        }
    }

    @Override
    public void editBookByID() throws CloneNotSupportedException, IOException, ParseException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите ID книги");
        int idBook = scanner.nextInt();
        scanner.nextLine();
        Book book = bookService.getById(idBook);
        if (book instanceof Book) {
            System.out.println("Старые данные Книги: "
                    + book.getTitle() + "\nАвтор:  "
                    + book.getAuthor() + "\nДата издания: "
                    + (book.getReleaseDate().getYear() + 1900));
            String [] bookDate = saveBook();
            Date date = new Date();
            date.setYear(Integer.parseInt(bookDate[2]) -1900);
            book.setReleaseDate(date);
            book.setTitle(bookDate[0]);
            book.setAuthor(bookDate[1]);
            startMenu();

        } else {
            System.out.println("Что то пошло не так 404!");
            startMenu();
        }

    }

    @Override
    public void addBook() throws CloneNotSupportedException, IOException, ParseException {
        String [] bookDate = saveBook();
        Date date = new Date();
        date.setYear(Integer.parseInt(bookDate[2]) -1900);
        if(bookService.addBook(bookDate[0],bookDate[1],date)){
            System.out.println("Новая книга: " + bookDate[0] + " " + bookDate[1] + " добавлена");
            startMenu();
        } else {
            System.out.println("Что то пошло не так 404!");
            startMenu();
        }
    }

    @Override
    public void reserveBookByID() throws CloneNotSupportedException {
// TODO Menu -> Service -> Repo ->User
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
        MenuAdminImpl menuAdmin = new MenuAdminImpl(userService, bookService, userRepository, bookRepository);
        menuAdmin.startMenu();

    }
    private String[] saveBook () {
        String [] bookDate = new String[3];
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите названия книги");
        String title = scanner.nextLine();
        bookDate[0] = title;
        System.out.println("Введите автора книги");
        String author = scanner.nextLine();
        bookDate[1]= author;
        System.out.println("Введите год издания книги в формате: yyyy ");
        boolean controlOfDate = false;
        int year = 0;
        while (controlOfDate ==false) {
            Scanner scanDate = new Scanner(System.in);
            String date = scanDate.nextLine();
            try {
                year = Integer.parseInt(date.trim());
            }catch (NumberFormatException e){
                System.out.println("Please enter a number");
            }
            if(year > 0 && year <= 2025) {
                controlOfDate = true;
            } else {
                System.out.println(" Year > 0 or Year >= 2025");
            }
        }
        bookDate[2] = Integer.toString(year);
        return bookDate;
    }
}
