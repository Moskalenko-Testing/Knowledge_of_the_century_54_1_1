package service;

import model.Book;
import model.User;
import utils.MyList;

public interface UserService {
    User registerCustomer(String email, String password);
    boolean loginCustomer(String email, String password);
    boolean logoutCustomer();
    boolean updatePassword(String email, String newPassword);
    User getCustomerByEmail(String email);
    User getActiveCustomer();
    boolean blockedCustomer(String email);
    MyList<Book> customerBooks(String email);
}
