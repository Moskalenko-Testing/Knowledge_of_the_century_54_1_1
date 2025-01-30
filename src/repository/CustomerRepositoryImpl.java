package repository;

import model.User;

public class CustomerRepositoryImpl implements CustomerRepository {
    @Override
    public User addCustomer(String email, String password) {
        return null;
    }

    @Override
    public boolean isEmailExist(String email) {
        return false;
    }

    @Override
    public User getCustomerByEmail(String email) {
        return null;
    }

    @Override
    public boolean updatePassword(String email, String newPassword) {
        return false;
    }
}
