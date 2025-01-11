import java.text.NumberFormat;
import java.util.Currency;
import java.util.Locale;

public class CurrencyDemo {
    public static void main(String[] args) {

        var locales = Locale.availableLocales();
        locales.forEach(System.out::println);

        var cur = Currency.getAvailableCurrencies();
        cur.stream().map(c -> "c:"+c.getCurrencyCode() + " n:"+c.getDisplayName() + " s:" + c.getSymbol() + " d:"+c.getDefaultFractionDigits()).forEach(System.out::println);

        var lv = Locale.of("lv","LV");
        System.out.println(Currency.getInstance(lv));

        Locale locale = Locale.getDefault();
        System.out.println(Currency.getInstance(locale));
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(locale);

        System.out.println(currencyFormatter.format(10.234));

    }
}
