package view;

import model.Book;
import model.MenuMain;
import model.User;
import repository.BookRepository;
import repository.UserRepository;
import service.BookService;
import service.UserService;
import utils.MyArrayList;
import utils.MyList;

import java.io.IOException;
import java.text.ParseException;
import java.util.Scanner;

public class MenuUserImpl extends MenuMain implements MenuUser {
    UserService userService;
    BookService bookService;
    UserRepository userRepository;
    BookRepository bookRepository;
    public MenuUserImpl(UserService userService, BookService bookService, UserRepository userRepository, BookRepository bookRepository) throws CloneNotSupportedException {
        super();
        this.userService = userService;
        this.bookService = bookService;
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
        addAllTitles();
        halloUsers();
    }
    private void halloUsers() throws CloneNotSupportedException {
        MyList<Book> reserveUserBooks = new MyArrayList<>();
        User user = userService.getActiveUser();
        for (Book book: user.getReservationBooks()) {
            Book needBook = bookService.getById(book.getId());
            if (needBook instanceof Book && needBook.isBorrowed() == false) {
                reserveUserBooks.add(needBook);
            }
        }
        if(reserveUserBooks.size() > 0) {
            System.out.println("Сейчас доступны заказанные Вами книги: ");
            for (Book book: reserveUserBooks) {
                System.out.println(book);
            }
        }
    }

    private void addAllTitles() {
        menuTitle.put(1, "Сменить пароль" );
        menuTitle.put(2, "Удалить аккаунт");
        menuTitle.put(3, "Меню книг");
        menuTitle.put(4, "Logout");
        menuTitle.put(5, "Вернуться в предыдущее меню");
    }
    public void startMenu() throws CloneNotSupportedException, IOException, ParseException {
        printMenu();
        int result = scanMenu(5);
        switch (result) {
            case 1 -> updatePassword();
            case 2 -> deleteAccount();
            case 3 -> showMenuUserBooks();
            case 4 -> logoutUser();
            case 5 -> returnLastMenu();
                    }
    }

    @Override
    public void updatePassword() throws CloneNotSupportedException, IOException, ParseException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите новый пароль: ");
        String newPassword = scanner.nextLine();
        if(userService.updatePassword(userService.getActiveUser().getEmail(), newPassword)) {
            System.out.println("newPassword: " + newPassword);
            startMenu();
        } else {
            System.out.println("Пароль не изменен");
            startMenu();
        }
    }

    @Override
    public void deleteAccount() throws CloneNotSupportedException, IOException, ParseException {
        if(userService.deleteUser(userService.getActiveUser().getEmail())) {
            System.out.println("Delete Account");
            WelcomeMenu welcomeMenu = new WelcomeMenu(userService, bookService, userRepository, bookRepository);
            welcomeMenu.startMenu();
        } else {
            System.out.println("Удаление не возможно!");
            startMenu();
        }

    }

    @Override
    public void showMenuUserBooks() throws CloneNotSupportedException, IOException, ParseException {
        MenuUserBooksImpl menuUserBooks = new MenuUserBooksImpl(userService, bookService, userRepository, bookRepository);
        menuUserBooks.startMenu();
    }

    @Override
    public void logoutUser() throws IOException, CloneNotSupportedException {
        if(userService.logout() && bookService.logout()) {
            System.out.println("Logout! Массив Users обновлен!");
            System.exit(0);
        }
        System.out.println("Массив Users не был корректно обновлен!" );
    }

    @Override
    public void returnLastMenu() throws CloneNotSupportedException, IOException, ParseException {
        System.out.println("Return Last Menu");
        WelcomeMenu welcomeMenu = new WelcomeMenu(userService, bookService, userRepository, bookRepository);
        welcomeMenu.startMenu();
    }
}
