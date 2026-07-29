/**
 * Represents a single episode belonging to an episodic media entry like a TV Series.
 * @author Jimlor
 * @version 1.2
 */
public class Episode {
    /** The title of the episode. */
    private String title;
    /** The numerical order of the episode */
    private int episodeNumber;
    /** The season number the episode belongs to. */
    private int season;

    /**
     * Constructs a new Episode.
     *
     * @param title         the title of the episode
     * @param episodeNumber the episode's numerical order
     * @param season        the season number
     */
    public Episode(String title, int episodeNumber, int season) {
        this.title = title;
        this.episodeNumber = episodeNumber;
        this.season = season;
    }

    /**
     * Gets the title of the episode.
     * @return the episode title
     */
    public String getTitle() { 
        return title; 
    }

    /**
     * Sets the title of the episode.
     * @param title the new title
     */
    public void setTitle(String title) { 
        this.title = title; 
    }

    /**
     * Gets the numerical order of the episode.
     * @return the episode number
     */
    public int getEpisodeNumber() { 
        return episodeNumber; 
    }

    /**
     * Sets the numerical order of the episode.
     * @param episodeNumber the new episode number
     */
    public void setEpisodeNumber(int episodeNumber) { 
        this.episodeNumber = episodeNumber; 
    }

    /**
     * Gets the season number this episode belongs to.
     * @return the season number
     */
    public int getSeason() { 
        return season; 
    }

    /**
     * Sets the season number this episode belongs to.
     * @param season the new season number
     */
    public void setSeason(int season) { 
        this.season = season; 
    }
    
    /**
     * Displays the episode's season, number, and title.
     */
    public void displayDetails() {
        System.out.println("Season " + season + " Ep " + episodeNumber + ": " + title);
    }
}