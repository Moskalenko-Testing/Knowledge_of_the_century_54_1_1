package service;

import model.Book;
import model.User;
import model.Role;
import repository.BookRepository;
import repository.BookRepositoryImpl;
import repository.UserRepository;
import utils.MyList;
import utils.PersonValidition;

import java.io.FileWriter;
import java.io.IOException;

public class UserServiceImpl implements UserService {
    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    private User activeUser;

    public UserServiceImpl(UserRepository userRepository, BookRepository bookRepository) {
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
        this.activeUser = null;
    }

    @Override
    public User registerUser(String email, String password) {
//        if(activeUser.getRole()!= Role.ADMIN) {
//            System.out.println("This operation is only available to Admin");
//            return null;
//        }
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
        User user = userRepository.addUser(email, password);
        if (this.setActiveUser(user)){
            return user;
        }
        return null;
    }

    @Override
    public boolean loginUser(String email, String password) {
        User tempUser = userRepository.getUserByEmail(email);
        if (tempUser != null) {
            if (tempUser.getPassword().equals(password)) {
                this.activeUser = tempUser;
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean updatePassword(String email, String newPassword) {
        if(activeUser.getRole() == Role.ADMIN || activeUser.getEmail().equals(email)) {
            return userRepository.updatePassword(email, newPassword);
        }
        return false;
    }

    @Override
    public User getUserByEmail(String email) {
        if(activeUser.getRole() == Role.ADMIN) {
            return userRepository.getUserByEmail(email);
        }
        return null;
    }

    @Override
    public User getActiveUser() {
        return activeUser;
    }

    @Override
    public MyList<Book> userBooks(String email) {
        return activeUser.getUserBooks();
    }

    @Override
    public boolean logout() throws IOException {
        FileWriter fileWriter = new FileWriter("users.csv", true);
        for (User user : userRepository.getAllUsers()) {
            String [] newUserArray = new String[] {user.getEmail(),
                    user.getPassword(),
                    user.getRole().toString()
            };
            if (!user.getEmail().equals("SuperEmail")) {
                String newLine = String.join(";", newUserArray);
                newLine = newLine + "\n";
                fileWriter.write(newLine);
            }
        }
        fileWriter.close();
        return true;
    }

    @Override
    public boolean deleteUser(String email) {
        User tempUser = userRepository.getUserByEmail(email);
        if(tempUser != null) {
            MyList<Book> tempUserBooks = tempUser.getUserBooks();
            for(Book tempBook : tempUserBooks) {
                Book returnBook = bookRepository.getById(tempBook.getId());
                returnBook.setBorrowed(false);
            }
            userRepository.getAllUsers().remove(tempUser);
            return true;
        }
        return false;
    }

    @Override
    public boolean blockedUser(String email) {
        if(activeUser.getRole() == Role.ADMIN) {
            User tempUser = userRepository.getUserByEmail(email);
            if(tempUser != null) {
                tempUser.setRole(Role.BLOCKED);
                return true;
            }
        }
        return false;
    }

    @Override
    public MyList<User> allAllUsers() {
        if(activeUser.getRole() == Role.ADMIN) {
            return userRepository.getAllUsers();
        }
        return null;
    }

    @Override
    public boolean setActiveUser(User user) {
        if(user instanceof User) {
            this.activeUser = user;
            return true;
        }
        return false;
    }
}
