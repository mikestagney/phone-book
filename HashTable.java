package phonebook;

import java.util.List;

public class HashTable {

    String[] table;

        HashTable(List<String> dataSource){
            table = new String[dataSource.size() * 2];

            for (int i = 0; i < dataSource.size(); i++) {
                String datum = Main.getNameFromTuple(dataSource, i);
                int hash = Math.abs(datum.hashCode());

            }



    }


}
