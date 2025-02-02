package model;

import utils.MyArrayList;
import utils.MyList;

public class User {
    private String name;
    private String email;
    private String password;
    private Role role;
    MyList<Book> userBooks;
    MyList<Book> reservationBooks;

    public User(String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.userBooks = new MyArrayList<>();
        this.reservationBooks = new MyArrayList<>();
        this.role = Role.USER;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public MyList<Book> getUserBooks() {
        return userBooks;
    }

    public void addUserBook(Book book) {
        this.userBooks.add(book);
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public MyList<Book> getReservationBooks() {
        return reservationBooks;
    }


    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", role=" + role +
                '}';
    }
}
