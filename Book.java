/**
 * Represents a standalone book entry in the library.
 * <p>Extends the {@link MediaEntry} class with book-specific attributes.</p>
 * 
 * @author Jimlor
 * @version 1.2
 */
public class Book extends MediaEntry {
    /** The author of the book. */
    private String author;
    /** The total number of pages in the book. */
    private int pageCount;

    /**
     * Constructs a new Book entry.
     *
     * @param title     the title of the book
     * @param genre     the genre of the book
     * @param status    the current consumption status
     * @param author    the author of the book
     * @param pageCount the total number of pages
     */
    public Book(String title, String genre, String status, String author, int pageCount) {
        super(title, genre, status);
        this.author = author;
        this.pageCount = pageCount;
    }

    /**
     * Gets the author of the book.
     * @return the author's name
     */
    public String getAuthor() { 
        return author; 
    }

    /**
     * Sets the author of the book.
     * @param author the new author name
     */
    public void setAuthor(String author) {
        this.author = author; 
    }

    /**
     * Gets the total page count of the book.
     * @return the number of pages
     */
    public int getPageCount() { 
        return pageCount; 
    }

    /**
     * Sets the page count of the book.
     * @param pageCount the new page count
     */
    public void setPageCount(int pageCount) {
        this.pageCount = pageCount; 
    }

    /**
     * Displays the core details of the book to the console.
     */
    @Override
    public void displayDetails() {
        System.out.println("[Book] " + getTitle());
        System.out.println("Author: " + author);
        System.out.println("Genre: " + getGenre() + " | Status: " + getCurrentStatus());
        System.out.println("Length: " + pageCount + " pages");
    }
}