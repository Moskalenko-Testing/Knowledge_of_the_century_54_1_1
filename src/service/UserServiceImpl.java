package service;

import model.Book;
import model.User;
import model.Role;
import repository.UserRepository;
import utils.MyList;
import utils.PersonValidition;

public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private User activeCustomer;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
        User tempCustomer = new User("TestEmail", "TestPassword");
        tempCustomer.setRole(Role.ADMIN);
        activeCustomer = tempCustomer;
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

        if (userRepository.isEmailExist(email)) {
            System.out.println("Email already exists!");
            return null;
        }
        User customer = userRepository.addUser(email, password);
        return customer;
    }

    @Override
    public boolean loginCustomer(String email, String password) {
        User tempCustomer = userRepository.getUserByEmail(email);
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
            return userRepository.updatePassword(email, newPassword);
        }
        return false;
    }

    @Override
    public User getCustomerByEmail(String email) {
        if(activeCustomer.getRole() == Role.ADMIN) {
            return userRepository.getUserByEmail(email);
        }
        return null;
    }

    @Override
    public User getActiveCustomer() {
        return activeCustomer;
    }

    @Override
    public MyList<Book> customerBooks(String email) {
        return activeCustomer.getCustomerBooks();
    }

    @Override
    public boolean blockedCustomer(String email) {
        if(activeCustomer.getRole() == Role.ADMIN) {
            User tempCustomer = userRepository.getUserByEmail(email);
            if(tempCustomer != null) {
                tempCustomer.setRole(Role.BLOCKED);
                return true;
            }
        }
        return false;
    }
}
