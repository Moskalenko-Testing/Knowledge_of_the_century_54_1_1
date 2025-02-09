package model;

import java.util.Date;
import java.util.HashMap;
import java.util.Scanner;

public class Book implements Cloneable {
    private int id;
    private String title;
    private String author;
    private Date releaseDate;
    private Date returnDate;
    private boolean isBorrowed;
    private String userBookEmail;


    public Book(int id, String title, String author, Date releaseDate) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.releaseDate = releaseDate;
        this.returnDate = returnDate;
        this.isBorrowed = isBorrowed;
        this.userBookEmail = userBookEmail;
    }

    public boolean isBorrowed() {
        return isBorrowed;
    }

    public void setBorrowed(boolean borrowed) {
        isBorrowed = borrowed;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    public String getUserBookEmail() {
        return userBookEmail;
    }

    public void setUserBookEmail(String userBookEmail) {
        this.userBookEmail = userBookEmail;
    }

    public Book clone() throws CloneNotSupportedException {
        return (Book) super.clone();
    }


    @Override
    public String toString() {
        return "Book:{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", releaseDate=" + (releaseDate.getYear() + 1900) +
                ", returnDate=" + returnDate +
                ", isBorrowed=" + isBorrowed +
                ", userBookEmail='" + userBookEmail + '\'' +
                '}';
    }

}