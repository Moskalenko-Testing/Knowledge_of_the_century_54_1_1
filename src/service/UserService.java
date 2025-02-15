package service;

import model.Book;
import model.User;
import utils.MyList;

import java.io.IOException;

public interface UserService {
    User registerUser(String email, String password);
    boolean loginUser(String email, String password);
    boolean updatePassword(String email, String newPassword);
    User getUserByEmail(String email);
    User getActiveUser();
    boolean blockedUser(String email);
    boolean deleteUser(String email);
    MyList<Book> userBooks(String email);
    MyList<User> allAllUsers();
    boolean setActiveUser(User user);
    boolean logout() throws IOException;
}
