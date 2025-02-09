package view;
import model.Book;
import model.MenuMain;
import model.Role;
import model.User;
import repository.BookRepository;
import repository.UserRepository;
import service.BookService;
import service.UserService;
import utils.MyList;

import java.io.IOException;
import java.text.ParseException;
import java.util.Scanner;
//userService, bookService, userRepository, bookRepository

public class MenuAdminImpl extends MenuMain implements MenuAdmin {

    UserService userService;
    BookService bookService;
    UserRepository userRepository;
    BookRepository bookRepository;
    public MenuAdminImpl(UserService userService, BookService bookService, UserRepository userRepository, BookRepository bookRepository) {
        super();
        this.userService = userService;
        this.bookService = bookService;
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;

        addAllTitles();
    }
    private void addAllTitles() {
        menuTitle.put(1, "Сменить роль User by Email" );
        menuTitle.put(2, "Удалить аккаунт User by Email" );
        menuTitle.put(3, "Редактировать данны User by Email" );
        menuTitle.put(4, "Найти User by Email" );
        menuTitle.put(5, "HOW I AM?");
        menuTitle.put(6, "Распечатать всех Users");
        menuTitle.put(7, "Распечатать Все Книги User By Email");
        menuTitle.put(8, "Меню книг");
        menuTitle.put(9, "Logout");
        menuTitle.put(10, "Вернуться в предыдущее меню");
    }
    public void startMenu() throws CloneNotSupportedException, IOException, ParseException {
        printMenu();
        int result = scanMenu(10);
        switch (result) {
            case 1 -> setNewUserRoleByEmail();
            case 2 -> deleteUserByEmail();
            case 3 -> editUserByEmail();
            case 4 -> findUserByEmail();
            case 5 -> getActiveUser();
            case 6 -> getAllUsers();
            case 7 -> getAllUserBooksByEmail();
            case 8 -> getMenuBooks();
            case 9 -> logout();
            case 10 -> returnLastMenu();
        }
    }
    private User returnUserByEmail() {
        System.out.println("Введи Email требуемого User");
        Scanner scanner = new Scanner(System.in);
        String email = scanner.nextLine();
        return userService.getUserByEmail(email);
    }

    @Override
    public void setNewUserRoleByEmail() throws CloneNotSupportedException, IOException, ParseException {
        User tempUser = returnUserByEmail();
        System.out.println(tempUser);
        System.out.println("Введите новую роль User");
        System.out.println("1, ADMIN");
        System.out.println("2, USER");
        System.out.println("3, BLOCKED");
        System.out.println("4, Вернуться в предыдущее меню");
        Scanner scanner = new Scanner(System.in);
        int newUserRole = scanner.nextInt();
        scanner.nextLine();
        switch (newUserRole) {
            case 1 -> tempUser.setRole(Role.ADMIN);
            case 2 -> tempUser.setRole(Role.USER);
            case 3 -> tempUser.setRole(Role.BLOCKED);
            case 4 -> this.startMenu();
        }
        startMenu();
    }
    @Override
    public void deleteUserByEmail() throws CloneNotSupportedException, IOException, ParseException {
        User tempUser = returnUserByEmail();
        if (userService.deleteUser(tempUser.getEmail())) {
            System.out.println(" User Deleted Successfully");
        } else {
            System.out.println(" User Delete Failed");
        }
        startMenu();
    }

    @Override
    public void editUserByEmail() {
        // Menu edit User

    }

    @Override
    public void findUserByEmail() throws CloneNotSupportedException, IOException, ParseException {
        User tempUser = returnUserByEmail();
        System.out.println(tempUser);
        startMenu();
    }

    @Override
    public void getActiveUser() throws CloneNotSupportedException, IOException, ParseException {
        System.out.println(userService.getActiveUser());
        startMenu();

    }

    @Override
    public void getAllUsers() throws CloneNotSupportedException, IOException, ParseException {
        MyList<User> users = userRepository.getAllUsers();
        if (users != null && users.size() > 0) {
            for (User user : users) {
                System.out.println(user);
            }
        } else {
            System.out.println("No User Found");
        }
        startMenu();
    }

    @Override
    public void getAllUserBooksByEmail() throws CloneNotSupportedException, IOException, ParseException {
        User tempUser = returnUserByEmail();
        MyList<Book> books = tempUser.getUserBooks();
        if (books != null && books.size() > 0) {
            for (Book book : books) {
                System.out.println(book);
            }
        } else {
            System.out.println("No User Books Found");
        }
        startMenu();

    }

    @Override
    public void logout() throws IOException {
        if(userService.logout()) {
            System.out.println("Logout! Массив Users обновлен!");
            System.exit(0);
        }
        System.out.println("Массив Users не был корректно обновлен!" );

    }





    @Override
    public void getMenuBooks() throws CloneNotSupportedException, IOException, ParseException {
        MenuBooksAdminImpl menuBooksAdmin = new MenuBooksAdminImpl(userService, bookService, userRepository, bookRepository);
        menuBooksAdmin.startMenu();

    }

    @Override
    public void returnLastMenu() throws CloneNotSupportedException, IOException, ParseException {
        WelcomeMenu welcomeMenu = new WelcomeMenu(userService, bookService, userRepository, bookRepository);
        welcomeMenu.startMenu();
    }
}
