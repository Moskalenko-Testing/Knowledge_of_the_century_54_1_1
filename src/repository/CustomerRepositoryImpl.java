package repository;

import model.Customer;

public class CustomerRepositoryImpl implements CustomerRepository {
    @Override
    public Customer addCustomer(String email, String password) {
        return null;
    }

    @Override
    public boolean isEmailExist(String email) {
        return false;
    }

    @Override
    public Customer getUserByEmail(String email) {
        return null;
    }

    @Override
    public boolean updatePassword(String email, String newPassword) {
        return false;
    }
}
