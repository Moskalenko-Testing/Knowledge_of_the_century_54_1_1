package repository;

import model.User;

public interface UserRepository {
    User addCustomer(String email, String password);

    boolean isEmailExist(String email);

    User getUserByEmail(String email);

    boolean updatePassword(String email, String newPassword);
}
