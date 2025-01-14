import org.junit.jupiter.api.Test;

public class Overflow {


    @Test
    void test_overflow() {

        int min = Integer.MIN_VALUE;
        int min_m1 = (min-1);
        int min_p1 = (min+1);

        System.out.println("min = " + min + " min_m1 = " + min_m1 + "; min_p1 = " + min_p1);

        System.out.println("min / -1 = " + (int)(min / (-1)) + " min_m1 / -1 " + (int)(min_m1 / (-1))+ " min_m1 / -1 " + (int)(min_p1 / (-1)));

        System.out.println("min * -1 = " + (int)(min * (-1)) + " min_m1 * -1 " + (int)(min_m1 * (-1))+ " min_m1 / -1 " + (int)(min_p1 * (-1)));

        /// //    Integer.MIN_VALUE  * -1  = Integer.MIN_VALUE  !!!!!!!!!!!!!!!!!!!!!!!!!
        /// //    Integer.MIN_VALUE  / -1  = Integer.MIN_VALUE  !!!!!!!!!!!!!!!!!!!!!!!!!

    }

}
