package mediavault;

/**
 * Represents a user of the Media Vault.
 *
 * @author Jimlor
 * @version 1.0
 */
public class User {
    /** The username of this component */
    private String username;
    /** The library of media entries of this user. */
    private Library myLibrary;

    /**
     * Constructs a new User with the specified username.
     *
     * @param username the username of the new user instance
     */
    public User(String username) {
        this.username = username;
        this.myLibrary = new Library();
    }

    /**
     * Gets the username assigned to this user.
     *
     * @return the username of this user
     */
    public String getUsername() {
        return username;
    }

    /**
     * Retrieves the Library object belonging to this user.
     *
     * @return the Library instance containing the user's media
     */
    public Library getLibrary() {
        return myLibrary;
    }

    /**
     * Sets the username of this user to a new name.
     *
     * @param name the new username that will be set.
     */
    public void setUsername(String name) {
        username = name;
    }
}