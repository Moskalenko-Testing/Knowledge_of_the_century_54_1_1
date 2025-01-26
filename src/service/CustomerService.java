package service;

import model.Customer;

public interface CustomerService {
    Customer registerCustomer(String email, String password);
    boolean loginCustomer(String email, String password);
    boolean logoutCustomer();
    boolean updatePassword(String email, String newPassword);
    Customer getCustomerByEmail(String email);
    Customer getActiveCustomer();
    boolean blockedCustomer(String email);
}
