package service;

import model.User;

public interface UserService {
    User registerUser(String email, String password);
    boolean loginUser(String email, String password);
    boolean logoutUser();
    boolean updatePassword(String email, String newPassword);
    User getCustomerByEmail(String email);
    User getActiveCustomer();
    boolean blockedCustomer(String email);
}
