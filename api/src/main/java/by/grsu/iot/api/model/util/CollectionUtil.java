package by.grsu.iot.api.model.util;

import java.util.List;
import java.util.Set;
import java.util.*;

import static io.vavr.API.*;

/**
 * Collection Util
 *
 * @author Ilyukou Ilya
 */
public class CollectionUtil {

    public static <T> List<T> samplingList(List<T> list, Double size) {
        if (list.size() <= size) {
            return list;
        }

        Double listSize = (double) list.size();

        Double chance = size / listSize;

        List<T> result = new ArrayList<>();

        for (T t : list) {
            if (getRandomBoolean(chance)) {
                result.add(t);
            }
        }

        return result;
    }

    private static boolean getRandomBoolean(Double chance) {
        return Math.random() < chance;
    }

    public static <T> boolean listEqualsIgnoreOrder(List<T> list1, List<T> list2) {
        return new HashSet<>(list1).equals(new HashSet<>(list2));
    }

    public static <T> boolean hasListDuplicates(List<T> list) {
        return !areAllUnique(list);
    }

    public static <T> boolean areAllUnique(List<T> list) {
        Set<T> set = new HashSet<>();

        for (T t : list) {
            if (!set.add(t))
                return false;
        }

        return true;
    }

    public static Integer getCountOfArrayPortion(Integer arraySize, Long portionSize) {
        return Match(true).of(
                Case($(arraySize < 0 || portionSize <= 0), i -> {
                    throw new IllegalArgumentException("Number must be more than 0. " +
                            "arraySize={" + arraySize + "}, " +
                            "portionSize={" + portionSize + "}");
                }),
                Case($(arraySize == 0), 0),
                Case($(arraySize <= portionSize), 1),
                Case($(true), new Double(Math.ceil(arraySize.doubleValue() / portionSize.doubleValue())).intValue()));
    }

    public static <T> List<T> getArrayFromTo(Long from, Long to, List<T> array) {
        if (from > to) {
            throw new IllegalArgumentException("From more than to");
        }

        if (from > array.size()) {
            throw new IllegalArgumentException("From more than List size");
        }

        return array.subList(from.intValue(), (Match(true).of(
                Case($(to > array.size()), array.size()),
                Case($(to <= array.size()), to)
        )).intValue());
    }

    public static <T> Set<T> getSharedSetKeys(Set<T> set1, Set<T> set2) {
        Set<T> result = new LinkedHashSet<>();

        Set<T> smallSet;
        Set<T> bigSet;

        if (set1.size() > set2.size()) {
            bigSet = set1;
            smallSet = set2;
        } else {
            bigSet = set2;
            smallSet = set1;
        }

        for (T value : smallSet) {
            if (bigSet.contains(value)) {
                result.add(value);
            }
        }

        return result;
    }
}
