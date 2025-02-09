package view;

import java.io.IOException;
import java.text.ParseException;

public interface MenuAdmin {

    void setNewUserRoleByEmail () throws CloneNotSupportedException, IOException, ParseException; // BLOCKED and ADMIN
    void deleteUserByEmail() throws CloneNotSupportedException, IOException, ParseException; // Удалением Пользователя
    void editUserByEmail();
    void findUserByEmail() throws CloneNotSupportedException, IOException, ParseException;
    void getActiveUser() throws CloneNotSupportedException, IOException, ParseException;
    void getAllUsers() throws CloneNotSupportedException, IOException, ParseException;
    void getAllUserBooksByEmail() throws CloneNotSupportedException, IOException, ParseException;
    void logout() throws IOException;
    void returnLastMenu() throws CloneNotSupportedException, IOException, ParseException;
    void getMenuBooks() throws CloneNotSupportedException, IOException, ParseException;

}
