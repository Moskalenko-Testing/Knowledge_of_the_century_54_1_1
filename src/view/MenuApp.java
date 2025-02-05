package view;

import repository.BookRepository;
import repository.BookRepositoryImpl;
import repository.UserRepository;
import repository.UserRepositoryImpl;
import service.BookService;
import service.BookServiceImpl;
import service.UserService;
import service.UserServiceImpl;

import java.io.IOException;
import java.text.ParseException;

public class MenuApp {
    public static void main(String[] args) throws CloneNotSupportedException, IOException, ParseException {
        BookRepository bookRepository = new BookRepositoryImpl();
        UserRepository userRepository = new UserRepositoryImpl();
        UserService userService = new UserServiceImpl(userRepository, bookRepository);
        BookService bookService = new BookServiceImpl(bookRepository, userService);


       WelcomeMenu welcomeMenu = new WelcomeMenu(userService, bookService, userRepository, bookRepository);
       welcomeMenu.startMenu();
    }
}
