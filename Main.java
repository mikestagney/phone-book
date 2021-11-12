package phonebook;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Main {
    public static final String dataFilePath = "/Users/mikestagney/Downloads/small_directory.txt";
    public static final String searchFilePath = "/Users/mikestagney/Downloads/small_find.txt";

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

    List<String> bubbleSortJumpSearchList = new ArrayList<>(dataSource);
    Timer totalSortSearchTimer = new Timer();
    Timer sortTimer = new Timer();

    boolean completeBubbleSort = true;
    int numOfSwaps = -1;
    int counter = 1;
    while (numOfSwaps != 0) {
        numOfSwaps = 0;

        for (int i = 0; i < bubbleSortJumpSearchList.size() - counter; i++) {
            String firstName = getNameFromTuple(bubbleSortJumpSearchList, i);
            String secondName = getNameFromTuple(bubbleSortJumpSearchList, i + 1);
            if (firstName.compareTo(secondName) > 0) {
                Collections.swap(bubbleSortJumpSearchList, i, i + 1);
                numOfSwaps++;
            }

        }
        counter++;
        /*
        if (sortTimer.getCurrentTime() > linearSearchThreshold) {
            completeBubbleSort = false;
            break;
        }*/
    }
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

    } else {

        Timer jumpSearchTimer = new Timer();
        int jumpInterval = (int) Math.floor(Math.sqrt(bubbleSortJumpSearchList.size()));
        numItemsFound = 0;
        for (String name : searchItems) {
            for (int i = 0; i < bubbleSortJumpSearchList.size(); i += jumpInterval) {
                String indexName = getNameFromTuple(bubbleSortJumpSearchList, i);
                if (name.equals(indexName)) {
                    numItemsFound++;
                    break;
                }
                if (name.compareTo(indexName) > 0) {
                    continue;
                }
                for (int j = i - 1; j > i - jumpInterval; j--) {
                    String countBackName = getNameFromTuple(bubbleSortJumpSearchList, j);
                    if (name.equals(countBackName)) {
                        numItemsFound++;
                        break;
                    }
                }
                if (i + jumpInterval >= bubbleSortJumpSearchList.size()) {
                    for (int j = bubbleSortJumpSearchList.size() - 1; j > i; j--) {
                        String countBackFromEndName = getNameFromTuple(bubbleSortJumpSearchList, j);
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
        System.out.println();
        System.out.println("Start searching (quick sort + binary search)...");
        List<String> quickSortBinarySearchList = new ArrayList<>(dataSource);


        Timer quickBinaryTimer = new Timer();
        Timer quickSortTimer = new Timer();

        quickSortBinarySearchList = quickSort(quickSortBinarySearchList);
        quickSortTimer.stopTimer();
        // quickSortBinarySearchList.forEach(System.out::println);

        Timer binaryTimer = new Timer();
        numItemsFound = BinarySearch.search(quickSortBinarySearchList, searchItems);
        binaryTimer.stopTimer();
        quickBinaryTimer.stopTimer();

        System.out.printf("Sorting time: %d min. %d sec. %d ms.\n",
                    quickSortTimer.getMinutes(), quickSortTimer.getSeconds(), quickSortTimer.getMilliseconds());
        System.out.printf("Searching time: %d min. %d sec. %d ms.\n",
                binaryTimer.getMinutes(), binaryTimer.getSeconds(), binaryTimer.getMilliseconds());
        System.out.printf("Found %d / %d entries. Time taken: %d min. %d sec. %d ms.\n",
                numItemsFound, numberItemsToFind, quickBinaryTimer.getMinutes(), quickBinaryTimer.getSeconds(), quickBinaryTimer.getMilliseconds());


    }

    public static String getNameFromTuple(List<String> list, int index) {
        String[] tuple = list.get(index).split(" ", 2);
        return tuple[1];
    }
    public static List<String> quickSort(List<String> list) {
        if (list.size() <= 1) {
            return list;
        }
        String pivot = getNameFromTuple(list, list.size() - 1);
        List<String> lower = new ArrayList<>();
        List<String> upper = new ArrayList<>();
        for (int i = 0; i < list.size() - 2; i++) {
            if (getNameFromTuple(list, i).compareTo(pivot) > 0) {
                upper.add(list.get(i));
            } else {
                lower.add(list.get(i));
            }
        }
        List<String> lowHolder = quickSort(lower);
        List<String> upperHolder = quickSort(upper);

        lowHolder.addAll(upperHolder);
        return lowHolder;
    }

}








