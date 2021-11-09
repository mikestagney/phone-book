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

    int numItemsFound = LinearSearch.search(dataSource, searchItems);

    linearTimer.stopTimer();
    System.out.printf("Found %d / %d entries. Time taken: %d min. %d sec. %d ms.\n",
    numItemsFound, numberItemsToFind, linearTimer.getMinutes(), linearTimer.getSeconds(), linearTimer.getMilliseconds() );
    System.out.println();

    long linearSearchThreshold = Math.abs(linearTimer.getTotalTimeMilli()) * 10L;

    System.out.println("Start searching (bubble sort + jump search)...");
    Timer totalSortSearchTimer = new Timer();
    Timer sortTimer = new Timer();

    boolean completeBubbleSort = true;
    int numOfSwaps = -1;
    int counter = 1;
    while (numOfSwaps != 0) {
        numOfSwaps = 0;

        for (int i = 0; i < dataSource.size() - counter; i++) {
            String firstName = getNameFromTuple(dataSource, i);
            String secondName = getNameFromTuple(dataSource, i + 1);
            if (firstName.compareTo(secondName) > 0) {
                Collections.swap(dataSource, i, i + 1);
                numOfSwaps++;
            }

        }
        counter++;

        if (sortTimer.getCurrentTime() > linearSearchThreshold) {
            completeBubbleSort = false;
            break;
        }
    }
    sortTimer.stopTimer();

    if (!completeBubbleSort) {
        Timer nextLinearTimer = new Timer();
        numItemsFound = LinearSearch.search(dataSource, searchItems);
        nextLinearTimer.stopTimer();
        totalSortSearchTimer.stopTimer();

        System.out.printf("Found %d / %d entries. Time taken: %d min. %d sec. %d ms.\n",
                numItemsFound, numberItemsToFind, totalSortSearchTimer.getMinutes(), totalSortSearchTimer.getSeconds(), totalSortSearchTimer.getMilliseconds() );
        System.out.printf("Sorting time: %d min. %d sec. %d ms. - STOPPED, moved to linear search\n",
                sortTimer.getMinutes(), sortTimer.getSeconds(), sortTimer.getMilliseconds());
        System.out.printf("Searching time: %d min. %d sec. %d ms.\n",
                nextLinearTimer.getMinutes(), nextLinearTimer.getSeconds(), nextLinearTimer.getMilliseconds());

    } else {

        Timer jumpSearchTimer = new Timer();
        int jumpInterval = (int) Math.floor(Math.sqrt(dataSource.size()));
        numItemsFound = 0;
        for (String name : searchItems) {
            for (int i = 0; i < dataSource.size(); i += jumpInterval) {
                String indexName = getNameFromTuple(dataSource, i);
                if (name.equals(indexName)) {
                    numItemsFound++;
                    break;
                }
                if (name.compareTo(indexName) > 0) {
                    continue;
                }
                for (int j = i - 1; j > i - jumpInterval; j--) {
                    String countBackName = getNameFromTuple(dataSource, j);
                    if (name.equals(countBackName)) {
                        numItemsFound++;
                        break;
                    }
                }
                if (i + jumpInterval >= dataSource.size()) {
                    for (int j = dataSource.size() - 1; j > i; j--) {
                        String countBackFromEndName = getNameFromTuple(dataSource, j);
                        if (name.equals(countBackFromEndName)) {
                            numItemsFound++;
                            break;
                        }
                    }
                }
                break;
            }
        }
        jumpSearchTimer.stopTimer();
        totalSortSearchTimer.stopTimer();

        System.out.printf("Sorting time: %d min. %d sec. %d ms.\n",
                sortTimer.getMinutes(), sortTimer.getSeconds(), sortTimer.getMilliseconds());
        System.out.printf("Searching time: %d min. %d sec. %d ms.\n",
                jumpSearchTimer.getMinutes(), jumpSearchTimer.getSeconds(), jumpSearchTimer.getMilliseconds());
        System.out.println();
        System.out.printf("Found %d / %d entries. Time taken: %d min. %d sec. %d ms.\n",
                numItemsFound, numberItemsToFind, totalSortSearchTimer.getMinutes(), totalSortSearchTimer.getSeconds(), totalSortSearchTimer.getMilliseconds());

        }
    }
    public static String getNameFromTuple(List<String> list, int index) {
        String[] tuple = list.get(index).split(" ", 2);
        return tuple[1];
    }
}








