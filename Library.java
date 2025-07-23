
import java.util.LinkedList;

public class Library {
    private LinkedList<Book> books;

    public Library() {
        books = new LinkedList<>();
    }

    public void addBook(Book book) {
        books.add(book);
    }

    public String displayBooks() {
        if (books.isEmpty()) {
            return "No books in the library.";
        }

        StringBuilder sb = new StringBuilder();
        for (Book book : books) {
            sb.append(book.toString()).append("\n");
        }
        return sb.toString();
    }

    public boolean borrowBook(String isbn, Borrower borrower) {
        for (Book book : books) {
            if (book.getIsbn().equals(isbn)) {
                if (!book.isBorrowed()) {
                    book.borrowBook(borrower);
                    return true;
                }
                return false;
            }
        }
        return false;
    }

    public boolean returnBook(String isbn) {
        for (Book book : books) {
            if (book.getIsbn().equals(isbn)) {
                if (book.isBorrowed()) {
                    book.returnBook();
                    return true;
                }
                return false;
            }
        }
        return false;
    }

    public String getBorrowedBooks() {
        StringBuilder sb = new StringBuilder();
        boolean found = false;

        for (Book book : books) {
            if (book.isBorrowed()) {
                sb.append(book.toString()).append("\n");
                found = true;
            }
        }

        return found ? sb.toString() : "No books are currently borrowed.";
    }

    public String searchBook(String query) {
        StringBuilder sb = new StringBuilder();
        boolean found = false;

        for (Book book : books) {
            if (book.getTitle().toLowerCase().contains(query.toLowerCase()) ||
                    book.getAuthor().toLowerCase().contains(query.toLowerCase()) ||
                    book.getIsbn().toLowerCase().contains(query.toLowerCase())) {
                sb.append(book.toString()).append("\n");
                found = true;
            }
        }

        return found ? sb.toString() : "No books found matching your search.";
    }
}
