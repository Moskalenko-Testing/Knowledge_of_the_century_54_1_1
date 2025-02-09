package view;

import java.io.IOException;
import java.text.ParseException;

public interface MenuBooksAdmin {

    void showAllBooks() throws CloneNotSupportedException, IOException, ParseException;
    void getBookByTitle() throws CloneNotSupportedException, IOException, ParseException;
    void getBookByAuthor() throws CloneNotSupportedException, IOException, ParseException;
    void getAvailableBooks() throws CloneNotSupportedException, IOException, ParseException;
    void getBorrowedBooks() throws CloneNotSupportedException, IOException, ParseException;
    void borrowBookByID() throws CloneNotSupportedException, IOException, ParseException;
    void returnBookByID() throws CloneNotSupportedException, IOException, ParseException;
    void deleteBookByID() throws CloneNotSupportedException, IOException, ParseException;
    void editBookByID() throws CloneNotSupportedException, IOException, ParseException;
    void addBook() throws CloneNotSupportedException, IOException, ParseException;
    void reserveBookByID() throws CloneNotSupportedException;
    void logout() throws IOException, CloneNotSupportedException;
    void returnLastMenu() throws CloneNotSupportedException, IOException, ParseException;
}
