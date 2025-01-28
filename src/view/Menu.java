package view;

import repository.BookRepositoryInter;
import repository.BookRepositoryInterImpl;
import repository.CustomerRepository;
import repository.CustomerRepositoryImpl;
import service.BookService;
import service.BookServiceImpl;
import service.CustomerService;
import service.CustomerServiceImpl;
import utils.MyList;

import java.util.Scanner;

public class Menu {

//        private final BookService bookService;
//        private final CustomerService customerService;

    private final Scanner scanner = new Scanner(System.in);

      BookRepositoryInter bookRepository = new BookRepositoryInterImpl();
      CustomerRepository customerRepository = new CustomerRepositoryImpl();
      CustomerService service = new CustomerServiceImpl(customerRepository);
      BookService bookService = new BookServiceImpl(bookRepository, service);




        public void start() {
            showMenu();
        }

        private void showMenu() {
            while (true) {
                System.out.println("Добро пожаловать в меню");
                System.out.println("1. Меню книг");
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
                    // Todo show car menu
                    showBookMenu();
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

        private void showBookMenu(int choice) {
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

               int input = scanner.nextInt();
               scanner.nextLine();
               switch (choice) {
                   case 1:
                       listBooks();
                   break;
                   case 2:
                       searchBookByTitle();
                   break;
                   case 3:
                       searchBookByAuthor();
                   break;
                   case 4:
                       listAvailableBooks();
                   break;
                   case 5:
                       listUserBooks();
                   break;
                   case 6:
                       orderBook();
                   break;
                   case 7:
                       returnBook();
                   break;
                   default:
                       System.out.println("Сделайте корректный выбор");
                       waitRead();
               }

               // прервать текущий цикл
               //if (input == 0) break;

           }

        }

        private void showUserMenu() {
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

        private void handleUserMenuUsers(int input) {
            switch (input) {
                case 1:
                    // Авторизация
                    // Todo
                /*
                1. Запросить у пользователя email и пароль
                2. Передать полученные данные в СЕРВИСНЫЙ слой
                3. Получить ответ от сервисного слоя - прошел ли успешно login -
                4. Сообщить пользователю результат
                 */
                    break;
                case 2:
                    // Регистрация
                    // Todo
                /*
                1. Запросить необходимые данные (email, password)
                2. Передать данные в СЕРВИСНЫЙ слой
                3. Получить ответ - передать инфо клиенту
                 */
                    System.out.println("Регистрация нового пользователя");
                    System.out.println("Введи email:");
                    String email = scanner.nextLine();

                    System.out.println("Введите пароль: ");
                    String password = scanner.nextLine();

                    User user = service.registerUser(email, password);

                    if (user == null) {
                        System.out.println("Регистрация провалена!");
                    } else {
                        System.out.println("Вы успешно зарегистрировались в системе!");
                    }

                    waitRead();
                    break;

                case 3:
                    // Logout
                    service.logout();
                    System.out.println("Вы вышли из системы");
                    waitRead();
                    break;
            }
        }

        private void showAdminMenu() {
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



        private void waitRead() {
            System.out.println("\nДля продолжения нажмите Enter...");
            scanner.nextLine();
        }

        private void  showCarsList(MyList<Car> list) {
            for (Car car : list) {
                System.out.printf("%d. %s (%d г.в). Цена: 2.%f\n",
                        car.getId(), car.getModel(), car.getYear(), car.getPrice());
            }
        }


    }
}
