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
    public void rateEntry(MediaEntry m, int rating, Scanner sc) {
        if (m.getCurrentStatus().equals(MediaEntry.STATUSES[2])) {
            // input validation for out of bounds rating
            while (rating > 10 || rating < 0) {
                System.out.println("Error: Ratings are only from 0 - 10 stars! Try again.");
                System.out.print("Enter rating (0-10 stars): ");
                rating = sc.nextInt();
                sc.nextLine();
            }
            m.setRating(rating);
            System.out.println("You've rated " + m.getTitle() + " " + rating + " stars!");

            System.out.print("Write a review? (y/n): ");
            char choice = sc.next().toLowerCase().charAt(0);
            sc.nextLine();

            if (choice == 'y') {
                System.out.println("What did you think about " + m.getTitle() + "? (Press ENTER to submit)");
                String review = sc.nextLine();
                m.setReview(review);
                System.out.println("Rating and Review successfully added!");
            }
        } 
        else {
            System.out.println("You haven't finished this yet. Rate it when you do!");
        }
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
    public void updateProgress(MediaEntry m, Scanner sc) {
        System.out.println("Update status to:");
        for (int i = 0; i < MediaEntry.STATUSES.length; i++)
            System.out.println("["+ (i+1) +"] " + MediaEntry.STATUSES[i]);
        System.out.print("Enter: ");
        int status = sc.nextInt() - 1; 
        sc.nextLine();

        while (status > 2 || status < 0) {
            System.out.println("Error: Input out of bounds. Try again: ");
            System.out.print("Enter: ");
            status = sc.nextInt() - 1;
            sc.nextLine();
        }

        switch (status) {
            case 0:
                m.setCurrentStatus(MediaEntry.STATUSES[0]);
                m.setRating(0);
                m.setReview(null);
                break;
            case 1:
                m.setCurrentStatus(MediaEntry.STATUSES[1]);
                m.setRating(0);
                m.setReview(null);
                break;
            case 2:
                m.setCurrentStatus(MediaEntry.STATUSES[2]);
                break;
        }
    }

    /**
     * Deletes a media entry from this component.
     *
     * @param index the index of the media entry.
     */
    public void deleteEntry(int index) {
        this.media.remove(index);
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

    /**
     * Prints the formatted, complete details of a specific media entry to the console.
     *     <b>Precondition:</b>
     *     <ul>
     *         <li>The parameter <code>m</code> must not be null.</li>
     *     </ul>
     * </p>
     *
     * @param m the <code>MediaEntry</code> object whose information is to be displayed
     */
    public void displayEntry(MediaEntry m) {
        m.displayDetails();
        
        if (m.getCurrentStatus().equals(MediaEntry.STATUSES[2])) {
            System.out.println("Your Rating: " + m.getRating() + "/10");
            if (m.getReview() != null && !m.getReview().trim().isEmpty()) {
                System.out.println("Your Review: " + m.getReview());
            } else {
                System.out.println("Your Review: (No review provided)");
            }
        } else {
            System.out.println("Rating: N/A (Finish it first!)");
            System.out.println("Review: N/A");
        }
    }

    /**
     * Displays all media entries currently stored in the library.
     * <p>
     * This method iterates through the entire library collection and prints each item's
     * title alongside its current consumption status.
     * </p>
     *
     * <p>
     *     <b>Precondition:</b>
     *     <ul>
     *         <li>The <code>media</code> collection must be initialized (not null).</li>
     *     </ul>
     * </p>
     */
    public void displayLibrary() {
        if (!media.isEmpty())
            for(int i = 0; i < this.media.size(); i++)
                System.out.println("[" + (i+1) + "]" + this.media.get(i).getTitle() + ": " + this.media.get(i).getCurrentStatus());
        else
            System.out.println("Your library is empty!");
    }

    /**
     * Displays a filtered list of media entries matching a specific status.
     * <p>
     * This method checks the status string of every media item in the library and displays
     * only those that match the exact value passed in the <code>status</code> parameter.
     * </p>
     *
     * <p>
     *     <b>Preconditions:</b>
     *     <ul>
     *         <li>The <code>media</code> collection must be initialized (not null).</li>
     *         <li>The <code>status</code> parameter should match one of the predefined
     *             status constants (e.g., <code>MediaEntry.STATUSES</code>).</li>
     *     </ul>
     * </p>
     *
     * @param status the exact status text to filter the library entries by
     */
    public void displayLibrary(String status) {
        if (!media.isEmpty()) {
            ArrayList<MediaEntry> filtered = new ArrayList<>();

            for (MediaEntry mediaEntry : this.media) {
                if (mediaEntry.getCurrentStatus().equals(status)) {
                    filtered.add(mediaEntry);
                }
            }

            System.out.println("Filter by: " + status);
            if(filtered.isEmpty())
                System.out.println("No matching items found.");
            else
                for(int j = 0; j < filtered.size(); j++) {
                    System.out.println((j+1) + ". " + filtered.get(j).getTitle());
            }
            System.out.println("\nTotal Items: " + filtered.size());
        }
        else
            System.out.println("Your library is empty!");
    }

    /**
     * Displays a filtered list of media entries matching the exact class type of the provided object.
     * <p>
     * This method checks the runtime class of the passed <code>m</code> parameter
     * and prints only the media entries in the library that share the identical subclass type
     * (e.g., only <code>Book</code> objects, or only <code>Movie</code> objects).
     * </p>
     *
     * <p>
     *     <b>Preconditions:</b>
     *     <ul>
     *         <li>The <code>media</code> collection must be initialized (not null).</li>
     *         <li>The parameter <code>m</code> must not be null.</li>
     *     </ul>
     * </p>
     *
     * @param m an instance of a <code>MediaEntry</code> subclass used to determine the class type to filter by
     */
    public void displayLibrary(MediaEntry m) {
        if (!media.isEmpty()) {
            ArrayList<MediaEntry> filtered = new ArrayList<>();

            for (MediaEntry mediaEntry : this.media) {
                if (mediaEntry.getClass() == m.getClass()) {
                    filtered.add(mediaEntry);
                }
            }

            System.out.println("Filter by: " + m.getClass().getSimpleName());
            if(filtered.isEmpty()) {
                System.out.println("No matching items found.");
            }
            else {
                for (int j = 0; j < filtered.size(); j++) {
                    System.out.println((j + 1) + ". " + filtered.get(j).getTitle());
                }
                System.out.println("\nTotal Items: " + filtered.size());
            }
        }
        else
            System.out.println("Your library is empty!");
    }
}