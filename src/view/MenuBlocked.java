package view;

import java.io.IOException;
import java.text.ParseException;

public interface MenuBlocked {
    void getAllMyBook() throws CloneNotSupportedException, IOException, ParseException;
    void returnAllBook() throws CloneNotSupportedException, IOException, ParseException;
    void logoutUser();
    void returnLastMenu() throws CloneNotSupportedException, IOException, ParseException;
}
