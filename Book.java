public class Book {
    private String title;
    private String author;
    private String isbn;
    private boolean isBorrowed;
    private Borrower borrower;

    public Book(String title, String author, String isbn) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.isBorrowed = false;
        this.borrower = null;
    }

    // Getters and setters
    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getIsbn() {
        return isbn;
    }

    public boolean isBorrowed() {
        return isBorrowed;
    }

    public Borrower getBorrower() {
        return borrower;
    }

    public void borrowBook(Borrower borrower) {
        this.isBorrowed = true;
        this.borrower = borrower;
    }

    public void returnBook() {
        this.isBorrowed = false;
        this.borrower = null;
    }

    @Override
    public String toString() {
        return "Title: " + title + ", Author: " + author + ", ISBN: " + isbn +
                ", Status: " + (isBorrowed ? "Borrowed by " + borrower.getName() : "Available");
    }
}