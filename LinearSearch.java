package phonebook;

import java.util.List;

public class LinearSearch {

    public static int go(List<String> dataSource, List<String> searchItems) {
        int numItemsFound = 0;
        for (String searchItem : searchItems) {
            for (String tuple : dataSource) {
                String[] datum = tuple.split(" ", 2);
                if (searchItem.equals(datum[1])) {
                    numItemsFound++;
                    break;
                }
            }
        }
        return numItemsFound;
    }
}