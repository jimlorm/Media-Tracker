package mediavault;

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
    public Book(String title, String genre, Status status, String author, int pageCount) {
        super(title, genre, status);
        this.author = author;
        setPageCount(pageCount);
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
     *
     * @param pageCount the new page count
     * @throws IllegalArgumentException if the page count provided is negative
     */
    public void setPageCount(int pageCount) {
        if (pageCount < 0) {
            throw new IllegalArgumentException("Page count cannot be negative.");
        }
        
        this.pageCount = pageCount;
    }

    /**
     * Retrieves the formatted details of the book.
     *
     * @return a string containing the book's information
     */
    @Override
    public String getDetails() {
        return "[Book] " + title + "\n" +
               "Author: " + author + "\n" +
               "Genre: " + genre + " | Status: " + currentStatus + "\n" +
               "Length: " + pageCount + " pages";
    }
}