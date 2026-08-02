package mediavault;

import java.util.*;

/**
 * Represents a library of media entries.
 * <p>This class manages all operations that will be done on each mediavault.MediaEntry</p>
 *
 * @author Jimlor
 * @version 2.0
 */
public class Library {
    /** The list containing all media entries. */
    private ArrayList<MediaEntry> media;

    /**
     * Creates an empty Library instance.
     */
    public Library() {
        this.media = new ArrayList<MediaEntry>();
    }

    /**
     * Adds a media entry to this library.
     *
     * @param m the {@link MediaEntry} that will be added
     */
    public void addEntry(MediaEntry m) {
        this.media.add(m);
    }

    /**
     * Rates and reviews a media entry.
     * <p>Delegates the rating validation to the entry itself.</p>
     *
     * @param m      the {@link MediaEntry} to be rated
     * @param rating the numerical rating (0-10) given to the media entry
     * @param review the written review to attach
     */
    public void rateEntry(MediaEntry m, int rating, String review) {
        m.rate(rating, review);
    }

    /**
     * Updates the consumption status of a media entry.
     *
     * @param m         the {@link MediaEntry} object whose status is being modified
     * @param newStatus the new {@link Status} to transition to
     */
    public void updateProgress(MediaEntry m, Status newStatus) {
        m.updateStatus(newStatus);
    }

    /**
     * Deletes a media entry from this library.
     *
     * @param index the index of the media entry.
     * @throws IndexOutOfBoundsException if the index is less than 0 or greater than the list size
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
     *
     * @param index the index of the media entry to retrieve
     * @return the {@link MediaEntry} object found at the index, or null if out of bounds
     */
    public MediaEntry getEntry(int index) {
        if (index >= 0 && index < media.size()) {
            return media.get(index);
        }
        return null;
    }

    /**
     * Gets the number of entries inside this library.
     *
     * @return the current size of the media list
     */
    public int getSize() {
        return media.size();
    }

    /**
     * Retrieves the entire unfiltered list of media entries.
     *
     * @return a List containing all {@link MediaEntry} objects
     */
    public List<MediaEntry> getAllMedia() {
        return this.media;
    }

    /**
     * Filters the library to return only entries matching a specific status.
     *
     * @param status the {@link Status} to filter by
     * @return a List of {@link MediaEntry} objects that match the requested status
     */
    public List<MediaEntry> getMediaByStatus(Status status) {
        List<MediaEntry> filtered = new ArrayList<>();

        for (MediaEntry m : media) {
            if (m.getCurrentStatus() == status) {
                filtered.add(m);
            }
        }

        return filtered;
    }

    /**
     * Filters the library to return only entries of a specific media type (class).
     *
     * @param mediaClass the Class type to filter by (e.g., Book.class, Movie.class)
     * @return a List of {@link MediaEntry} objects that are instances of the requested class
     */
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