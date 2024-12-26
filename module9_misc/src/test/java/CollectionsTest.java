import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.function.Function;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class CollectionsTest {

    private String[] easyAppend(String[] array, String element) {
        var newArray = Arrays.copyOf(array, array.length + 1);
        newArray[array.length] = element;
        return newArray;
    }

    @Test
    void arrays_add() {
        String[] array = {"a", "bb", "ccc"};

        System.out.println(Arrays.binarySearch(array, "ccc"));

        Arrays.stream(array).filter(s -> s.length() == 2).findFirst().ifPresent(System.out::println);

        var newArray = easyAppend(array, "dddd");
        System.out.println(Arrays.toString(newArray));
    }

    @Test
    void arrays_modify_easier() {
        String[] array = {"a", "bb", "ccc"};

        var list = new ArrayList<String>(List.of(array));
        list.remove("a");
        list.remove("a");//again
        list.add("dddd");

        var newArray = list.toArray(String[]::new);
        System.out.println(Arrays.toString(newArray));
    }

    @Test
    void arrays_primitive_remove() {
        int[] array = {1, 2, 3};
        int[] newArray = Arrays.stream(array)
                .filter((int i) -> i % 2 ==1)
                .toArray();
        System.out.println(Arrays.toString(newArray));

        int[] withListStep = Arrays.stream(array)
                .boxed()
                .filter((Integer i) -> i % 2 == 1)
                .mapToInt(Integer::intValue)
                .toArray();
        System.out.println(Arrays.toString(withListStep));
    }

    @Test
    void set_operations() {

        var setA = Set.of("a", "b", "c");
        var setB = Set.of("c", "d", "e");

        var thisWillContainIntersection = new HashSet<>(setA);
        thisWillContainIntersection.retainAll(setB);

        System.out.println(thisWillContainIntersection + " belong to setA:" + setA + " and setB:" + setB );

        var thisWillContainUnion = new HashSet<>(setA);
        thisWillContainUnion.addAll(setB);
        System.out.println(thisWillContainUnion + " belong to either  setA:" + setA + " or setB:" + setB + " or both" );

        var thisWillContainDiff = new HashSet<>(thisWillContainUnion);
        thisWillContainDiff.removeAll(thisWillContainIntersection);
        System.out.println(thisWillContainDiff + " belong to only  setA:" + setA + " or setB:" + setB);

    }

    @Test
    void mapping() {
        List<Double> doubles = new ArrayList<>(List.of(10.525, 10.535));
        System.out.println(doubles);

        //bankers rounding
        UnaryOperator<Double> roundingOperator = d -> BigDecimal.valueOf(d).setScale(2, RoundingMode.HALF_EVEN).doubleValue();
        doubles.replaceAll(roundingOperator);
        System.out.println(doubles);
    }

    @Test
    void sorting() {
        List<String> names = new ArrayList<>(List.of("yyy", "AAA", "ddd", "aa"));
        names.sort(String::compareTo);
        System.out.println(names);

        names.sort(Comparator.comparing(String::length).thenComparing(String::compareTo));
        System.out.println(names);

        names.sort(Comparator.comparing(String::length).reversed().thenComparing(String::compareTo));
        System.out.println(names);
    }

    @Test
    void sorting_not_modified() {
        List<String> names = new ArrayList<>(List.of("yyy", "AAA", "ddd", "aa"));

        List<String> sorted = names.stream().sorted(Comparator.comparing(String::length).thenComparing(String::compareTo)).toList();

        System.out.println(sorted);
    }

    @Test
    void sorting_nulls() {
        List<String> namesWithNulls = new ArrayList<>(Arrays.asList("yyy", "AAA", null, "ddd", "aa"));


        //sorting nulls fails
        assertThrows(
                NullPointerException.class,
                () -> namesWithNulls.sort(String::compareTo)
        );


        //sorting nullsFirst / nullsLast is null safe
        namesWithNulls.sort(Comparator.nullsFirst(String::compareTo));
        System.out.println(namesWithNulls);

        var filterNullsSortes = namesWithNulls.stream()
                .filter(Objects::nonNull)
                .sorted(String::compareTo)
                .toList();

        System.out.println(filterNullsSortes);
    }

    @Test
    void frequency() {
        List<String> names = List.of("yyy", "AAA", "ddd", "aa","aa");
        System.out.println(Collections.frequency(names, "aa"));

        var statistics = names.stream().collect(
                Collectors.groupingBy(
                        Function.identity(),
                        Collectors.counting()
        ));

        System.out.println(statistics);
    }

    @Test
    void map_views() {
        var alpha = new HashMap<>(Map.of(1,"a",2,"b", 3, "c",4,"d", 5, "e",7,"e",9,"e"));

        System.out.println(alpha);
        alpha.keySet().removeIf(key -> key % 2 == 0);
        System.out.println("even ordinals " + alpha);

        alpha.values().removeIf(a -> Collections.frequency(alpha.values(),a) > 1);
        System.out.println("remove duplicated " + alpha); //can we guarantee order???? nope... will leave one random entry with duplicate
    }


    @Test
    void map_equality() {
        Map<Integer,String> map1 = Map.of(1, "a",2, "b", 3, "c");
        Map<Integer,String> map2 = Map.of(3, "d", 4,"e");

        Map<Integer,String> toBeOverwritten = new HashMap<>(map1);
        toBeOverwritten.putAll(map2);
        System.out.println(toBeOverwritten);

        Map<Integer,String> toBeMerged = Stream.of(map1, map2)
                .flatMap(m -> m.entrySet().stream())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (v1,v2) -> v1+v2));

        System.out.println(toBeMerged);
    }
}
