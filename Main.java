package phonebook;

import java.util.Collections;
import java.util.List;

public class Main {
    public static final String dataFilePath = "/Users/mikestagney/Downloads/directory.txt";
    public static final String searchFilePath = "/Users/mikestagney/Downloads/find.txt";

    public static void main(String[] args) {

    List<String> dataSource = FileIO.readFromFile(dataFilePath);
    List<String> searchItems = FileIO.readFromFile(searchFilePath);
    int numberItemsToFind = searchItems.size();

    System.out.println("Start searching (linear search)...");
    Timer linearTimer = new Timer();

    int numItemsFound = LinearSearch.go(dataSource, searchItems);

    linearTimer.stopTimer();
    System.out.printf("Found %d / %d entries. Time taken: %d min. %d sec. %d ms.\n",
    numItemsFound, numberItemsToFind, linearTimer.getMinutes(), linearTimer.getSeconds(), linearTimer.getMilliseconds() );
    System.out.println();

    long linearSearchThreshold = Math.abs(linearTimer.getTotalTimeMilli()) * 10L;
    //System.out.println("search threshold " + linearSearchThreshold);
    System.out.println("Start searching (bubble sort + jump search)...");
    Timer totalSortSearchTimer = new Timer();
    Timer sortTimer = new Timer();
    // add bubble sort
    boolean completeSearch = true;
        for (int i = 0; i < dataSource.size() - 1; i++) {
        for (int j = i + 1; j < dataSource.size(); j++) {
            String[] firstTuple = dataSource.get(i).split(" ", 2);
            String firstName = firstTuple[1];
            String[] secondTuple = dataSource.get(j).split(" ",  2);
            String secondName = secondTuple[1];
            if (firstName.compareTo(secondName) > 0) {
                Collections.swap(dataSource, i, j);
            }
        }
        // System.out.println("Current time: " + sortTimer.getCurrentTime() + " compare to " + linearSearchThreshold);
        if (sortTimer.getCurrentTime() > linearSearchThreshold) {
            completeSearch = false;
            break;
        }
    }
    if (!completeSearch) {
        sortTimer.stopTimer();
        Timer nextLinearTimer = new Timer();
        numItemsFound = LinearSearch.go(dataSource, searchItems);
        nextLinearTimer.stopTimer();
        totalSortSearchTimer.stopTimer();
        System.out.printf("Found %d / %d entries. Time taken: %d min. %d sec. %d ms.\n",
                numItemsFound, numberItemsToFind, totalSortSearchTimer.getMinutes(), totalSortSearchTimer.getSeconds(), totalSortSearchTimer.getMilliseconds() );

        System.out.printf("Sorting time: %d min. %d sec. %d ms. - STOPPED, moved to linear search\n",
                sortTimer.getMinutes(), sortTimer.getSeconds(), sortTimer.getMilliseconds());
        System.out.printf("Searching time: %d min. %d sec. %d ms.\n",
                nextLinearTimer.getMinutes(), nextLinearTimer.getSeconds(), nextLinearTimer.getMilliseconds());

    } else {

        sortTimer.stopTimer();
        System.out.printf("Sorting time: %d min. %d sec. %d ms.\n",
                sortTimer.getMinutes(), sortTimer.getSeconds(), sortTimer.getMilliseconds());

        //dataSource.forEach(System.out::println);

        // jump search
        Timer jumpSearchTimer = new Timer();
        int jumpInterval = (int) Math.floor(Math.sqrt(dataSource.size()));
        numItemsFound = 0;
        for (String name : searchItems) {
            for (int i = 0; i < dataSource.size(); i += jumpInterval) {
                String[] tuple = dataSource.get(i).split(" ", 2);
                String indexName = tuple[1];
                if (name.equals(indexName)) {
                    numItemsFound++;
                    break;
                }
                if (name.compareTo(indexName) > 0) {
                    continue;
                }
                for (int j = i - 1; j > i - jumpInterval; j--) {
                    String[] countBackTuple = dataSource.get(j).split(" ", 2);
                    String countBackName = countBackTuple[1];
                    if (name.equals(countBackName)) {
                        numItemsFound++;
                        break;
                    }
                }
                break;
            }
        }
        totalSortSearchTimer.stopTimer();
        jumpSearchTimer.stopTimer();
        System.out.printf("Searching time: %d min. %d sec. %d ms.\n",
                jumpSearchTimer.getMinutes(), jumpSearchTimer.getSeconds(), jumpSearchTimer.getMilliseconds());
        System.out.println();
        System.out.printf("Found %d / %d entries. Time taken: %d min. %d sec. %d ms.\n",
                numItemsFound, numberItemsToFind, totalSortSearchTimer.getMinutes(), totalSortSearchTimer.getSeconds(), totalSortSearchTimer.getMilliseconds());

    }

}}








