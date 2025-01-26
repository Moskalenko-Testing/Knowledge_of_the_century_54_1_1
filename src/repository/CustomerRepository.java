package repository;

import model.Customer;

public interface CustomerRepository {
    Customer addCustomer(String email, String password);

    boolean isEmailExist(String email);

    Customer getCustomerByEmail(String email);

    boolean updatePassword(String email, String newPassword);
}
