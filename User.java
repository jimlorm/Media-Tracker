/**
 * Represents a user of the Media Vault.
 *
 * @author Jimlor
 * @version 1.0
 */
public class User {
    /** The username of this component */
    private String username;
    /** The library of media entries of this component. */
    private Library myLibrary;

    /**
     * Class constructor specifying the username of this instance.
     *
     * @param username the username of the new <code>User</code> instance.
     */
    public User(String username) {
        this.username = username;
        this.myLibrary = new Library();
    }

    /**
     * Gets the name assigned to this component.
     *
     * @return the name of this <code>User</code>.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Retrieves the <code>Library</code> object of this class
     *
     * @return the <code>Library</code> of this <code>User</code>
     */
    public Library getLibrary() {
        return myLibrary;
    }

    /**
     * Sets the username of this class instance to a new name.
     *
     * @param name the new username that will be set.
     */
    public void setUsername(String name) {
        username = name;
    }
}