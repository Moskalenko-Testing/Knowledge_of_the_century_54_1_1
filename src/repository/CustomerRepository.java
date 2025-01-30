package repository;

import model.User;

public interface CustomerRepository {
    User addCustomer(String email, String password);

    boolean isEmailExist(String email);

    User getCustomerByEmail(String email);

    boolean updatePassword(String email, String newPassword);
}
