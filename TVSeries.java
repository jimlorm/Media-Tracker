import java.util.ArrayList;

/**
 * Represents an episodic TV Series entry in the library.
 * <p>Extends the {@link MediaEntry} class and manages a collection of episodes.</p>
 * @author Jimlor
 * @version 1.4
 */
public class TVSeries extends MediaEntry {
    /** The total expected number of episodes in the series. */
    private int totalEpisodes;
    /** The list of specific episodes tracked by the user. */
    private ArrayList<Episode> episodes;

    /**
     * Constructs a new TV Series entry.
     *
     * @param title         the title of the TV series
     * @param genre         the genre of the TV series
     * @param status        the current consumption status
     * @param totalEpisodes the total number of episodes in the series
     */
    public TVSeries(String title, String genre, String status, int totalEpisodes) {
        super(title, genre, status);
        this.totalEpisodes = totalEpisodes;
        this.episodes = new ArrayList<>();
    }

    /**
     * Gets the total number of episodes in the series.
     * @return the total episode count
     */
    public int getTotalEpisodes() { 
        return totalEpisodes; 
    }

    /**
     * Sets the total number of episodes in the series.
     * @param totalEpisodes the new total episode count
     */
    public void setTotalEpisodes(int totalEpisodes) {
        this.totalEpisodes = totalEpisodes; 
    }

    /**
     * Retrieves the list of tracked episodes for this series.
     * @return an ArrayList of {@link Episode} objects
     */
    public ArrayList<Episode> getEpisodes() { 
        return episodes; 
    }

    /**
     * Adds a newly watched episode to the series tracking list.
     * @param ep the {@link Episode} to add
     */
    public void addEpisode(Episode ep) {
        episodes.add(ep);
    }

    /**
     * Displays the core details of the TV series
     */
    @Override
    public void displayDetails() {
        System.out.println("[TV Series] " + getTitle());
        System.out.println("Genre: " + getGenre() + " | Status: " + getCurrentStatus());
        System.out.println("Total Episodes: " + totalEpisodes);
    }
}