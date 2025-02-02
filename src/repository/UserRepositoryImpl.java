package repository;

import model.Role;
import model.User;
import utils.MyArrayList;
import utils.MyList;

public class UserRepositoryImpl implements UserRepository {
    private final MyList<User> users;

    public UserRepositoryImpl() {
        users = new MyArrayList<>();
        addSuperAdmin();
    }
    private void addSuperAdmin() {
        User superUser = new User("SuperEmail", "SuperPassword");
        superUser.setRole(Role.ADMIN);
        users.add(superUser);
        User user = new User("U", "U");
        user.setRole(Role.USER);
        users.add(user);
        User blockedUser = new User("B", "B");
        blockedUser.setRole(Role.BLOCKED);
        users.add(blockedUser);
    }


    @Override
    public User addUser(String email, String password) {
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

    @Override
    public MyList<User> getAllUsers() {
        return this.users;
    }
}
