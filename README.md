# Media-Tracker
Media Tracker is a desktop application built to track and manage personal media consumption. The application utilizes a strict Model-View-Controller (MVC) architecture and applies core OOP concepts like inheritance, composition, and exception handling. It allows users to maintain a digital library of Books, Movies, and TV Series, updating their progress and securely saving their data locally without needing a complex external database.

## Features
* **Multi-Media Tracking**: Add and manage `Book` entries (tracking pages), `Movie` entries (tracking runtime), and `TVSeries` entries (tracking individual 'Episode' objects).
* **Status Management**: Update the consumption status of each entry using a predefined Enum (`PLANNED`, `IN_PROGRESS`, `COMPLETED`)
* **Logic & Validation**: The system prevents you from rating or reviewing a piece of media unless its status is actively set to `COMPLETED`. If you move a completed item back to "Planned", the app automatically clears out your old rating and review.
* **File I/O**: The `FileManager` class automatically serializes the library into a flat text file (`mediavault_data.txt`) using delimiters (`|` and `~`) when the app closes, and reconstructs the objects when the app launches.
* **Search and Filter**: The GUI allows you to live-search by title or filter the list view by specific media types or consumption statuses.

## Requirements
To run this project on your machine, you will need the following installed:
* **Java Development Kit (JDK)**: Version 11 or higher.
* **JavaFX SDK**: Required to render the graphical user interface.

## How to Compile
Open your terminal or command prompt, navigate to the root folder of the project, and compile the Java files:
```bash
javac --module-path "C:\path\to\your\javafx-sdk\lib" -d bin src/module-info.java src/mediavault/*.java
```
*(Note: Replace "C:\path\to\your\javafx-sdk\lib" with the actual path where you extracted your JavaFX library).*

## How to Run
Once compiled, you can launch the application by running the `MainApp` class:
```bash
java --module-path "C:\path\to\your\javafx-sdk\lib;bin" -m mediavault/mediavault.MainApp
```
## Project Structure
The project is organized using the MVC pattern to keep the data logic completely separate from the visual interface. Here is how the files are structured within the mediavault package:

**The Model**
* `User.java`: Represents the profile of the person using the app and owns the library.
* `Library.java`: The core collection class that handles adding, deleting, filtering, and updating media.
* `MediaEntry.java`: The abstract base class that holds common attributes like title, genre, rating, and status.
* `Book.java`, `Movie.java`, `TVSeries.java`: Concrete subclasses that inherit from MediaEntry and add unique attributes (like authors, directors, and episode lists).
* `Episode.java`: A simple class representing a single episode, which is owned by a TVSeries.
* `Status.java`: An enumeration defining the valid states for any media (PLANNED, IN_PROGRESS, COMPLETED).
* `FileManager.java`: A utility class that handles reading from and writing to mediavault_data.txt.

**The View**
* `MainView.fxml`: The XML file that defines the visual layout of the application (buttons, lists, text areas).

**The Controller**
* `MainApp.java`: The JavaFX entry point that loads the FXML, sets up the primary window, and handles startup/shutdown routines.
* `MainController.java`: Listens to user interactions in the UI (like clicking "Add Entry" or typing in the search bar) and tells the `Library` what to do.
* `MediaVault.java`: An older terminal-based CLI version of the app from MCO1, preserved for reference and testing.