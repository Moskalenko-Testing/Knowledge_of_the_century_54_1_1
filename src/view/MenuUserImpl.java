package view;

import model.User;
import repository.BookRepository;
import repository.BookRepositoryImpl;
import repository.UserRepository;
import repository.UserRepositoryImpl;
import service.BookService;
import service.BookServiceImpl;
import service.UserService;
import service.UserServiceImpl;

import java.util.Scanner;

public class MenuUserImpl extends MenuMain implements MenuUser {
    UserService userService;
    BookService bookService;
    UserRepository userRepository;
    BookRepository bookRepository;
    public MenuUserImpl(UserService userService, BookService bookService, UserRepository userRepository, BookRepository bookRepository) {
        super();
        this.userService = userService;
        this.bookService = bookService;
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
        addAllTitles();
    }
    private void addAllTitles() {
        menuTitle.put(1, "Сменить пароль" );
        menuTitle.put(2, "Удалить аккаунт");
        menuTitle.put(3, "Меню книг");
        menuTitle.put(4, "Logout");
        menuTitle.put(5, "Вернуться в предыдущее меню");
    }
    public void startMenu() throws CloneNotSupportedException {
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
    public void updatePassword() throws CloneNotSupportedException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите новый пароль: ");
        String newPassword = scanner.nextLine();
        System.out.println("newPassword: " + newPassword);
        System.out.println();
        startMenu();
    }

    @Override
    public void deleteAccount() throws CloneNotSupportedException {
        System.out.println("Delete Account");
        startMenu();
    }

    @Override
    public void showMenuUserBooks() throws CloneNotSupportedException {
        MenuUserBooksImpl menuUserBooks = new MenuUserBooksImpl(userService, bookService, userRepository, bookRepository);
        menuUserBooks.startMenu();
    }

    @Override
    public void logoutUser() {
        System.out.println("Logout");
        System.exit(0);
    }

    @Override
    public void returnLastMenu() throws CloneNotSupportedException {
        System.out.println("Return Last Menu");
        WelcomeMenu welcomeMenu = new WelcomeMenu(userService, bookService, userRepository, bookRepository);
        welcomeMenu.startMenu();
    }
}
