import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

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
    void test_float_overflow() {
        float max = Float.MAX_VALUE;  //0x1.fffffeP+127     3.4028235E38
        float min = Float.MIN_VALUE;  //0x1.fffffeP+127     1.4E-45f
        System.out.println("max = " + max + " min = " + min);

        float s = max + min;
        System.out.println("s = " + s);

        float s1 = max + 1E32f;
        System.out.println("s1 = " + s1); //infinity


        float aa = 0x1.AA11eP+1f;
        System.out.println("aa = " + aa);

        Float f = 4.5f;
        int i = Float.floatToIntBits( f );
        System.out.printf( "X = %X%n", i );

        String f1 = "40900000";
        Integer f1i = Integer.parseInt(f1, 16);
        Float f2 = Float.intBitsToFloat(f1i);
        System.out.println("f1 = " + f2);
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

        System.out.println(" ==" + (BigDecimal.valueOf(0.3f).subtract(BigDecimal.valueOf(0.1f))).toPlainString() );
        System.out.println(" ==" + (BigDecimal.valueOf(3,1).subtract(BigDecimal.valueOf(1,1))).toPlainString() );
        System.out.println(" ==" + (BigDecimal.valueOf(3,1).
                divide(BigDecimal.valueOf(7,0), new MathContext(3, RoundingMode.HALF_EVEN)).toPlainString() ));

        System.out.println(" ==" + (BigDecimal.valueOf(3,1).
                divide(BigDecimal.valueOf(7,0), MathContext.DECIMAL32).setScale(3, RoundingMode.HALF_EVEN) ).toPlainString() );

        System.out.println(" ==" + (BigDecimal.valueOf(300,0).
                multiply(BigDecimal.valueOf(7,0), new MathContext(0, RoundingMode.HALF_EVEN)).toPlainString() ));



        System.out.println(" ==" + (BigDecimal.valueOf(202,2).
                multiply(BigDecimal.valueOf(202,2)).toPlainString() ));

    }

}
