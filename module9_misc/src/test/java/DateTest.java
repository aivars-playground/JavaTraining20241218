import org.junit.jupiter.api.Test;

import java.time.*;
import java.time.temporal.*;

public class DateTest {
    @Test
    public void dateTest() {
        LocalDate startDate = LocalDate.of(2020, 1, 1);
        LocalDate endDate = LocalDate.now();

        Period timePassed = Period.between(startDate, endDate);

        System.out.println("timePassed:\n" +
                "years:"+ timePassed.getYears() +"\n"+
                "months:"+ timePassed.getMonths() +"\n"+
                "days:"+ timePassed.getDays()
            );

        Duration duration = Duration.between(startDate.atStartOfDay(), endDate.atStartOfDay());
        System.out.println("duration:\n" +
                "in days:"+ (duration.toMillis()/ (1000*60*60*24) ) +"\n"
        );

    }

    @Test
    public void dateTestAdjustments() {

        LocalDate now = LocalDate.now();

        System.out.println("next monday:"+now.with(TemporalAdjusters.nextOrSame(DayOfWeek.MONDAY)));

        TemporalAdjuster taxYearStartDate = new TemporalAdjuster() {
            @Override
            public Temporal adjustInto(Temporal temporal) {
                var currentMonth = temporal.get(ChronoField.MONTH_OF_YEAR);
                var currentDayOfMonth = temporal.get(ChronoField.DAY_OF_MONTH);
                var currentYear = temporal.get(ChronoField.YEAR);

                var tyStartMonth = Month.APRIL;
                var tyStartDay = 6;
                int tyYear =(currentMonth >= Month.APRIL.getValue() && currentDayOfMonth >= 6)? currentYear : currentYear -1;

                var result = temporal
                        .with(ChronoField.YEAR, tyYear)
                        .with(ChronoField.MONTH_OF_YEAR,tyStartMonth.getValue())
                        .with(ChronoField.DAY_OF_MONTH,tyStartDay);

                if (temporal.isSupported(ChronoField.NANO_OF_DAY)) {
                    result = result.with(ChronoField.NANO_OF_DAY, 0);
                }

                return result;
            }
        };


        System.out.println("tax year:"+now.with(taxYearStartDate));

        LocalDateTime startTime = LocalDateTime.now();

        System.out.println("start:"+startTime+" tax year:"+startTime.with(taxYearStartDate));

    }

}
