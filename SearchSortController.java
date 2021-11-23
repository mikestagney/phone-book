package phonebook;

import java.util.ArrayList;
import java.util.List;

public class SearchSortController {
    List<String> dataSource;
    List<String> searchItems;
    static int numberItemsToFind;
    long linearSearchThreshold = 0L;
    static final String SORTING = "sorting";
    static final String SEARCHING = "searching";

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

            printFoundNumbers(numItemsFound, sortTimer, nextLinearTimer);
            System.out.printf("Sorting time: %d min. %d sec. %d ms. - STOPPED, moved to linear search\n",
                    sortTimer.getMinutes(), sortTimer.getSeconds(), sortTimer.getMilliseconds());
            printTimeDetail(SORTING, nextLinearTimer);
            System.out.println();

        } else {

            Timer jumpSearchTimer = new Timer();
            numItemsFound = JumpSearch.search(bubbleSortJumpSearchList, searchItems);
            jumpSearchTimer.stopTimer();

            printFoundNumbers(numItemsFound, sortTimer, jumpSearchTimer);
            printTimeDetail(SORTING, sortTimer);
            printTimeDetail(SEARCHING, jumpSearchTimer);
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

        printFoundNumbers(numItemsFound, quickSortTimer, binaryTimer);
        printTimeDetail(SORTING, quickSortTimer);
        printTimeDetail(SEARCHING, binaryTimer);
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

        printFoundNumbers(numItemsFound, hashBuildTimer, hashSearchTimer);
        printTimeDetail("Creating", hashBuildTimer);
        printTimeDetail(SEARCHING, hashSearchTimer);
        System.out.println();
    }
    private static void printFoundNumbers(int numItemsFound, Timer sortTimer, Timer searchTimer) {
        System.out.printf("Found %d / %d entries. Time taken: %d min. %d sec. %d ms.\n",
                numItemsFound, numberItemsToFind,
                searchTimer.getMinutes() + sortTimer.getMinutes(),
                searchTimer.getSeconds() + sortTimer.getSeconds(),
                searchTimer.getMilliseconds() + sortTimer.getMilliseconds());
    }
    private static void printTimeDetail(String timeType, Timer timer) {
        System.out.printf("%s time: %d min. %d sec. %d ms.\n",
                timeType, timer.getMinutes(), timer.getSeconds(), timer.getMilliseconds());
    }
}
