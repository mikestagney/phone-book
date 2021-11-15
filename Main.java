package phonebook;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static final String dataFilePath = "/Users/mikestagney/Downloads/directory.txt";
    public static final String searchFilePath = "/Users/mikestagney/Downloads/find.txt";

    public static void main (String[] args) {

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

    System.out.println("Start searching (quick sort + binary search)...");
    List<String> quickSortBinarySearchList = new ArrayList<>(dataSource);

    Timer quickSortTimer = new Timer();
    quickSortBinarySearchList = QuickSort.sort(quickSortBinarySearchList);
    quickSortTimer.stopTimer();

    Timer binaryTimer = new Timer();
    numItemsFound = BinarySearch.search(quickSortBinarySearchList, searchItems);
    binaryTimer.stopTimer();

    printResults(numItemsFound, numberItemsToFind, quickSortTimer, binaryTimer);
    }

    public static String getNameFromTuple(List<String> list, int index) {
        String[] tuple = list.get(index).split(" ", 2);
        return tuple[1];
    }

    public static void printResults(int numItemsFound, int numberItemsToFind, Timer sortTimer, Timer searchTimer) {
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








