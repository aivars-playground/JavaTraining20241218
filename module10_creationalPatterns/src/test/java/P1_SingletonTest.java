import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class P1_SingletonTest {

    @Test
    void testBasicSingleton() {
        BasicSingleton singleton1 = BasicSingleton.getInstance();
        System.out.println(singleton1);

        BasicSingleton singleton2 = BasicSingleton.getInstance();
        System.out.println(singleton2);
    }

    @Test
    void testOldschoolDoubleCheckedLazySingleton() {
        OldschoolDoubleCheckedLazySingleton singleton1 = OldschoolDoubleCheckedLazySingleton.getInstance();
        System.out.println(singleton1);
        OldschoolDoubleCheckedLazySingleton singleton2 = OldschoolDoubleCheckedLazySingleton.getInstance();
        System.out.println(singleton2);
    }

    @Test
    void testInitializationOnDemandHolderSingleton() {
        InitializationOnDemandHolderSingleton singleton1 = InitializationOnDemandHolderSingleton.getInstance();
        System.out.println(singleton1);
        InitializationOnDemandHolderSingleton singleton2 = InitializationOnDemandHolderSingleton.getInstance();
        System.out.println(singleton2);
    }

    @Test
    void testInitializationOnDemandHolderSingletonWithErr() {
        //error in static initializer
        assertThrows(
                ExceptionInInitializerError.class,
                () -> InitializationOnDemandHolderSingletonErr.getInstance()
        );
    }

}

//eager
class BasicSingleton {
    private static BasicSingleton instance = new BasicSingleton();

    private BasicSingleton() {}

    public static BasicSingleton getInstance() {
        return instance;
    }
}

//threadsafe lazy
class OldschoolDoubleCheckedLazySingleton {

    private static volatile OldschoolDoubleCheckedLazySingleton instance = null;

    private OldschoolDoubleCheckedLazySingleton() {}

    public static OldschoolDoubleCheckedLazySingleton getInstance() {
        if (instance == null) {
            synchronized(OldschoolDoubleCheckedLazySingleton.class) {
                if (instance == null) {
                    instance = new OldschoolDoubleCheckedLazySingleton();
                }
            }
        }
        return instance;
    }
}

//threadsafe lazy
class InitializationOnDemandHolderSingleton {

    private static class LazyHolder {
        static final InitializationOnDemandHolderSingleton INSTANCE = new InitializationOnDemandHolderSingleton();
    }

    private InitializationOnDemandHolderSingleton() {}

    public static InitializationOnDemandHolderSingleton getInstance() {
        return LazyHolder.INSTANCE;
    }
}

class InitializationOnDemandHolderSingletonErr {

    private static class LazyHolder {
        static final InitializationOnDemandHolderSingletonErr INSTANCE = init();
        private static InitializationOnDemandHolderSingletonErr init() { throw new RuntimeException(); }
    }

    private InitializationOnDemandHolderSingletonErr() {}

    public static InitializationOnDemandHolderSingletonErr getInstance() {
        return LazyHolder.INSTANCE;
    }
}
