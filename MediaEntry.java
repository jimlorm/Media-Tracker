/**
 * Represents an abstract base class for any media entry in the library.
 * <p>This class contains the common attributes and methods shared across all media types.</p>
 * @author Rapha
 * @version 1.3
 */
public abstract class MediaEntry {
    /** The title of the media entry. */
    protected String title;
    /** The genre of the media entry. */
    protected String genre;
    /** The current consumption status of the media entry. */
    protected Status currentStatus;
    /** The user's personal rating of the media entry (0-10). */
    protected int rating;
    /** The user's personal review of the media entry. */
    protected String review;

    /**
     * Constructs a new MediaEntry with the specified details.
     *
     * @param title         the title of the media
     * @param genre         the genre of the media
     * @param currentStatus the initial status (e.g., Planned, In Progress)
     */
    public MediaEntry(String title, String genre, Status currentStatus) {
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
    public Status getCurrentStatus() {
        return this.currentStatus;
    }

    /**
     * Sets the consumption status of the media entry.
     * @param newStatus the new status to be assigned
     */
    public void setCurrentStatus(Status newStatus) {
        this.currentStatus = newStatus;
    }

    /**
     * Updates the consumption status of this entry.
     * @param newStatus the new status to transition to
     */
    public void updateStatus(Status newStatus) {
        this.currentStatus = newStatus;

        if (newStatus == Status.PLANNED || newStatus == Status.IN_PROGRESS) {
            this.rating = 0;
            this.review = null;
        }
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
        if (rating < 0 || rating > 10) {
            throw new IllegalArgumentException("Rating must be between 0 and 10.");
        }
        this.rating = rating;
    }

    /**
     * Rates and reviews this media entry.
     * @param rating the numerical rating (0-10) given to the media entry
     * @param review the written review to attach
     */
    public void rate(int rating, String review) {
        if (currentStatus != Status.COMPLETED) {
            throw new IllegalStateException("You can only rate a completed media. Finish it first then update its status!");
        }

        setRating(rating);
        setReview(review);
    }

    /**
     * Retrieves the specific formatted details of the media entry.
     * To be implemented by concrete subclasses.
     * 
     * @return a formatted string containing the entry's details
     */
    public abstract String getDetails();
}