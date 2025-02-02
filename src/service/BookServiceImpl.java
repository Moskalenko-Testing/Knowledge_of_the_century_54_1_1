package service;

import model.Book;
import model.Role;
import repository.BookRepository;
import utils.MyArrayList;
import utils.MyList;

import java.util.Date;

public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final UserService userService;

    public BookServiceImpl(BookRepository bookRepository, UserService userService) {
        this.bookRepository = bookRepository;
        this.userService = userService;
    }

    @Override
    public boolean addBook(String title, String author, Date releaseDate) {
        if (userService.getActiveUser().getRole() == Role.ADMIN) { // Книгу может добавлять только АДМ
            bookRepository.saveBook(title, author, releaseDate);
            return true;
        }
        return false;
    }

    @Override
    public MyList<Book> getAllBooks() throws CloneNotSupportedException{
        return makeCloneList(bookRepository.getAllBooks());
    }

    @Override
    public Book getById(int id) throws CloneNotSupportedException {
        if (userService.getActiveUser().getRole() == Role.ADMIN) { // Get только у админ
            return bookRepository.getById(id);
        }
        return null;
    }


    @Override
    public MyList<Book> searchBookByTitle(String title) throws CloneNotSupportedException {
        MyList<Book> tempBooks = bookRepository.getAllBooks();
        MyList<Book> resultBooks = new MyArrayList<>();
        for (Book book : tempBooks) {
            if(book.getTitle().equals(title)){
                resultBooks.add(book);
            }
        }
        return makeCloneList(resultBooks);
    }

    @Override
    public MyList<Book> searchBookByAuthor(String author) throws CloneNotSupportedException{
        MyList<Book> tempBooks = bookRepository.getAllBooks();
        MyList<Book> resultBooks = new MyArrayList<>();
        for (Book book : tempBooks) {
            if(book.getAuthor().equals(author)){
                resultBooks.add(book);
            }
        }
        return makeCloneList(resultBooks);
    }


    @Override
    public Book returnBook(int id) throws CloneNotSupportedException {
        Book book = checkBookLocGlob(id);
        if(book instanceof Book){
            bookRepository.getById(id).setBorrowed(false);
            userService.getActiveUser().getUserBooks().remove(book);
            return book;
        }
            return null;
    }


    @Override
    public MyList<Book> getAvailableBooks() throws CloneNotSupportedException{
        MyList<Book> tempBooks = bookRepository.getAllBooks();
        MyList<Book> resultBooks = new MyArrayList<>();
        for (Book book : tempBooks) {
            if(!book.isBorrowed()){
                resultBooks.add(book);
            }
        }
        return makeCloneList(resultBooks);
    }

    @Override
    public MyList<Book> getBorrowedBooks() throws CloneNotSupportedException{
        MyList<Book> tempBooks = bookRepository.getAllBooks();
        MyList<Book> resultBooks = new MyArrayList<>();
        for (Book book : tempBooks) {
            if(book.isBorrowed() == true){
                resultBooks.add(book);
            }
        }
        return makeCloneList(resultBooks);
    }

    @Override
    public Book borrowBook(int id) throws CloneNotSupportedException {
            Role role = userService.getActiveUser().getRole();
            switch (role) {
                case ADMIN -> {
                    Book book = bookRepository.getById(id);
                    if(book instanceof Book && book.isBorrowed() == false) {
                        userService.getActiveUser().addUserBook(book);
                        bookRepository.getById(id).setBorrowed(true);
                        return book;
                    }
                    System.out.println("Книга с данным ID не доступна");
                    return null;
                }

                case USER -> {
                    Book borrowBook = bookRepository.getById(id);
                    if(borrowBook instanceof Book && borrowBook.isBorrowed() == false) {
                        Book cloneBook = (Book) borrowBook.clone();
                        bookRepository.getById(id).setBorrowed(true);
                        userService.getActiveUser().getUserBooks().add(cloneBook);
                        return cloneBook;
                    }
                    System.out.println("Книга с данным ID не доступна");
                    return null;
                }
            }
            return null;
    }

    @Override
    public Book deleteBook(int id) throws CloneNotSupportedException {
        Role role = userService.getActiveUser().getRole();
        if (role == Role.ADMIN) {
            Book book = bookRepository.getById(id);
            if(book instanceof Book && book.isBorrowed() == false) {
                bookRepository.deleteBook(id);
            }
            System.out.println("Книга с данным ID не доступна для удаления");
            return null;
        }
        return null;
    }

    @Override
    public Book reserveBookByID(int Id) throws CloneNotSupportedException {
        Role role = userService.getActiveUser().getRole();
        switch (role) {
            case ADMIN -> {
                Book book = bookRepository.getById(Id);
                if(book instanceof Book && book.isBorrowed() == true) {
                    userService.getActiveUser().getReservationBooks().add(book);
                }
                else {
                    System.out.println("Книга с данным ID не доступна для резервирования");
                    return null;
                }
            }
            case USER -> {
                Book book = bookRepository.getById(Id);
                if(book instanceof Book && book.isBorrowed() == true) {
                    Book cloneBook = (Book) book.clone();
                    userService.getActiveUser().getReservationBooks().add(cloneBook);
                    return cloneBook;
                }
                else {
                    System.out.println("Книга с данным ID не доступна для резервирования");
                    return null;
                }
            }
        }
        return null;
    }
    /*
    Метод сепарирования массива книг в зависимости от Роли User
     ADMIN -> return оригинал массива со ссылками на реальные книги
     USER -> Делаем глубокое копирование массива типа MyList<Book> и возвращаем только Клон
     BLOCKED -> null;

    * */

    private MyList<Book> makeCloneList(MyList<Book> books) throws CloneNotSupportedException {
        if (books != null) {
            Role role = userService.getActiveUser().getRole();
            switch (role) {
                case ADMIN -> {return books;}
                case USER -> {
                    MyList<Book> tempBooks = new MyArrayList<>();
                    for (Book book : books) {
                        Book cloneBook = (Book) book.clone();
                        tempBooks.add(cloneBook);

                    }
                    return tempBooks;
                }
            }
        }
        return null;
    }
    /*
    Проверяем корректность данных, что в локальном массиве User и глобальном репозитории
    есть книга с данным ID
    */
    private Book checkBookLocGlob (int id) throws CloneNotSupportedException {
        for(Book book : userService.getActiveUser().getUserBooks()){
            if(book.getId() == id && bookRepository.getById(id) != null){
                return book;
            }
        }return null;
    }
}
