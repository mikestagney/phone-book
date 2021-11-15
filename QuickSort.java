package phonebook;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class QuickSort {

    public static List<String> sort(List<String> list) {
        if (list.size() <= 1) {
            return list;
        }
        String pivot = Main.getNameFromTuple(list, list.size() - 1);
        List<String> lower = new ArrayList<>();
        List<String> upper = new ArrayList<>();


        for (int i = 0; i < list.size() - 1; i++) {
            if (Main.getNameFromTuple(list, i).compareTo(pivot) > 0) {
                upper.add(list.get(i));
            } else {
                lower.add(list.get(i));
            }
        }

        lower = sort(lower);
        upper = sort(upper);
        lower.add(list.get(list.size() - 1));

        return Stream.of(lower, upper)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }

}
