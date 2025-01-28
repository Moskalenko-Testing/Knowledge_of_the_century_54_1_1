package model;

import java.util.ArrayList;

public class User {
    private String name;
    private String email;
    private String password;
    private Role role;
    ArrayList<Book> customerBooks = new ArrayList<>();

    public User(String name, String email) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = Role.USER;
        this.customerBooks = new ArrayList<>();
    }


    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
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

    public ArrayList<Book> getCustomerBooks() {
        return customerBooks;
    }

    public void addCustomerBook(Book book) {
        this.customerBooks.add(book);
    }

    @Override
    public String toString() {
        return "Customer: {" +
                "password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
