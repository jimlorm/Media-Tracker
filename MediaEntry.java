/**
 * Represents an abstract base class for any media entry in the library.
 * <p>This class contains the common attributes and methods shared across all media types.</p>
 * @author Rapha
 * @version 1.3
 */
public abstract class MediaEntry {
    /** The title of the media entry. */
    private String title;
    /** The genre of the media entry. */
    private String genre;
    /** The current consumption status of the media entry. */
    private String currentStatus;
    /** The user's personal rating of the media entry (0-10). */
    private int rating;
    /** The user's personal review of the media entry. */
    private String review;
    
    /** The predefined valid statuses for any media entry. */
    public static final String[] STATUSES = {"Planned","In Progress","Completed"};

    /**
     * Constructs a new MediaEntry with the specified details.
     *
     * @param title         the title of the media
     * @param genre         the genre of the media
     * @param currentStatus the initial status (e.g., Planned, In Progress)
     */
    public MediaEntry(String title, String genre, String currentStatus) {
        this.title = title;
        this.genre = genre;
        this.currentStatus = currentStatus;
    }

    /**
     * Gets the title of the media entry.
     * @return the title
     */
    public String getTitle() {
        return this.title;
    }

    /**
     * Gets the user's rating for the media entry.
     * @return the rating from 0 to 10
     */
    public int getRating() {
        return rating;
    }

    /**
     * Gets the user's written review for the media entry.
     * @return the written review, or null if not provided
     */
    public String getReview() {
        return review;
    }

    /**
     * Gets the genre of the media entry.
     * @return the genre
     */
    public String getGenre() {
        return genre;
    }

    /**
     * Gets the current consumption status of the media entry.
     * @return the current status string
     */
    public String getCurrentStatus() {
        return this.currentStatus;
    }

    /**
     * Sets the consumption status of the media entry.
     * @param newStatus the new status to be assigned
     */
    public void setCurrentStatus(String newStatus) {
        this.currentStatus = newStatus;
    }

    /**
     * Sets the user's review for the media entry.
     * @param review the review text
     */
    public void setReview(String review) {
        this.review = review;
    }

    /**
     * Sets the user's numerical rating for the media entry.
     * @param rating the rating score from 0 to 10
     */
    public void setRating(int rating) {
        this.rating = rating;
    }

    /**
     * Displays the specific details of the media entry. 
     * To be implemented by concrete subclasses.
     */
    public abstract void displayDetails();
}