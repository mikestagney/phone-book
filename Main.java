package phonebook;

import java.util.List;

public class Main {

    public static final String dataFilePath = "/Users/mikestagney/Downloads/small_directory.txt";
    public static final String searchFilePath = "/Users/mikestagney/Downloads/small_find.txt";

    public static void main(String[] args) {
        SearchSortController controller = new SearchSortController(dataFilePath, searchFilePath);
        controller.linearSearch();
        controller.bubbleSortJumpSearch();
        controller.quickSortBinarySearch();
        controller.hashTableSearch();
    }

    public static String getNameFromTuple(List<String> list, int index) {
        String[] tuple = list.get(index).split(" ", 2);
        return tuple[1];
    }

}








