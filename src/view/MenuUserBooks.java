package view;

public interface MenuUserBooks {
    void showAllBooks() throws CloneNotSupportedException;
    void getBookByTitle() throws CloneNotSupportedException;
    void getBookByAuthor() throws CloneNotSupportedException;
    void getAvailableBooks() throws CloneNotSupportedException;
    void showMyBooks() throws CloneNotSupportedException;
    void showMyReservBooks() throws CloneNotSupportedException;
    void borrowBookByID() throws CloneNotSupportedException;
    void returnBookByID() throws CloneNotSupportedException;
    void reserveBookByID() throws CloneNotSupportedException;
    void logout();
    void returnLastMenu() throws CloneNotSupportedException;
}
