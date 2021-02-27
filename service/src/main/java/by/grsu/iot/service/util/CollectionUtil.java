package by.grsu.iot.service.util;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CollectionUtil {

    public static <T> boolean hasListDuplicates(List<T> list){
        return !areAllUnique(list);
    }

    public static <T> boolean areAllUnique(List<T> list){
        Set<T> set = new HashSet<>();

        for (T t: list){
            if (!set.add(t))
                return false;
        }

        return true;
    }
}
