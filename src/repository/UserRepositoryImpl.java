package repository;

import model.Role;
import model.User;

import java.util.ArrayList;
import java.util.List;

public class UserRepositoryImpl implements UserRepository {
    private final List<User> users = new ArrayList<>();
    @Override
    public User addCustomer(String email, String password) {
        if (isEmailExist(email)) {
            System.out.println("Пользователь с таким email уже существует: " + email);
        return null;
    }
        User user = new User(email, password);
        user.setRole(Role.USER);
        users.add(user);
        return user;
    }

    @Override
    public boolean isEmailExist(String email) {
        for (User user : users) {
            if (user.getEmail().equalsIgnoreCase(email)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public User getUserByEmail(String email) {
        for (User user : users) {
            if (user.getEmail().equalsIgnoreCase(email)) {
                return user;
            }
        }
        return null;
    }

    @Override
    public boolean updatePassword(String email, String newPassword) {
        User user = getUserByEmail(email);
        if (user != null) {
            user.setPassword(newPassword);
            return true;
        }
        return false;
    }
}
