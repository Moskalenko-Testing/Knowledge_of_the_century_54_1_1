package view;

public interface MenuAdmin {

    void setNewUserRoleByEmail () throws CloneNotSupportedException; // BLOCKED and ADMIN
    void deleteUserByEmail() throws CloneNotSupportedException; // Удалением Пользователя
    void editUserByEmail();
    void findUserByEmail() throws CloneNotSupportedException;
    void getActiveUser() throws CloneNotSupportedException;
    void getAllUsers() throws CloneNotSupportedException;
    void getAllUserBooksByEmail() throws CloneNotSupportedException;
    void logout();
    void returnLastMenu() throws CloneNotSupportedException;
    void getMenuBooks() throws CloneNotSupportedException;

}
