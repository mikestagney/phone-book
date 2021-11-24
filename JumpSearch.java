package phonebook;

import java.util.List;

public class JumpSearch {


    public static int search(List<String> dataSource, List<String> searchItems) {
        int numItemsFound = 0;
        int jumpInterval = (int) Math.floor(Math.sqrt(dataSource.size()));
        for (String name : searchItems) {
            for (int i = 0; i < dataSource.size(); i += jumpInterval) {
                String indexName = Main.getNameFromTuple(dataSource, i);
                if (name.equals(indexName)) {
                    numItemsFound++;
                    break;
                }
                if (name.compareTo(indexName) > 0) {
                    continue;
                }
                if (backwardsSearch(dataSource, name, i - 1, i - jumpInterval)) {
                    numItemsFound++;
                    break;
                }
                if (i + jumpInterval >= dataSource.size() &&
                    backwardsSearch(dataSource, name, dataSource.size() - 1, i)) {
                        numItemsFound++;
                        break;
                }
                break;
                }
            }
        return numItemsFound;
    }
    public static boolean backwardsSearch(List<String> dataSource, String name, int startIndex, int endIndex) {
        boolean itemFound = false;
        for (int j = startIndex; j > endIndex; j--) {
            String countBackFromEndName = Main.getNameFromTuple(dataSource, j);
            if (name.equals(countBackFromEndName)) {
                itemFound = true;
                break;
            }
        }
        return itemFound;
    }
}