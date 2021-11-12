package phonebook;

import java.util.List;

public class BinarySearch {

    public static int search(List<String> dataSource, List<String> searchItems) {
        int numItemsFound = 0;
        for (String searchItem : searchItems) {
            int left = 0;
            int right = dataSource.size() - 1;
            int middle = right / 2;

            while (left <= right) {
                String datum = dataSource.get(middle).split(" ", 2)[1];
                if (searchItem.equals(datum)) {
                    numItemsFound++;
                    break;
                }
                if (searchItem.compareTo(datum) > 0) {
                    left = middle + 1;

                } else {
                    right = middle - 1;

                }
                middle = (left + right) / 2;
            }

        }
        return numItemsFound;




    }

}
