package view;

import model.Book;
import model.Role;
import model.User;
import repository.BookRepositoryInter;
import repository.BookRepositoryInterImpl;
import repository.CustomerRepository;
import repository.CustomerRepositoryImpl;
import service.BookService;
import service.BookServiceImpl;
import service.UserService;
import service.CustomerServiceImpl;
import utils.MyList;
import utils.PersonValidition;

import java.util.Scanner;

public class Menu {

//        private final BookService bookService;
//        private final CustomerService customerService;

    private final Scanner scanner = new Scanner(System.in);

    BookRepositoryInter bookRepository = new BookRepositoryInterImpl();
    CustomerRepository customerRepository = new CustomerRepositoryImpl();
    UserService service = new CustomerServiceImpl(customerRepository);
    BookService bookService = new BookServiceImpl(bookRepository, service);


    public void start() {
        showMenu();
    }

    private void showMenu() {
        while (true) {
            System.out.println("Добро пожаловать в меню");
           // System.out.println("1. Меню книг");
            System.out.println("2. Меню пользователя");
            System.out.println("3. Меню администратора");
            System.out.println("0. Выход из системы");

            int choice = scanner.nextInt();
            scanner.nextLine();

            if (choice == 0) {
                System.out.println("До свидания!");
                // Завершить работу приложения
                System.exit(0);
            }

            showSubMenu(choice);

        }
    }

    private void showSubMenu(int choice) {
        switch (choice) {
            case 1:
               // showBookMenu();
                break;
            case 2:
                // Todo show User Menu
                showUserMenu();
                break;
            case 3:
                // Todo show Admin Menu
                showAdminMenu();
                break;
            default:
                System.out.println("Сделайте корректный выбор");
                waitRead();
        }
    }

    private void showBookMenu() throws CloneNotSupportedException {
        while (true) {
            System.out.println("Меню книг");
            System.out.println("1. Просмотр всех книг");
            System.out.println("2. Поиск книг по названию");
            System.out.println("3. Поиск книг по автору");
            System.out.println("4. Просмотр доступных книг");
            System.out.println("5. Просмотр книг у пользователя");
            System.out.println("6. Заказать книгу"); // TODO реализовать вызов сервиса
            System.out.println("7. Вернуть книгу");
            System.out.println("0. Вернуться в предыдущее меню");

            int showBookMenuChoice = scanner.nextInt();
            scanner.nextLine();
            switch (showBookMenuChoice) {
                case 1:
                    getAllBooks();
                    break;
                case 2:
                    searchBookByTitle();
                    break;
                case 3:
                    searchBookByAuthor();
                    break;
                case 4:
                    getAvailableBooks();
                    break;
                case 5:
                    listUserBooks();
                    break;
                case 6:
                    borrowBook();
                    break;
                case 7:
                    returnBook();
                    break;
                case 0:
                    showBookMenu();
                    break;
                default:
                    service.logoutCustomer();
                    System.out.println("Сделайте корректный выбор");
                    waitRead(); //метод ожидания чтения клавиатуры
                    break;

            }

            // прервать текущий цикл
            //if (input == 0) break;

        }

    }


    private void returnBook() {
        System.out.println("Вepните книгу:");
        Scanner scanner = new Scanner(System.in);
        int id = scanner.nextInt();
        scanner.nextLine();
        boolean result = bookService.returnBook(id);
        if (result) {
            System.out.println("Книга успешно возвращена");
        } else {
            System.out.println("Книга не была вoзвращена");
        }

    }

    private void borrowBook() throws CloneNotSupportedException {
        System.out.println("Выберите книгу:");
        Scanner scanner = new Scanner(System.in);
        int id = scanner.nextInt();
        scanner.nextLine();
        Book borrowBook = bookService.borrowBook(id);
        System.out.println("Вы взяли книгу: " + borrowBook.toString());


    }

    private void listUserBooks() throws CloneNotSupportedException {
        MyList<Book> books = service.getActiveCustomer().getCustomerBooks();

    }

    private void getAvailableBooks() throws CloneNotSupportedException {
        MyList<Book> books = bookService.getAvailableBooks();
        System.out.println("Доступные книги:" + books.toString());
    }

    private void getAllBooks() throws CloneNotSupportedException {
        MyList<Book> books = bookService.getAllBooks();
        System.out.println("Все книги:" + books.toString());
    }

    private void searchBookByTitle() throws CloneNotSupportedException {
        System.out.println("Введите название книги:");
        String title = scanner.nextLine();
        MyList<Book> books = bookService.searchBookByTitle(title);
        System.out.println("Книги по названию:" + books.toString());
    }
    private void waitRead() {
        System.out.println("\nДля продолжения нажмите Enter...");
        scanner.nextLine();
    }
    private void searchBookByAuthor() throws CloneNotSupportedException {
        System.out.println("Введите автора книги:");
        Scanner scanner = new Scanner(System.in);
        String author = scanner.nextLine();
        MyList<Book> books = bookService.searchBookByAuthor(author);
        System.out.println("Книги по автору:" + books.toString());

    }

        private void showUserMenu () {
            while (true) {
                System.out.println("Меню пользователя");
                System.out.println("1. Вход в систему");
                System.out.println("2. Регистрация нового пользователя");
                System.out.println("3. Logout");
                System.out.println("0. Вернуться в предыдущее меню");

                System.out.println("\nСделайте выбор пункта меню");
                int input = scanner.nextInt();
                scanner.nextLine();

                // прервать текущий цикл
                if (input == 0) break;

                handleUserMenuUsers(input);

            }
        }
    // Авторизация
    // Todo
                /*
                1. Запросить у пользователя email и пароль
                2. Передать полученные данные в СЕРВИСНЫЙ слой
                3. Получить ответ от сервисного слоя - прошел ли успешно login -
                4. Сообщить пользователю результат
                 */

        private void handleUserMenuUsers ( int input){
            switch (input) {
                case 1:
                    System.out.println("Вход в систему");
                    System.out.println(" Введи email:");
                    Scanner scanner = new Scanner(System.in);
                    String email = scanner.nextLine();
                    System.out.println("Введите пароль: ");
                    String password = scanner.nextLine();
                    boolean registrationResult = service.loginCustomer(email, password);
                    if (registrationResult) {
                        System.out.println("Вы успешно вошли в систему!");
                        Role role = service.getActiveCustomer().getRole();
                        switch (role) {
                            case ADMIN:
                                showAdminMenu();
                                break;
                            case USER:
                                showUserMenu();
                                break;
                            case BLOCKED:
                                System.out.println("Ваш аккаунт заблокирован!");
                                break;

                        }

                    }

                    break;
                case 2:
                    System.out.println("Регистрация нового пользователя");
                    System.out.println("Введи email:");
                    Scanner scanner2 = new Scanner(System.in);
                    String emailNew = scanner2.nextLine();
                    System.out.println("Введите пароль: ");
                    String passwordNew = scanner2.nextLine();
                    User userNew = service.registerUser(emailNew, passwordNew);
                    if (userNew != null) {
                        System.out.println("Вы успешно зарегистрировались в системе!");
                        showServiceUserMenu();
                    }
                    waitRead();
                    break;

                case 3:
                    service.logoutUser();
                    System.out.println("Вы вышли из системы");
                    waitRead();
                    break;
            }
        }
        private void showServiceUserMenu () throws CloneNotSupportedException {
            System.out.println("Меню пользователя");
            System.out.println("1. Меню книги");
            System.out.println("2. Сменить пароль");
            System.out.println("3. Удалить аккаунт");
            System.out.println("4. Logout");
            System.out.println("0. Вернуться в предыдущее меню");
            Scanner scanner = new Scanner(System.in);
            int input = scanner.nextInt();
            scanner.nextLine();
            switch (input) {
                case 1:
                    showBookMenu();
                    break;
                case 2:
                    System.out.println("Введите новый пароль: ");
                    String newPassword = scanner.nextLine();
                    service.updatePassword(service.getActiveCustomer().getEmail(), newPassword);
                    break;
                case 3:
                    deleteAccount(); //Todo реализовать удаление аккаунта
                    break;
                case 4:
                    service.logoutUser();
                    System.out.println("Вы вышли из системы");
                    waitRead();
                    break;
            }

        }

        private void showAdminMenu () {
            while (true) {
                System.out.println("Меню администратора");
                System.out.println("1. Регистрация нового пользователя");
                System.out.println("2. Блокировка пользователя");
                System.out.println("3. Найти пользователя по Е-mail");
                System.out.println("4. Найти активного пользователя");
                System.out.println("5. Добавление новой книги");
                System.out.println("6. Удаление книги");
                System.out.println("7. Найти книгу по Id");
                System.out.println("8. Logout");
                System.out.println("0. Вернуться в предыдущее меню");

                int input = scanner.nextInt();
                scanner.nextLine();

                switch (input) {
                    case 1:
                        registerCustomer();
                        break;
                    case 2:
                        blockedCustomer();
                        break;
                    case 3:
                        getCustomerByEmail();
                        break;
                    case 4:
                        getActiveCustomer();
                        break;
                    case 5:
                        addBook();
                        break;
                    case 6:
                        deleteBook();
                        break;
                    case 7:
                        getBookById();
                        break;
                    case 8:
                        service.logout();
                        System.out.println("Вы вышли из системы");
                        waitRead();
                        break;

                }
            }
        }

        private void showCarsList (MyList < Car > list) {
            for (Car car : list) {
                System.out.printf("%d. %s (%d г.в). Цена: 2.%f\n",
                        car.getId(), car.getModel(), car.getYear(), car.getPrice());
            }
        }



}
