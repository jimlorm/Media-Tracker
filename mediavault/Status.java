package mediavault;

/**
 * Represents the current consumption status of a media entry.
 *
 * @author Jimlor
 * @version 1.0
 */
public enum Status {
    /** Media that the user plans to consume in the future. */
    PLANNED("Planned"),

    /** Media that the user is currently consuming. */
    IN_PROGRESS("In Progress"),

    /** Media that the user has finished consuming. */
    COMPLETED("Completed");

    /** The string representation of the status. */
    private final String statusDisplay;

    /**
     * Constructs a Status enum with its display name.
     *
     * @param statusDisplay the formatted string to display in the UI
     */
    Status(String statusDisplay) {
        this.statusDisplay = statusDisplay;
    }

    /**
     * Gets the formatted display name of the status.
     *
     * @return the status display string
     */
    public String getStatusDisplay() {
        return statusDisplay;
    }

    /**
     * Returns the string representation of this status.
     *
     * @return the status display string
     */
    @Override
    public String toString() {
        return statusDisplay;
    }
}