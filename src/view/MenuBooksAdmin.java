package view;

public interface MenuBooksAdmin {
//            System.out.println("Меню книг");
//            System.out.println("1. Просмотр всех книг");
//            System.out.println("2. Поиск книг по названию");
//            System.out.println("3. Поиск книг по автору");
//            System.out.println("4. Просмотр доступных книг");
    //
//            System.out.println(NNN Просмотр книг у пользователя") В меню администратора
//            System.out.println("6. Заказать книгу"); // TODO реализовать вызов сервиса
//            System.out.println("7. Вернуть книгу");
//            System.out.println("0. Вернуться в предыдущее меню");

    void showAllBooks() throws CloneNotSupportedException;
    void getBookByTitle() throws CloneNotSupportedException;
    void getBookByAuthor() throws CloneNotSupportedException;
    void getAvailableBooks() throws CloneNotSupportedException;
    void getBorrowedBooks() throws CloneNotSupportedException;
    void borrowBookByID() throws CloneNotSupportedException;
    void returnBookByID() throws CloneNotSupportedException;
    void deleteBookByID() throws CloneNotSupportedException;
    void editBookByID() throws CloneNotSupportedException;
    void addBook() throws CloneNotSupportedException;
    void reserveBookByID() throws CloneNotSupportedException;
    void logout();
    void returnLastMenu() throws CloneNotSupportedException;
}
