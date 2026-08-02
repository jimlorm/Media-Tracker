package mediavault;

import java.util.*;

public class MediaVault {
    public static void main(String args[]) {
        Scanner sc = new Scanner(System.in);
        boolean running = true;
        int choice;

        System.out.println("-+H+-+H+-+H+-+H+-+H+-+H+-+H+-+H+-+H+-+H+-+H+-+H+-+H+-+H+-");
        System.out.println("                  Welcome to mediavault.MediaVault!                  ");
        System.out.println("-+H+-+H+-+H+-+H+-+H+-+H+-+H+-+H+-+H+-+H+-+H+-+H+-+H+-+H+-");

        System.out.print("Enter your username to begin: ");
        String username = sc.nextLine();
        User currentUser = new User(username);
        Library myLibrary = currentUser.getLibrary();

        while (running) {
            System.out.println("\n--------------------- MAIN MENU ---------------------");
            System.out.println("  1 - Add a New Media Entry");
            System.out.println("  2 - Update Entry mediavault.Status");
            System.out.println("  3 - Rate & Review a Completed Entry");
            System.out.println("  4 - View Full Details of an Entry");
            System.out.println("  5 - Display Entire mediavault.Library");
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

    private static void printSimpleList(Library library) {
        List<MediaEntry> mediaList = library.getAllMedia();
        for (int i = 0; i < mediaList.size(); i++) {
            System.out.println("[" + (i + 1) + "] " + mediaList.get(i).getTitle() + " - " + mediaList.get(i).getCurrentStatus());
        }
    }

    private static void addMediaMenu(Scanner sc, Library library) {
        System.out.println("\nWhat type of media are you adding?");
        System.out.println("  1 - mediavault.Book\n  2 - mediavault.Movie\n  3 - TV Series");
        System.out.print("Choice: ");
        int type = sc.nextInt();
        sc.nextLine();

        System.out.print("Enter Title: ");
        String title = sc.nextLine();
        System.out.print("Enter Genre: ");
        String genre = sc.nextLine();

        System.out.println("Select mediavault.Status:\n  1 - Planned\n  2 - In Progress");
        System.out.print("Choice: ");
        int statusChoice = sc.nextInt();
        sc.nextLine();
        Status status = (statusChoice == 2) ? Status.IN_PROGRESS : Status.PLANNED;

        switch (type) {
            case 1:
                System.out.print("Enter Author: ");
                String author = sc.nextLine();
                System.out.print("Enter Page Count: ");
                int pages = sc.nextInt();
                sc.nextLine();
                library.addEntry(new Book(title, genre, status, author, pages));
                System.out.println("mediavault.Book added successfully!");
                break;
            case 2:
                System.out.print("Enter Director: ");
                String director = sc.nextLine();
                System.out.print("Enter Runtime (in minutes): ");
                int runtime = sc.nextInt();
                sc.nextLine();
                library.addEntry(new Movie(title, genre, status, director, runtime));
                System.out.println("mediavault.Movie added successfully!");
                break;
            case 3:
                System.out.print("Enter Total Number of Episodes: ");
                int episodes = sc.nextInt();
                sc.nextLine();
                TVSeries newSeries = new TVSeries(title, genre, status, episodes);
                
                System.out.println("Let's add the episode titles for this series.");
                for (int i = 1; i <= episodes; i++) {
                    System.out.print("Enter title for mediavault.Episode " + i + ": ");
                    newSeries.addEpisode(new Episode(sc.nextLine(), i));
                }
                library.addEntry(newSeries);
                System.out.println("TV Series and episodes added successfully!");
                break;
            default:
                System.out.println("Invalid media type. Cancelled.");
        }
    }

    private static void updateStatusMenu(Scanner sc, Library library) {
        if (library.getSize() == 0) {
            System.out.println("Your library is empty!");
            return;
        }
        printSimpleList(library);
        System.out.print("Enter entry number to update: ");
        int index = sc.nextInt() - 1;
        sc.nextLine();

        MediaEntry entry = library.getEntry(index);
        if (entry != null) {
            System.out.println("Update status to:\n  1 - Planned\n  2 - In Progress\n  3 - Completed");
            System.out.print("Enter: ");
            int choice = sc.nextInt();
            sc.nextLine();
            
            Status newStatus = Status.PLANNED;
            if (choice == 2) newStatus = Status.IN_PROGRESS;
            if (choice == 3) newStatus = Status.COMPLETED;

            library.updateProgress(entry, newStatus);
            System.out.println("mediavault.Status updated successfully.");
        } else {
            System.out.println("Invalid entry number.");
        }
    }

    private static void rateReviewMenu(Scanner sc, Library library) {
        if (library.getSize() == 0) {
            System.out.println("Your library is empty!");
            return;
        }
        printSimpleList(library);
        System.out.print("Enter entry number to rate: ");
        int index = sc.nextInt() - 1;
        sc.nextLine();

        MediaEntry entry = library.getEntry(index);
        if (entry != null) {
            try {
                System.out.print("Enter rating (0-10): ");
                int rating = sc.nextInt();
                sc.nextLine();
                
                System.out.print("Write a review (or press ENTER to skip): ");
                String review = sc.nextLine();
                if (review.trim().isEmpty()) review = null;
                
                
                library.rateEntry(entry, rating, review);
                System.out.println("Successfully rated!");
                
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        } else {
            System.out.println("Invalid entry number.");
        }
    }

    private static void viewDetailsMenu(Scanner sc, Library library) {
        if (library.getSize() == 0) {
            System.out.println("Your library is empty!");
            return;
        }
        printSimpleList(library);
        System.out.print("Enter entry number to view: ");
        int index = sc.nextInt() - 1;
        sc.nextLine();

        MediaEntry entry = library.getEntry(index);
        if (entry != null) {
            System.out.println("\n-----------------------------------------");
            
            System.out.println(entry.getDetails()); 
            
            if (entry.getCurrentStatus() == Status.COMPLETED) {
                System.out.println("Your Rating: " + entry.getRating() + "/10");
                System.out.println("Your Review: " + (entry.getReview() != null ? entry.getReview() : "(No review)"));
            } else {
                System.out.println("Rating: N/A (Finish it first!)");
            }
            
            if (entry instanceof TVSeries) {
                System.out.println("\n------- mediavault.Episode List --------");
                for (Episode ep : ((TVSeries) entry).getEpisodes()) {
                    System.out.println(ep.getDetails()); // mediavault.Episode details
                }
            }
            System.out.println("-----------------------------------------");
        } else {
            System.out.println("Invalid entry number.");
        }
    }

    private static void displayLibraryMenu(Scanner sc, Library library, User user) {
        System.out.println("\n--- " + user.getUsername() + "'s mediavault.Library ---");
        if (library.getSize() == 0) {
            System.out.println("No media in your library yet.");
            return;
        }

        System.out.println("Display by:\n  1 - Entire mediavault.Library\n  2 - Filter by mediavault.Status\n  3 - Filter by Type");
        System.out.print("Choice: ");
        int choice = sc.nextInt();
        sc.nextLine();

        List<MediaEntry> results;

        if (choice == 2) {
            System.out.println("mediavault.Status:\n  1 - Planned\n  2 - In Progress\n  3 - Completed");
            System.out.print("Choice: ");
            int statChoice = sc.nextInt();
            sc.nextLine();
            Status target = (statChoice == 3) ? Status.COMPLETED : (statChoice == 2) ? Status.IN_PROGRESS : Status.PLANNED;
            
            results = library.getMediaByStatus(target); 
        } else if (choice == 3) {
            System.out.println("Type:\n  1 - mediavault.Book\n  2 - mediavault.Movie\n  3 - TV Series");
            System.out.print("Choice: ");
            int typeChoice = sc.nextInt();
            sc.nextLine();
            Class<?> targetClass = (typeChoice == 1) ? Book.class : (typeChoice == 2) ? Movie.class : TVSeries.class;
            
            results = library.getMediaByType(targetClass); 
        } else {
            results = library.getAllMedia();
        }

        System.out.println("\n--- Results ---");
        if (results.isEmpty()) {
            System.out.println("No matching items found.");
        } else {
            for (int i = 0; i < results.size(); i++) {
                System.out.println((i + 1) + ". " + results.get(i).getTitle() + " (" + results.get(i).getCurrentStatus() + ")");
            }
            System.out.println("\nTotal Items: " + results.size());
        }
    }

    private static void deleteEntryMenu(Scanner sc, Library library) {
        if (library.getSize() == 0) {
            System.out.println("Your library is empty!");
            return;
        }
        printSimpleList(library);
        System.out.print("Enter entry number to DELETE: ");
        int index = sc.nextInt() - 1;
        sc.nextLine();

        try {
            library.deleteEntry(index);
            System.out.println("Entry successfully deleted.");
        } catch (IndexOutOfBoundsException e) {
            System.out.println(e.getMessage());
        }
    }

}

