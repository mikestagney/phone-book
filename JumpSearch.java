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
                for (int j = i - 1; j > i - jumpInterval; j--) {
                    String countBackName = Main.getNameFromTuple(dataSource, j);
                    if (name.equals(countBackName)) {
                        numItemsFound++;
                        break;
                    }
                }
                if (i + jumpInterval >= dataSource.size()) {
                    for (int j = dataSource.size() - 1; j > i; j--) {
                        String countBackFromEndName = Main.getNameFromTuple(dataSource, j);
                        if (name.equals(countBackFromEndName)) {
                            numItemsFound++;
                            break;
                        }
                    }
                }
                break;
            }
        }
        return numItemsFound;
    }

}
