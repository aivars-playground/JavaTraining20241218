import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.shadow.com.univocity.parsers.annotations.Copy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class C03_Datatypes {

    @Test
    void simple_collections() {
        List<String> abc = new ArrayList<>();
        abc.add("abc");
        System.out.println("abc = " + System.identityHashCode(abc));
        abc.add("cde");
        System.out.println("abc = " + System.identityHashCode(abc));
        abc.add("efg");
        System.out.println("abc = " + System.identityHashCode(abc));

        System.out.println("abc = " + abc);

        for (String s : abc) {
            if (s.equals("cde")) {
                abc.remove(s);
                abc.add("not cde");  //nope cannot add
            }
        }

        System.out.println("abc new = " + abc);
    }

    @Test
    void copyonwrite_collections_modifable_on_loop() {
        List<String> abc = new CopyOnWriteArrayList<>();
        abc.add("abc");
        System.out.println("abc = " + System.identityHashCode(abc));
        abc.add("cde");
        System.out.println("abc = " + System.identityHashCode(abc));
        abc.add("efg");
        System.out.println("abc = " + System.identityHashCode(abc));

        System.out.println("abc = " + abc);

        for (String s : abc) {
            if (s.equals("cde")) {
                abc.remove(s);
                abc.add("not cde");
            }
        }

        System.out.println("abc new = " + abc);
    }

    @Test
    void sync_collections_not_for_loop() {
        List<String> abc = Collections.synchronizedList(new ArrayList<>());

        abc.add("abc");
        System.out.println("abc = " + System.identityHashCode(abc));
        abc.add("cde");
        System.out.println("abc = " + System.identityHashCode(abc));
        abc.add("efg");
        System.out.println("abc = " + System.identityHashCode(abc));

        for (String s : abc) {
            if (s.equals("cde")) {
                abc.remove(s);         //nope cannot remove
                abc.add("not cde");
            }
        }
        System.out.println("abc new = " + abc);

    }
}
