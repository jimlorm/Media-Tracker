package mediavault;

/**
 * Represents a standalone movie entry in the library.
 * <p>Extends the {@link MediaEntry} class with movie-specific attributes.</p>
 * @author Jimlor
 * @version 1.2
 */
public class Movie extends MediaEntry {
    /** The director of the movie. */
    private String director;
    /** The runtime of the movie in minutes. */
    private int runtimeMinutes;

    /**
     * Constructs a new Movie entry.
     *
     * @param title          the title of the movie
     * @param genre          the genre of the movie
     * @param status         the current consumption status
     * @param director       the director of the movie
     * @param runtimeMinutes the total runtime in minutes
     */
    public Movie(String title, String genre, Status status, String director, int runtimeMinutes) {
        super(title, genre, status);
        this.director = director;
        setRuntimeMinutes(runtimeMinutes);
    }

    /**
     * Gets the director of the movie.
     *
     * @return the director's name
     */
    public String getDirector() { 
        return director; 
    }

    /**
     * Sets the director of the movie.
     *
     * @param director the new director name
     */
    public void setDirector(String director) { 
        this.director = director; 
    }
    
    /**
     * Gets the runtime of the movie.
     *
     * @return the runtime in minutes
     */
    public int getRuntimeMinutes() { 
        return runtimeMinutes; 
    }

    /**
     * Sets the runtime of the movie.
     *
     * @param runtimeMinutes the new runtime in minutes
     * @throws IllegalArgumentException if the runtime provided is negative
     */
    public void setRuntimeMinutes(int runtimeMinutes) { 
        if (runtimeMinutes < 0) {
            throw new IllegalArgumentException("Runtime cannot be negative.");
        }
        
        this.runtimeMinutes = runtimeMinutes; 
    }

    /**
     * Retrieves the formatted details of the movie.
     *
     * @return a string containing the movie's information
     */
    @Override
    public String getDetails() {
        return "[Movie] " + title + "\n" +
               "Director: " + director + "\n" +
               "Genre: " + genre + " | Status: " + currentStatus + "\n" +
               "Runtime: " + runtimeMinutes + " mins";
    }
}