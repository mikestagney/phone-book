package phonebook;

import java.util.ArrayList;
import java.util.List;

public class SearchSortController {
    List<String> dataSource;
    List<String> searchItems;
    static int numberItemsToFind;
    long linearSearchThreshold = 0L;
    static final String SORTING = "Sorting";
    static final String SEARCHING = "Searching";

    SearchSortController(String dataFilePath, String searchFilePath) {
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
    public void bubbleSortJumpSearch() {
        System.out.println("Start searching (bubble sort + jump search)...");
        int numItemsFound = 0;
        List<String> bubbleSortJumpSearchList = new ArrayList<>(dataSource);

        Timer sortTimer = new Timer();
        boolean completeBubbleSort = BubbleSort.sort(bubbleSortJumpSearchList, linearSearchThreshold);
        sortTimer.stopTimer();

        if (!completeBubbleSort) {
            Timer nextLinearTimer = new Timer();
            numItemsFound = LinearSearch.search(bubbleSortJumpSearchList, searchItems);
            nextLinearTimer.stopTimer();

            printNumberFoundAndTimers(numItemsFound, sortTimer, nextLinearTimer);
            System.out.printf("Sorting time: %d min. %d sec. %d ms. - STOPPED, moved to linear search\n",
                    sortTimer.getMinutes(), sortTimer.getSeconds(), sortTimer.getMilliseconds());
            printTimerDetail(SORTING, nextLinearTimer);
            System.out.println();

        } else {
            Timer jumpSearchTimer = new Timer();
            numItemsFound = JumpSearch.search(bubbleSortJumpSearchList, searchItems);
            jumpSearchTimer.stopTimer();

            printNumberFoundAndTimers(numItemsFound, sortTimer, jumpSearchTimer);
            printTimerDetail(SORTING, sortTimer);
            printTimerDetail(SEARCHING, jumpSearchTimer);
            System.out.println();
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

        printNumberFoundAndTimers(numItemsFound, quickSortTimer, binaryTimer);
        printTimerDetail(SORTING, quickSortTimer);
        printTimerDetail(SEARCHING, binaryTimer);
        System.out.println();
    }
    public void hashTableSearch() {
        System.out.println("Start searching (hash table)...");
        Timer hashBuildTimer = new Timer();
        HashTable hashTable = new HashTable(dataSource);
        hashBuildTimer.stopTimer();
        Timer hashSearchTimer = new Timer();
        int numItemsFound = hashTable.search(searchItems);
        hashSearchTimer.stopTimer();

        printNumberFoundAndTimers(numItemsFound, hashBuildTimer, hashSearchTimer);
        printTimerDetail("Creating", hashBuildTimer);
        printTimerDetail(SEARCHING, hashSearchTimer);
        System.out.println();
    }
    private static void printNumberFoundAndTimers(int numItemsFound, Timer sortTimer, Timer searchTimer) {
        System.out.printf("Found %d / %d entries. Time taken: %d min. %d sec. %d ms.\n",
                numItemsFound, numberItemsToFind,
                searchTimer.getMinutes() + sortTimer.getMinutes(),
                searchTimer.getSeconds() + sortTimer.getSeconds(),
                searchTimer.getMilliseconds() + sortTimer.getMilliseconds());
    }
    private static void printTimerDetail(String timeType, Timer timer) {
        System.out.printf("%s time: %d min. %d sec. %d ms.\n",
                timeType, timer.getMinutes(), timer.getSeconds(), timer.getMilliseconds());
    }
}
