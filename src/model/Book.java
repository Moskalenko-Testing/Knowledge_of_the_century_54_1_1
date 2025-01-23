package model;

import java.util.Date;

public class Book {
    public static int id;
    private String title;
    private String author;
    private Date releaseDate;
    private Date returnDate;
    private String nameOfCustomer;
    // private Customer bookCustomer;(Возможно мы будем использовать сущность связь для сильной связи с книгой )


    public Book(String title, String author, Date releaseDate) {
        this.id++;
        this.title = title;
        this.author = author;
        this.releaseDate = releaseDate;
    }

    public int getId() {
        return id;
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

    public String getNameOfCustomer() {
        return nameOfCustomer;
    }

    public void setNameOfCustomer(String nameOfCustomer) {
        this.nameOfCustomer = nameOfCustomer;
    }

    @Override
    public String toString() {
        return "Book:{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", releaseDate=" + releaseDate +
                ", returnDate=" + returnDate +
                ", nameOfCustomer='" + nameOfCustomer + '\'' +
                '}';
    }
}
