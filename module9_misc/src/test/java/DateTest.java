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

    @Test
    public void dateInstantZonedLocal() {
        System.out.println(Instant.now());
        System.out.println(LocalDateTime.now());

        System.out.println(ZoneId.getAvailableZoneIds());
        System.out.println("Rarotonga time:"+LocalDateTime.now(ZoneId.of("Pacific/Rarotonga")));


        LocalTime timeNow = LocalTime.now();
        LocalDate dateNow = LocalDate.now();
        LocalDateTime now = LocalDateTime.of(dateNow, timeNow);
        System.out.println("now:" +now);

        LocalDateTime timeMin = LocalDateTime.of(dateNow, LocalTime.MIN);
        LocalDateTime timeMidnight = LocalDateTime.of(dateNow, LocalTime.MIDNIGHT);
        LocalDateTime timeNOON = LocalDateTime.of(dateNow, LocalTime.NOON);
        LocalDateTime timeMax = LocalDateTime.of(dateNow, LocalTime.MAX);

        System.out.println(
                timeMin + " timeMin:\n" +
                timeMidnight + " timeMidnight:\n" +
                timeNOON + " timeNOON:\n" +
                timeMax+ " timeMax:\n"
        );
    }

    @Test
    public void dateZones() {

        var scheduledDepature = LocalDateTime.of(2025,1,1,6, 30);
        var arrival = scheduledDepature.plusHours(6);

        var departLondonTime = scheduledDepature.atZone(ZoneId.of("Europe/London"));
        var arriveLondonTime = arrival.atZone(ZoneId.of("Europe/London"));

        var arriveNyTIme = arriveLondonTime.withZoneSameInstant(ZoneId.of("America/New_York"));

        System.out.println("depart from London, local time:"+ departLondonTime);
        System.out.println("arrive at New_York, local time:"+ arriveNyTIme);

        System.out.println("arrive at New_York, London time:"+ arriveLondonTime);
    }

    @Test
    public void dateFlightRecord() {

        record Flight(String from, String to, LocalDateTime departure, int duration) {
            @Override
            public String toString() {
                ZoneId fromZone = ZoneId.of(from);
                ZoneId toZone = ZoneId.of(to);

                var departTime = departure.atZone(fromZone);
                var arrivalTime =departure.plusHours(duration)
                        .atZone(fromZone)
                        .withZoneSameInstant(toZone);

                return String.format("Flight departs at %s, and arrives at %s", departTime, arrivalTime);
            }
        }

        System.out.println("Flights:");
        var departureLondonToNy = LocalDateTime.of(2025,1,1,6, 30);
        System.out.println("leg1:"+new Flight("Europe/London", "America/New_York", departureLondonToNy,8));
        var departureNyToYakutat = LocalDateTime.of(2025,1,1,12, 0);
        System.out.println("leg2:"+new Flight("America/New_York", "America/Yakutat",departureNyToYakutat,4));
    }
}
