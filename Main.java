package phonebook;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) {
        // /Users/mikestagney/Downloads/small_directory.txt
        String dataFilePath = "/Users/mikestagney/Downloads/directory.txt";
        File dataFile = new File(dataFilePath);
        List<String> dataSource = new ArrayList<>();
        // /Users/mikestagney/Downloads/small_find.txt
        String searchFilePath = "/Users/mikestagney/Downloads/find.txt";
        File searchFile = new File(searchFilePath);
        List<String> searchItems = new ArrayList<>();

        try (Scanner scanner = new Scanner(dataFile)) {
            while (scanner.hasNext()) {
                dataSource.add(scanner.nextLine());
            }
        } catch (FileNotFoundException e) {
            System.out.println("Data file not found!");
        }
        try (Scanner scanner = new Scanner(searchFile)) {
            while (scanner.hasNext()) {
                searchItems.add(scanner.nextLine());
            }
        } catch (FileNotFoundException e) {
            System.out.println("Search items file not found!");
        }

        System.out.println("Start searching...");
        int numberItemsFind = searchItems.size();
        long startTime = System.currentTimeMillis();
        int numItemsFound = 0;

        for (int i = 0; i < numberItemsFind; i++) {
            String[] searchTerm = searchItems.get(i).split(" ");
            for (int j = 0; j < dataSource.size(); j++) {
                String[] datum = dataSource.get(j).split(" ");
                if (searchTerm[0].equals(datum[1])) {
                    if (datum.length > 2 && searchTerm[1].equals(datum[2])) {
                        numItemsFound++;
                        break;
                    } else if (datum.length == 2){
                        numItemsFound++;
                        break;
                    }
                }
            }
        }
        long timeTaken = System.currentTimeMillis() - startTime;

        long minutes = TimeUnit.MILLISECONDS.toMinutes(timeTaken);
        long seconds = (TimeUnit.MILLISECONDS.toSeconds(timeTaken) % 60);
        long milliseconds = (timeTaken - minutes - seconds) % 60;

        System.out.printf("Found %d / %d entries. Time taken: %d min. %d sec. %d ms.\n",
                numItemsFound, numberItemsFind, minutes, seconds, milliseconds );

    }
    /*
    public static void readFromFile(File fileName, List<String> ) {
        try (Scanner scanner = new Scanner(fileName)) {
            while (scanner.hasNext()) {
                dataSource.add(scanner.nextLine());
            }
        } catch (FileNotFoundException e) {
            System.out.println("Data file not found!");
        }
    }*/

}
