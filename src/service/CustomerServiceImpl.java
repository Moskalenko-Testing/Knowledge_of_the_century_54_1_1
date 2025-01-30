package service;

import model.User;
import model.Role;
import repository.CustomerRepository;
import utils.PersonValidition;

public class CustomerServiceImpl implements UserService {
    private final CustomerRepository customerRepository;
    private User activeCustomer;

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public User registerCustomer(String email, String password) {
        if(activeCustomer.getRole()!= Role.ADMIN) {
            System.out.println("This operation is only available to Admin");
            return null;
        }
        if (!PersonValidition.isEmailValid(email)) {
            System.out.print("Email не прошел проверку!");
            return null;
        }

        if (!PersonValidition.isPasswordValid(password)) {
            System.out.println("Password не прошел проверку!");
            return null;
        }

        if (customerRepository.isEmailExist(email)) {
            System.out.println("Email already exists!");
            return null;
        }
        User customer = customerRepository.addCustomer(email, password);
        return customer;
    }

    @Override
    public boolean loginCustomer(String email, String password) {
        User tempCustomer = customerRepository.getCustomerByEmail(email);
        if (tempCustomer != null) {
            if (tempCustomer.getPassword().equals(password)) {
                this.activeCustomer = tempCustomer;
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean logoutCustomer() {
        this.activeCustomer = null;
        return true;
    }

    @Override
    public boolean updatePassword(String email, String newPassword) {
        if(activeCustomer.getRole() == Role.ADMIN || activeCustomer.getEmail().equals(email)) {
            return customerRepository.updatePassword(email, newPassword);
        }
        return false;
    }

    @Override
    public User getCustomerByEmail(String email) {
        if(activeCustomer.getRole() == Role.ADMIN) {
            return customerRepository.getCustomerByEmail(email);
        }
        return null;
    }

    @Override
    public User getActiveCustomer() {
        return activeCustomer;
    }

    @Override
    public boolean blockedCustomer(String email) {
        if(activeCustomer.getRole() == Role.ADMIN) {
            User tempCustomer = customerRepository.getCustomerByEmail(email);
            if(tempCustomer != null) {
                tempCustomer.setRole(Role.BLOCKED);
                return true;
            }
        }
        return false;
    }
}
