package phonebook;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.List;


// test file names
// /Users/mikestagney/Downloads/small_directory.txt
// /Users/mikestagney/Downloads/small_find.txt

public class Main {
    public static void main(String[] args) {
        String dataFilePath = "/Users/mikestagney/Downloads/small_directory.txt";
        List<String> dataSource = FileIO.readFromFile(dataFilePath);

        String searchFilePath = "/Users/mikestagney/Downloads/small_find.txt";
        List<String> searchItems = FileIO.readFromFile(searchFilePath);

        System.out.println("Start searching...");
        Timer linearTimer = new Timer();
        //long startTime = System.currentTimeMillis();

        int numberItemsToFind = searchItems.size();
        int numItemsFound = 0;
        for (String searchItem : searchItems) {
            for (String tuple : dataSource) {
                String[] datum = tuple.split(" ", 2);
                if (searchItem.equals(datum[1])) {
                    numItemsFound++;
                    break;
                }
            }
        }
        /*
        long endTime = System.currentTimeMillis();
        long timeTaken =  endTime - startTime;
        Duration duration = Duration.of(timeTaken, ChronoUnit.MILLIS);

        long minutes = duration.toMinutesPart();
        long seconds = duration.toSecondsPart();
        long milliseconds = duration.toMillisPart();
        */
        linearTimer.stopTimer();
        System.out.printf("Found %d / %d entries. Time taken: %d min. %d sec. %d ms.\n",
                numItemsFound, numberItemsToFind, linearTimer.getMinutes(), linearTimer.getSeconds(), linearTimer.getMilliseconds() );
    }

}
