import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

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

    @Test
    void test_shift() {
        int seven = 7;
        System.out.println("7/2 = " + (seven)/2);
        System.out.println("7 >> 1 = " + (seven >> 1));

        System.out.println("-7/2 = " + (-seven)/2);           //-3
        System.out.println("-7 >> 1 = " + ((-seven) >> 1));   //-4

        System.out.println("7 >>> 1 = " + (seven >>> 1));
        System.out.println("-7 >>> 1 = " + ((-seven) >>> 1) ); //2147483644 ??????

    }

    @Test
    void test_float() {
        System.out.println("Max int:" + Integer.MAX_VALUE );
        System.out.println("Max flt:" + BigDecimal.valueOf(Float.MAX_VALUE).toPlainString() );

        float maxI2F = Integer.MAX_VALUE;
        System.out.println("Max int to F:" + maxI2F );
        System.out.println("Max maxI2F:" + BigDecimal.valueOf(maxI2F).toPlainString() );

        float o1 = 0.1f;
        float o3 =  0.3f;
        float o2 =  0.2f;
        System.out.println("o.3 - o.11 float:" + (o3 - o1) + " o2:" +o2 );

        System.out.println("(o.3 - o.1) == o.2 :" + ((o3 - o1) == o2) );

    }

}
