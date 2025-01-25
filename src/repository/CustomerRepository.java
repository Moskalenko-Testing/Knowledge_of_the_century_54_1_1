package repository;

import model.Customer;

public interface CustomerRepository {
    Customer addCustomer(String email, String password);

    boolean isEmailExist(String email);

    Customer getUserByEmail(String email);

    boolean updatePassword(String email, String newPassword);
}
