package phonebook;

import java.util.List;

public class BinarySearch {

    public static int search(List<String> dataSource, List<String> searchItems) {
        int numItemsFound = 0;
        for (String searchItem : searchItems) {
            int left = 0;
            int right = dataSource.size() - 1;

            while (left <= right) {
                int middle = (left + right) / 2;
                String datum = Main.getNameFromTuple(dataSource, middle);
                if (searchItem.equals(datum)) {
                    numItemsFound++;
                    break;
                }
                if (searchItem.compareTo(datum) > 0) {
                    left = middle + 1;
                } else {
                    right = middle - 1;
                }
            }
        }
        return numItemsFound;
    }
}
