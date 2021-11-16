package phonebook;

import java.util.ArrayList;
import java.util.List;

public class SearchSortController {

    public static final String dataFilePath = "/Users/mikestagney/Downloads/directory.txt";
    public static final String searchFilePath = "/Users/mikestagney/Downloads/find.txt";
    List<String> dataSource;
    List<String> searchItems;
    int numberItemsToFind;
    long linearSearchThreshold = 0L;

    SearchSortController() {
        dataSource = FileIO.readFromFile(dataFilePath);
        searchItems = FileIO.readFromFile(searchFilePath);
        numberItemsToFind = searchItems.size();
    }

    public void linearSearch() {
        System.out.println("Start searching (linear search)...");
        Timer linearTimer = new Timer();
        int numItemsFound = LinearSearch.search(dataSource, searchItems);
        linearTimer.stopTimer();

        System.out.printf("Found %d / %d entries. Time taken: %d min. %d sec. %d ms.\n",
                numItemsFound, numberItemsToFind, linearTimer.getMinutes(), linearTimer.getSeconds(), linearTimer.getMilliseconds() );
        System.out.println();

        linearSearchThreshold = Math.abs(linearTimer.getTotalTimeMilli()) * 10L;
    }
    public void bubbleJump() {
        System.out.println("Start searching (bubble sort + jump search)...");
        int numItemsFound;
        List<String> bubbleSortJumpSearchList = new ArrayList<>(dataSource);
        Timer totalSortSearchTimer = new Timer();
        Timer sortTimer = new Timer();

        boolean completeBubbleSort = BubbleSort.sort(bubbleSortJumpSearchList, linearSearchThreshold);

        sortTimer.stopTimer();

        if (!completeBubbleSort) {
            Timer nextLinearTimer = new Timer();
            numItemsFound = LinearSearch.search(bubbleSortJumpSearchList, searchItems);
            nextLinearTimer.stopTimer();
            totalSortSearchTimer.stopTimer();

            System.out.printf("Found %d / %d entries. Time taken: %d min. %d sec. %d ms.\n",
                    numItemsFound, numberItemsToFind, totalSortSearchTimer.getMinutes(), totalSortSearchTimer.getSeconds(), totalSortSearchTimer.getMilliseconds() );
            System.out.printf("Sorting time: %d min. %d sec. %d ms. - STOPPED, moved to linear search\n",
                    sortTimer.getMinutes(), sortTimer.getSeconds(), sortTimer.getMilliseconds());
            System.out.printf("Searching time: %d min. %d sec. %d ms.\n",
                    nextLinearTimer.getMinutes(), nextLinearTimer.getSeconds(), nextLinearTimer.getMilliseconds());
            System.out.println();

        } else {

            Timer jumpSearchTimer = new Timer();
            numItemsFound = JumpSearch.search(bubbleSortJumpSearchList, searchItems);

            jumpSearchTimer.stopTimer();
            totalSortSearchTimer.stopTimer();
            printResults(numItemsFound, numberItemsToFind, sortTimer, jumpSearchTimer);
        }
    }
    public void quickSortBinarySearch() {
        System.out.println("Start searching (quick sort + binary search)...");
        int numItemsFound;
        List<String> quickSortBinarySearchList = new ArrayList<>(dataSource);

        Timer quickSortTimer = new Timer();
        quickSortBinarySearchList = QuickSort.sort(quickSortBinarySearchList);
        quickSortTimer.stopTimer();

        Timer binaryTimer = new Timer();
        numItemsFound = BinarySearch.search(quickSortBinarySearchList, searchItems);
        binaryTimer.stopTimer();

        printResults(numItemsFound, numberItemsToFind, quickSortTimer, binaryTimer);
    }

    private static void printResults(int numItemsFound, int numberItemsToFind, Timer sortTimer, Timer searchTimer) {
        System.out.printf("Found %d / %d entries. Time taken: %d min. %d sec. %d ms.\n",
                numItemsFound, numberItemsToFind,
                searchTimer.getMinutes() + sortTimer.getMinutes(),
                searchTimer.getSeconds() + sortTimer.getSeconds(),
                searchTimer.getMilliseconds() + sortTimer.getMilliseconds());

        System.out.printf("Sorting time: %d min. %d sec. %d ms.\n",
                sortTimer.getMinutes(), sortTimer.getSeconds(), sortTimer.getMilliseconds());
        System.out.printf("Searching time: %d min. %d sec. %d ms.\n",
                searchTimer.getMinutes(), searchTimer.getSeconds(), searchTimer.getMilliseconds());
        System.out.println();
    }
}