import java.util.*;

/**
 * Represents a library of media entries.
 * <p>This class manages all operations that will be done on each MediaEntry</p>
 *
 * @author Raphael
 * @version 1.3
 */
public class Library {
    /** The list containing all media entries. */
    private ArrayList<MediaEntry> media;

    /**
     * Creates an empty Library.
     */
    public Library() {
        this.media = new ArrayList<MediaEntry>();
    }

    /**
     * Adds a media entry to this component.
     *
     * @param m the <code>MediaEntry</code> that will be added.
     */
    public void addEntry(MediaEntry m) {
        this.media.add(m);
    }

    /**
     * Rates a media entry from 0-10.
     *
     * <p>
     *     <b>Preconditions: </b>
     *     <ul>
     *         <li> The media entry <code>m</code> must be designated as Completed before it can be rated.</li>
     *         <li> The <code>rating</code> must be between 0-10 inclusive.</li>
     *     </ul>
     * </p>
     *
     * @param m the <code>MediaEntry</code> to be rated.
     * @param rating the numerical rating (0-10) given to the media entry.
     * @param sc the <code>Scanner</code> object used to read and validate user input.
     */
    public void rateEntry(MediaEntry m, int rating, String review) {
        if (m.currentStatus != Status.COMPLETED) {
            throw new IllegalArgumentException("You can only rate a completed media. Finish it first then update its status!")
        }

        if (rating < 0 || rating > 10) {
            throw new IllegalArgumentException("Rating must be between 0 and 10.");
        }

        m.setRating(rating);
        m.setReview(review);
    }

    /**
     * Updates the consumption status of a media entry based on user menu input.
     * <p>
     * Reverting an entry back to Planned or In Progress will automatically reset its
     * user rating to 0 and its review to <code>null</code>.
     * </p>
     *
     * <p>
     *     <b>Preconditions:</b>
     *     <ul>
     *         <li>The parameter <code>m</code> must not be null.</li>
     *         <li>The <code>Scanner sc</code> must be an initialized, open input stream.</li>
     *     </ul>
     * </p>
     *
     * @param m  the <code>MediaEntry</code> object whose status is being modified
     * @param sc the <code>Scanner</code> object used to read the user's menu choice
     */
    public void updateProgress(MediaEntry m, Status newStatus) {
        m.setCurrentStatus(newStatus);

        if (newStatus == Status.PLANNED || newStatus == Status.IN_PROGRESS) {
            m.setRating(0);
            m.setReview(null);
        }
    }

    /**
     * Deletes a media entry from this component.
     *
     * @param index the index of the media entry.
     */
    public void deleteEntry(int index) {
        if (index >= 0 && index < media.size()) {
            this.media.remove(index);
        } else {
            throw new IndexOutOfBoundsException("Invalid entry number.");
        }
    }

    /**
     * Retrieves a media entry from the library at the specified index position.
     * <p>
     * This method safely checks if the requested index is within the valid boundaries
     * of the <code>media</code> collection before attempting retrieval.
     * </p>
     *
     * <p>
     *     <b>Precondition:</b>
     *     <ul>
     *         <li>The <code>media</code> collection must be initialized (not null).</li>
     *     </ul>
     * </p>
     *
     * @param index the zero-based position index of the media entry to retrieve
     * @return the <code>MediaEntry</code> object found at the specified index,
     *         or <code>null</code> if the index is out of bounds
     */
    public MediaEntry getEntry(int index) {
        if (index >= 0 && index < media.size()) {
            return media.get(index);
        }
        return null;
    }

    /**
     * Gets the number of entries inside this component.
     *
     * @return the size of <code>ArrayList media</code>.
     */
    public int getSize() {
        return media.size();
    }

    public List<MediaEntry> getAllMedia() {
        return this.media;
    }

    public List<MediaEntry> getMediaByStatus(Status status) {
        List<MediaEntry> filtered = new ArrayList<>();

        for (MediaEntry m : media) {
            if (m.getCurrentStatus() == status) {
                filtered.add(m);
            }
        }

        return filtered;
    }

    public List<MediaEntry> getMediaByType(Class<?> mediaClass) {
        List<MediaEntry> filtered = new ArrayList<>();

        for (MediaEntry m : media) {
            if (mediaClass.isInstance(m)) {
                filtered.add(m);
            }
        }
        
        return filtered;
    }
}