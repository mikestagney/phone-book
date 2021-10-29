package phonebook;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.List;


// test file names
// /Users/mikestagney/Downloads/small_directory.txt
// /Users/mikestagney/Downloads/small_find.txt

public class Main {
    public static final String dataFilePath = "/Users/mikestagney/Downloads/directory.txt";
    public static final String searchFilePath = "/Users/mikestagney/Downloads/find.txt";

    public static void main(String[] args) {
        List<String> dataSource = FileIO.readFromFile(dataFilePath);
        List<String> searchItems = FileIO.readFromFile(searchFilePath);
        int numberItemsToFind = searchItems.size();

        System.out.println("Start searching...");
        Timer linearTimer = new Timer();

        int numItemsFound = LinearSearch.go(dataSource, searchItems);

        linearTimer.stopTimer();
        System.out.printf("Found %d / %d entries. Time taken: %d min. %d sec. %d ms.\n",
                numItemsFound, numberItemsToFind, linearTimer.getMinutes(), linearTimer.getSeconds(), linearTimer.getMilliseconds() );



    }

}
