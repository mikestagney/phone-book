package phonebook;

import java.util.List;

public class HashTable {

    String[] hashTable;

        HashTable(List<String> dataSource){
            hashTable = new String[dataSource.size() * 2];

            for (int i = 0; i < dataSource.size(); i++) {
                String datum = Main.getNameFromTuple(dataSource, i);
                int hash = hashFunction(datum);

                if (hashTable[hash] != null) {
                    String holder = hashTable[hash];
                    holder += " "  + dataSource.get(i);
                    hashTable[hash] = holder;
                } else {
                    hashTable[hash] = dataSource.get(i);
                }
            }
    }

    private int hashFunction(String datum) {
        int hash = 7;
        for (int j = 0; j < datum.length(); j++) {
            hash = hash * 31 + datum.charAt(j);
        }
        return Math.abs(hash % hashTable.length);
    }
    public int search(List<String> searchItems) {
            int numberItemsFound = 0;
            for (String item: searchItems) {
                int hashIndex = hashFunction(item);
                if (hashTable[hashIndex].contains(item)) {
                    numberItemsFound++;
                }
            }
            return numberItemsFound;
    }

}
