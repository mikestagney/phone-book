package phonebook;

import java.util.Collections;
import java.util.List;

public class BubbleSort {

    public static boolean sort(List<String> bubbleSortJumpSearchList, long linearSearchThreshold) {
        Timer sortTimer = new Timer();
        boolean completeBubbleSort = true;
        int numOfSwaps = -1;
        int counter = 1;
        while (numOfSwaps != 0) {
            numOfSwaps = 0;

            for (int i = 0; i < bubbleSortJumpSearchList.size() - counter; i++) {
                String firstName = Main.getNameFromTuple(bubbleSortJumpSearchList, i);
                String secondName = Main.getNameFromTuple(bubbleSortJumpSearchList, i + 1);
                if (firstName.compareTo(secondName) > 0) {
                    Collections.swap(bubbleSortJumpSearchList, i, i + 1);
                    numOfSwaps++;
                }
            }
            counter++;

            if (sortTimer.getCurrentTime() > linearSearchThreshold) {
                completeBubbleSort = false;
                break;
            }
        }
        return completeBubbleSort;
    }
}
