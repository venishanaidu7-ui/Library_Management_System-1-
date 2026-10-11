package library.model;

public class Book {

    private int bookId;
    private String bookName;
    private String author;
    private String category;
    private String publisher;
    private int quantity;
    private int availableQuantity;

    public Book() {
    }

    public Book(String bookName, String author, String category,
                String publisher, int quantity, int availableQuantity) {

        this.bookName = bookName;
        this.author = author;
        this.category = category;
        this.publisher = publisher;
        this.quantity = quantity;
        this.availableQuantity = availableQuantity;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getAvailableQuantity() {
        return availableQuantity;
    }

    public void setAvailableQuantity(int availableQuantity) {
        this.availableQuantity = availableQuantity;
    }
}