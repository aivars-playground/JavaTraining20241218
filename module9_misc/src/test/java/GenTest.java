import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class GenTest {

    @Test
    void testGen() {
        var genClass = new GenClass<MyClass>();
        genClass.testGen(new MyClass());
        genClass.testGen(new MyClass2());
        //genClass.testGen(new NotMyClass());

        var lesserGenClass = new LesserGenClass<MyClass>();
        lesserGenClass.testGen(new MyClass());
        lesserGenClass.testGen(new MyClass2());
        //lesserGenClass.testGen(new NotMyClass());

        List<MyClass> lmq = new ArrayList<>();
        lmq.add(new MyClass());
        lmq.add(new MyClass2());

        List<MyClass2> lmq2 = new ArrayList<>();
        //lmq2.add(new MyClass());
        lmq2.add(new MyClass2());


        //List<MyClass> lmqrec = lmq2;
        List<? extends MyClass> lexmq_2 = lmq2;
        List<? extends MyClass> lexmq = lmq;
        lexmq_2.forEach(a -> System.out.println(a.testMy()));
        lexmq.forEach(a -> System.out.println(a.testMy()));


        List<? super MyClass2> lsmq_2 = lmq2;
        List<? super MyClass2> lsmq = lmq;
        lsmq_2.forEach(a -> System.out.println(a instanceof MyClass aa? aa.testMy(): null));
    }
}


class LesserGenClass<T> {
    public void testGen(T in) {
        System.out.println("---in");
    }



    public <A extends T> void addIfPretty(List<A> shapes, A shape) {
        if (shape.equals(shape)) {
            shapes.add(shape);
        }
    }
}

class GenClass<T extends MyClass> {
    public void testGen(T in) {
        System.out.println("---in");
    }
}

class MyClass {
    public String testMy() {return "my";}
}

class MyClass2 extends MyClass {
    public String testMy2() {return "my2";}
}

class NotMyClass {}