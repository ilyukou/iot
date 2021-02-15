package by.grsu.iot.model.util;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ModelUtil {

    public static <T> boolean listEqualsWithOrder(List<T> list1, List<T> list2) {
        return list1.equals(list2);
    }

    public static <T> boolean listEqualsIgnoreOrder(List<T> list1, List<T> list2) {
        return new HashSet<>(list1).equals(new HashSet<>(list2));
    }

    public static <T> boolean setEqualsIgnoreOrder(Set<T> set1, Set<T> set2) {
        return set1.equals(set2);
    }
}
