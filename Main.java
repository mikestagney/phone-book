package phonebook;

import java.util.Collections;
import java.util.List;


// test file names
// /Users/mikestagney/Downloads/small_directory.txt
// /Users/mikestagney/Downloads/small_find.txt

public class Main {
    public static final String dataFilePath = "/Users/mikestagney/Downloads/small_directory.txt";
    public static final String searchFilePath = "/Users/mikestagney/Downloads/small_find.txt";

    public static void main(String[] args) {
        List<String> dataSource = FileIO.readFromFile(dataFilePath);
        List<String> searchItems = FileIO.readFromFile(searchFilePath);
        int numberItemsToFind = searchItems.size();

        System.out.println("Start searching (linear search)...");
        Timer linearTimer = new Timer();

        int numItemsFound = LinearSearch.go(dataSource, searchItems);

        linearTimer.stopTimer();
        System.out.printf("Found %d / %d entries. Time taken: %d min. %d sec. %d ms.\n",
                numItemsFound, numberItemsToFind, linearTimer.getMinutes(), linearTimer.getSeconds(), linearTimer.getMilliseconds() );

        System.out.println("Start searching (bubble sort + jump search)...");
        Timer jumpTimer = new Timer();
        // add bubble sort
        for (int i = 0; i < dataSource.size(); i++) {
            for (int j = i + 1; j < dataSource.size(); j++) {
                String[] firstTuple = dataSource.get(i).split(" ", 2);
                String firstName = firstTuple[1];
                String[] secondTuple = dataSource.get(j).split(" ",  2);
                String secondName = secondTuple[1];
                if (firstName.compareTo(secondName) > 0) {
                    Collections.swap(dataSource, i, j);
                }
            }
        }
        dataSource.forEach(System.out::println);

        // jump search
        int jumpInterval = (int) Math.floor(Math.sqrt(dataSource.size()));
        for (String name: searchItems) {
            for (int i = 0; i < numberItemsToFind; i += jumpInterval) {



            }
        }


    }

}
