package mediavault;

/**
 * Represents a single episode belonging to an episodic media entry like a TV Series.
 * @author Jimlor
 * @version 2.0
 */
public class Episode {
    /** The title of the episode. */
    private String title;

    /** The numerical order of the episode */
    private int episodeNumber;

    /**
     * Constructs a new Episode.
     *
     * @param title         the title of the episode
     * @param episodeNumber the episode's numerical order
     */
    public Episode(String title, int episodeNumber) {
        this.title = title;
        this.episodeNumber = episodeNumber;
    }

    /**
     * Gets the title of the episode.
     *
     * @return the episode title
     */
    public String getTitle() { 
        return title; 
    }

    /**
     * Sets the title of the episode.
     *
     * @param title the new title
     */
    public void setTitle(String title) { 
        this.title = title; 
    }

    /**
     * Gets the numerical order of the episode.
     *
     * @return the episode number
     */
    public int getEpisodeNumber() { 
        return episodeNumber; 
    }

    /**
     * Sets the numerical order of the episode.
     *
     * @param episodeNumber the new episode number to assign
     */
    public void setEpisodeNumber(int episodeNumber) { 
        this.episodeNumber = episodeNumber; 
    }
    
    /**
     * Retrieves the episode's number and title.
     * 
     * @return a formatted string of the episode details
     */
    public String getDetails() {
        return "Ep " + episodeNumber + ": " + title;
    }
}