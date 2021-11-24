package phonebook;

import java.util.List;

public class LinearSearch {

    public static int search(List<String> dataSource, List<String> searchItems) {
        int numItemsFound = 0;
        for (String searchItem : searchItems) {
            for (int i = 0; i < dataSource.size(); i++) {
                String datum = Main.getNameFromTuple(dataSource, i);
                if (searchItem.equals(datum)) {
                    numItemsFound++;
                    break;
                }
            }
        }
        return numItemsFound;
    }
}