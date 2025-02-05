package view;

public interface MenuUser {

    void updatePassword() throws CloneNotSupportedException;
    void deleteAccount() throws CloneNotSupportedException;
    void showMenuUserBooks() throws CloneNotSupportedException;
    void logoutUser();
    void returnLastMenu() throws CloneNotSupportedException;

}
