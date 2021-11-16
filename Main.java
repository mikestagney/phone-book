package phonebook;

import java.util.List;

public class Main {

    public static void main(String[] args) {
        SearchSortController controller = new SearchSortController();
        controller.linearSearch();
        controller.bubbleSortJumpSearch();
        controller.quickSortBinarySearch();
    }

    public static String getNameFromTuple(List<String> list, int index) {
        String[] tuple = list.get(index).split(" ", 2);
        return tuple[1];
    }

}








