package repository;

import model.Role;
import model.User;
import utils.MyArrayList;
import utils.MyList;
import utils.PersonValidition;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;

public class UserRepositoryImpl implements UserRepository {
    private final MyList<User> users;

    public UserRepositoryImpl() throws IOException, ParseException {
        users = new MyArrayList<>();
        addSuperAdmin();
        addTestUser();
    }
    private void addSuperAdmin() {
        User superUser = new User("SuperEmail", "SuperPassword");
        superUser.setRole(Role.ADMIN);
        users.add(superUser);
    }
    private void addTestUser() throws IOException, ParseException {
        String row;
        BufferedReader reader = new BufferedReader(new FileReader("users.csv"));
        while ((row = reader.readLine()) != null) {
            String[] fields = row.split(";");
            String email = fields[0];
            String password = fields[1];
            User user = new User(email, password);
            Role role = Role.valueOf(fields[2]);
            user.setRole(role);
            users.add(user);
        }
        reader.close();
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
        if (user != null && PersonValidition.isPasswordValid(newPassword)) {
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
