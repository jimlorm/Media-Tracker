package mediavault;

import java.io.*;

/**
 * Handles all File Input/Output operations for the Media Vault application.
 * <p>This class is responsible for saving the user's library to a text file
 * and loading it back into memory during application startup.</p>
 *
 * @author Jimlor
 * @version 1.0
 */
public class FileManager {
    /** The static file path where the library data is stored. */
    private static final String FILE_NAME = "mediavault_data.txt";

    /**
     * Saves the current state of the library to a text file.
     * <p>Formats each media entry with a pipe delimiter. Episodes in TV Series are
     * separated by custom string delimiters.</p>
     *
     * @param library the {@link Library} instance to be saved
     */
    public static void saveLibrary(Library library) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (MediaEntry m : library.getAllMedia()) {
                StringBuilder sb = new StringBuilder();

                String review = m.getReview() == null ? "NO_REVIEW" : m.getReview();

                if (m instanceof Book) {
                    Book b = (Book) m;
                    sb.append("Book|").append(b.getTitle()).append("|").append(b.getGenre()).append("|")
                            .append(b.getCurrentStatus().name()).append("|").append(b.getRating()).append("|")
                            .append(review).append("|").append(b.getAuthor()).append("|").append(b.getPageCount());
                } else if (m instanceof Movie) {
                    Movie mov = (Movie) m;
                    sb.append("Movie|").append(mov.getTitle()).append("|").append(mov.getGenre()).append("|")
                            .append(mov.getCurrentStatus().name()).append("|").append(mov.getRating()).append("|")
                            .append(review).append("|").append(mov.getDirector()).append("|").append(mov.getRuntimeMinutes());
                } else if (m instanceof TVSeries) {
                    TVSeries tv = (TVSeries) m;
                    sb.append("TVSeries|").append(tv.getTitle()).append("|").append(tv.getGenre()).append("|")
                            .append(tv.getCurrentStatus().name()).append("|").append(tv.getRating()).append("|")
                            .append(review).append("|").append(tv.getTotalEpisodes()).append("|");

                    if (tv.getEpisodes().isEmpty()) {
                        sb.append("NO_EPISODES");
                    } else {
                        for (int i = 0; i < tv.getEpisodes().size(); i++) {
                            Episode ep = tv.getEpisodes().get(i);
                            sb.append(ep.getTitle()).append("~").append(ep.getEpisodeNumber());
                            if (i < tv.getEpisodes().size() - 1) sb.append(";;");
                        }
                    }
                }

                writer.write(sb.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving file: " + e.getMessage());
        }
    }

    public static void loadLibrary(Library library) {
        File file = new File(FILE_NAME);
        if (file.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split("\\|");

                    if (parts.length >= 8) {
                        String type = parts[0];
                        String title = parts[1];
                        String genre = parts[2];

                        Status status = Status.valueOf(parts[3]);

                        int rating = Integer.parseInt(parts[4]);
                        String review = parts[5].equals("NO_REVIEW") ? null : parts[5];

                        if (type.equals("Book")) {
                            Book b = new Book(title, genre, status, parts[6], Integer.parseInt(parts[7]));
                            if (status == Status.COMPLETED && review != null) b.rate(rating, review);
                            library.addEntry(b);
                        } else if (type.equals("Movie")) {
                            Movie m = new Movie(title, genre, status, parts[6], Integer.parseInt(parts[7]));
                            if (status == Status.COMPLETED && review != null) m.rate(rating, review);
                            library.addEntry(m);
                        } else if (type.equals("TVSeries")) {
                            TVSeries tv = new TVSeries(title, genre, status, Integer.parseInt(parts[6]));
                            if (status == Status.COMPLETED && review != null) tv.rate(rating, review);

                            String epsData = parts[7];
                            if (!epsData.equals("NO_EPISODES")) {
                                String[] epArray = epsData.split(";;");
                                for (String epStr : epArray) {
                                    String[] epParts = epStr.split("~");
                                    if (epParts.length == 2) {
                                        tv.addEpisode(new Episode(epParts[0], Integer.parseInt(epParts[1])));
                                    }
                                }
                            }
                            library.addEntry(tv);
                        }
                    } else {
                        System.out.println("Warning: Skipped corrupted or incomplete line in save file.");
                    }
                }
            } catch(IOException | IllegalArgumentException e){
                System.out.println("Error loading file: " + e.getMessage());
                }
            }
        }
    }