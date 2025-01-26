package service;

import model.Book;
import model.Customer;
import model.Role;
import repository.CustomerRepository;
import utils.MyList;
import utils.PersonValidition;

import java.util.Date;
import java.util.concurrent.Callable;

public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;
    private Customer activeCustomer;

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public Customer registerCustomer(String email, String password) {
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
        Customer customer = customerRepository.addCustomer(email, password);
        return customer;
    }

    @Override
    public boolean loginCustomer(String email, String password) {
        Customer tempCustomer = customerRepository.getCustomerByEmail(email);
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
    public Customer getCustomerByEmail(String email) {
        if(activeCustomer.getRole() == Role.ADMIN) {
            return customerRepository.getCustomerByEmail(email);
        }
        return null;
    }

    @Override
    public Customer getActiveCustomer() {
        return activeCustomer;
    }

    @Override
    public boolean blockedCustomer(String email) {
        if(activeCustomer.getRole() == Role.ADMIN) {
            Customer tempCustomer = customerRepository.getCustomerByEmail(email);
            if(tempCustomer != null) {
                tempCustomer.setRole(Role.BLOCKED);
                return true;
            }
        }
        return false;
    }
}
