package view;

import java.io.IOException;
import java.text.ParseException;

public interface MenuUserBooks {
    void showAllBooks() throws CloneNotSupportedException, IOException, ParseException;
    void getBookByTitle() throws CloneNotSupportedException, IOException, ParseException;
    void getBookByAuthor() throws CloneNotSupportedException, IOException, ParseException;
    void getAvailableBooks() throws CloneNotSupportedException, IOException, ParseException;
    void showMyBooks() throws CloneNotSupportedException, IOException, ParseException;
    void showMyReservBooks() throws CloneNotSupportedException, IOException, ParseException;
    void borrowBookByID() throws CloneNotSupportedException, IOException, ParseException;
    void returnBookByID() throws CloneNotSupportedException, IOException, ParseException;
    void reserveBookByID() throws CloneNotSupportedException, IOException, ParseException;
    void logout() throws CloneNotSupportedException, IOException;
    void returnLastMenu() throws CloneNotSupportedException, IOException, ParseException;
}
