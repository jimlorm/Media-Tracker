import java.util.*;

public class MediaVault {
    public static void main(String args[]) {
        Scanner sc = new Scanner(System.in);
        boolean running = true;
        int choice;

        System.out.println("-+H+-+H+-+H+-+H+-+H+-+H+-+H+-+H+-+H+-+H+-+H+-+H+-+H+-+H+-");
        System.out.println("                  Welcome to MediaVault!                  ");
        System.out.println("-+H+-+H+-+H+-+H+-+H+-+H+-+H+-+H+-+H+-+H+-+H+-+H+-+H+-+H+-");

        System.out.print("Enter your username to begin: ");
        String username = sc.nextLine();
        User currentUser = new User(username);
        Library myLibrary = currentUser.getLibrary();

        while (running) {
            System.out.println("\n--------------------- MAIN MENU ---------------------");
            System.out.println("  1 - Add a New Media Entry");
            System.out.println("  2 - Update Entry Status");
            System.out.println("  3 - Rate & Review a Completed Entry");
            System.out.println("  4 - View Full Details of an Entry");
            System.out.println("  5 - Display Entire Library");
            System.out.println("  6 - Delete an Entry");
            System.out.println("  7 - Exit");
            System.out.println("-------------------------------------------------------");
            System.out.print("Select an option: ");

            choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1: 
                    addMediaMenu(sc, myLibrary); 
                    break;
                case 2: 
                    updateStatusMenu(sc, myLibrary); 
                    break;
                case 3: 
                    rateReviewMenu(sc, myLibrary); 
                    break;
                case 4: 
                    viewDetailsMenu(sc, myLibrary); 
                    break;
                case 5: 
                    displayLibraryMenu(sc, myLibrary, currentUser); 
                    break;
                case 6: 
                    deleteEntryMenu(sc, myLibrary); 
                    break;
                case 7:
                    running = false;
                    System.out.println("Goodbye, " + currentUser.getUsername() + "!");
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
        sc.close();
    }


}

