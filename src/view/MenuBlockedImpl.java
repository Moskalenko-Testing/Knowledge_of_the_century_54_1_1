package view;

import model.Book;
import model.User;
import repository.BookRepository;
import repository.BookRepositoryImpl;
import repository.UserRepository;
import repository.UserRepositoryImpl;
import service.BookService;
import service.BookServiceImpl;
import service.UserService;
import service.UserServiceImpl;
import utils.MyList;

public class MenuBlockedImpl extends MenuMain implements MenuBlocked {
    UserService userService;
    BookService bookService;
    UserRepository userRepository;
    BookRepository bookRepository;

    public MenuBlockedImpl(UserService userService, BookService bookService, UserRepository userRepository, BookRepository bookRepository) {
        super();
        this.userService = userService;
        this.bookService = bookService;
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;

        addAllTitles();

    }
    private void addAllTitles() {
        menuTitle.put(1, "Показать все мои Книги" );
        menuTitle.put(2, "Вернуть все мои Книги");
        menuTitle.put(3, "Logout");
        menuTitle.put(4, "Вернуться в предыдущее меню");
    }
    public void startMenu() throws CloneNotSupportedException {
        printMenu();
        int result = scanMenu(4);
        switch (result) {
            case 1 -> getAllMyBook();
            case 2 -> returnAllBook();
            case 3 -> logoutUser();
            case 4 -> returnLastMenu();
        }
    }



    @Override
    public void getAllMyBook() throws CloneNotSupportedException {
        MyList<Book> books = userService.getActiveUser().getUserBooks();
        if (books != null && !books.isEmpty()){
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
    public void returnAllBook() throws CloneNotSupportedException {
        MyList<Book> books = userService.getActiveUser().getUserBooks();
        if (books != null && books.size()> 0) {
            for (Book book : books) {
                Book bookReturn = bookService.returnBook(book.getId());
                if(bookReturn instanceof Book) {
                    System.out.println(book.getTitle() + " Успешно возвращена");
                } else {
                    System.out.println(book.getTitle()  + " Невозможно вернуть");
                }
            }
            startMenu();
        } else {
            System.out.println("У Вас нет Книг");
            startMenu();
        }
    }

    @Override
    public void logoutUser() {
        System.out.println("Logout");
        System.exit(0);

    }

    @Override
    public void returnLastMenu() throws CloneNotSupportedException {
        WelcomeMenu welcomeMenu = new WelcomeMenu(userService, bookService, userRepository, bookRepository);
        welcomeMenu.startMenu();
    }
}
