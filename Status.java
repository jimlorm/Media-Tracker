public enum Status {
    PLANNED("Planned"),
    IN_PROGRESS("In Progress"),
    COMPLETED("Completed");

    private final String statusDisplay;

    Status(String statusDisplay) {
        this.statusDisplay = statusDisplay;
    }
    
    public String getStatusDisplay() {
        return statusDisplay;
    }
}