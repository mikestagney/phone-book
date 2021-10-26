package phonebook;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

// test file names
// /Users/mikestagney/Downloads/small_directory.txt
// /Users/mikestagney/Downloads/small_find.txt

public class Main {
    public static void main(String[] args) {
        String dataFilePath = "/Users/mikestagney/Downloads/directory.txt";
        File dataFile = new File(dataFilePath);
        List<String> dataSource = readFromFile(dataFile);

        String searchFilePath = "/Users/mikestagney/Downloads/find.txt";
        File searchFile = new File(searchFilePath);
        List<String> searchItems = readFromFile(searchFile);

        System.out.println("Start searching...");
        int numberItemsToFind = searchItems.size();
        long startTime = System.currentTimeMillis();
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
        long timeTaken = System.currentTimeMillis() - startTime;

        long minutes = TimeUnit.MILLISECONDS.toMinutes(timeTaken);
        long seconds = (TimeUnit.MILLISECONDS.toSeconds(timeTaken) % 60);
        long milliseconds = (timeTaken - minutes - seconds) % 1000;

        System.out.println("Total milliseconds " + timeTaken);
        System.out.printf("Found %d / %d entries. Time taken: %d min. %d sec. %d ms.\n",
                numItemsFound, numberItemsToFind, minutes, seconds, milliseconds );

    }

    public static List<String> readFromFile(File fileName) {
        List<String> list = new ArrayList<>();
        try (Scanner scanner = new Scanner(fileName)) {
            while (scanner.hasNext()) {
                list.add(scanner.nextLine());
            }
        } catch (FileNotFoundException e) {
            System.out.println("Data file not found!");
        }
        return list;
    }

}
