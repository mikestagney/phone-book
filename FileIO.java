package phonebook;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileIO {

    public static List<String> readFromFile(String fileName) {
        File dataFile = new File(fileName);
        List<String> list = new ArrayList<>();
        try (Scanner scanner = new Scanner(dataFile)) {
            while (scanner.hasNext()) {
                list.add(scanner.nextLine());
            }
        } catch (FileNotFoundException e) {
            System.out.println(dataFile + " file not found!");
        }
        return list;
    }
}
