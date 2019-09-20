package lambdasinaction.chap12;

import static java.time.temporal.TemporalAdjusters.lastDayOfMonth;
import static java.time.temporal.TemporalAdjusters.nextOrSame;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.chrono.JapaneseDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAdjuster;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class DateTimeExamples {

    private static final ThreadLocal<DateFormat> formatters = new ThreadLocal<DateFormat>() {
        protected DateFormat initialValue() {
            return new SimpleDateFormat("dd-MMM-yyyy");
        }
    };

    public static void main(String[] args) {
        useInstant();
        useOldDate();
        useLocalDate();
        useTemporalAdjuster();
        useDateFormatter();
        useZoneId();
    }

    private static void useZoneId() {
        System.out.println("=========useZoneId==============");
        ZoneId zoneId = ZoneId.of("Europe/Rome");
        System.out.println(zoneId);
        //TimeZone是老式API
        ZoneId zoneId1 = TimeZone.getDefault().toZoneId();
        System.out.println(zoneId1);
        ZoneId zoneId2 = ZoneId.systemDefault();
        System.out.println(zoneId2);

        LocalDate date = LocalDate.of(2019, 9, 20);
        ZonedDateTime zonedDateTime = date.atStartOfDay(zoneId);
        System.out.println(zonedDateTime);
        ZonedDateTime zonedDateTime1 = date.atStartOfDay(zoneId1);
        System.out.println(zonedDateTime1);

        LocalDateTime dateTime = LocalDateTime.of(2019, 9, 20, 17, 24);
        ZonedDateTime zonedDateTime2 = dateTime.atZone(zoneId);
        System.out.println(zonedDateTime2);

        Instant instant = Instant.now();
        ZonedDateTime zonedDateTime3 = instant.atZone(zoneId);
        System.out.println(zonedDateTime3);
    }

    private static void useInstant() {
        Instant i1 = Instant.ofEpochSecond(2);
        Instant i2 = Instant.ofEpochSecond(2, 3);
        Instant i3 = Instant.ofEpochSecond(2, 1_000_000_000);
        Instant i4 = Instant.ofEpochSecond(2, -1_000_000_000);

        System.out.println(i1);
        System.out.println(i2);
        System.out.println(i3);
        System.out.println(i4);
        System.out.println(Instant.now());

        //Duration主要用于秒和纳秒之间的间隔
        Duration d1 = Duration.between(LocalDateTime.of(2019, 9, 19, 16, 1, 1),
                LocalDateTime.of(2019, 9, 19, 16, 3, 0));
        System.out.println(d1.getSeconds());
        Duration d2 = Duration.between(LocalTime.of(16, 1, 1), LocalTime.of(16, 3, 3));
        System.out.println(d2.getSeconds());
        Duration d3 = Duration.between(Instant.ofEpochSecond(2), Instant.ofEpochSecond(100));
        System.out.println(d3.getSeconds());

        //Period主要用于年、月、日之间的间隔
        Duration threeMinutes = Duration.ofMinutes(3);
        Duration threeSeconds = Duration.of(3, ChronoUnit.SECONDS);

        Period tenDays = Period.ofDays(10);
        Period threeWeeks = Period.ofWeeks(3);
        Period twoYearsSixMonthsOneDay = Period.of(2, 6, 1);
        System.out.println(tenDays);
        System.out.println(tenDays.getDays());

    }


    private static void useOldDate() {
        System.out.println("=========useOldDate==============");
        Date date = new Date(114, 2, 18);
        System.out.println(date);

        System.out.println(formatters.get().format(date));

        Calendar calendar = Calendar.getInstance();
        calendar.set(2014, Calendar.FEBRUARY, 18);
        System.out.println(calendar);
    }

    private static void useLocalDate() {
        System.out.println("=========useLocalDate==============");
        LocalDate date = LocalDate.of(2014, 3, 18);
        int year = date.getYear(); // 2014
        Month month = date.getMonth(); // MARCH
        int day = date.getDayOfMonth(); // 18
        DayOfWeek dow = date.getDayOfWeek(); // TUESDAY
        int len = date.lengthOfMonth(); // 31 (days in March)
        boolean leap = date.isLeapYear(); // false (not a leap year)
        System.out.println(date);

        int y = date.get(ChronoField.YEAR);
        int m = date.get(ChronoField.MONTH_OF_YEAR);
        int d = date.get(ChronoField.DAY_OF_MONTH);

        LocalTime time = LocalTime.of(13, 45, 20); // 13:45:20
        int hour = time.getHour(); // 13
        int minute = time.getMinute(); // 45
        int second = time.getSecond(); // 20
        System.out.println(time);

        LocalDateTime dt1 = LocalDateTime.of(2014, Month.MARCH, 18, 13, 45, 20); // 2014-03-18T13:45
        LocalDateTime dt2 = LocalDateTime.of(date, time);
        LocalDateTime dt3 = date.atTime(13, 45, 20);
        LocalDateTime dt4 = date.atTime(time);
        LocalDateTime dt5 = time.atDate(date);
        System.out.println(dt1);

        LocalDate date1 = dt1.toLocalDate();
        System.out.println(date1);
        LocalTime time1 = dt1.toLocalTime();
        System.out.println(time1);

        Instant instant = Instant.ofEpochSecond(44 * 365 * 86400);
        Instant now = Instant.now();

        Duration d1 = Duration.between(LocalTime.of(13, 45, 10), time);
        Duration d2 = Duration.between(instant, now);
        System.out.println(d1.getSeconds());
        System.out.println(d2.getSeconds());

        Duration threeMinutes = Duration.of(3, ChronoUnit.MINUTES);
        System.out.println(threeMinutes);

        JapaneseDate japaneseDate = JapaneseDate.from(date);
        System.out.println(japaneseDate);
    }

    private static void useTemporalAdjuster() {
        System.out.println("=========useTemporalAdjuster==============");
        LocalDate date = LocalDate.of(2014, 3, 18);
        date = date.with(nextOrSame(DayOfWeek.SUNDAY));
        System.out.println(date);
        date = date.with(lastDayOfMonth());
        System.out.println(date);

        date = date.with(new NextWorkingDay());
        System.out.println(date);
        date = date.with(nextOrSame(DayOfWeek.FRIDAY));
        System.out.println(date);
        date = date.with(new NextWorkingDay());
        System.out.println(date);

        date = date.with(nextOrSame(DayOfWeek.FRIDAY));
        System.out.println(date);
        date = date.with(temporal -> {
            DayOfWeek dow = DayOfWeek.of(temporal.get(ChronoField.DAY_OF_WEEK));
            int dayToAdd = 1;
            if (dow == DayOfWeek.FRIDAY) dayToAdd = 3;
            if (dow == DayOfWeek.SATURDAY) dayToAdd = 2;
            return temporal.plus(dayToAdd, ChronoUnit.DAYS);
        });
        System.out.println(date);
    }

    private static class NextWorkingDay implements TemporalAdjuster {
        @Override
        public Temporal adjustInto(Temporal temporal) {
            DayOfWeek dow = DayOfWeek.of(temporal.get(ChronoField.DAY_OF_WEEK));
            int dayToAdd = 1;
            if (dow == DayOfWeek.FRIDAY) dayToAdd = 3;
            if (dow == DayOfWeek.SATURDAY) dayToAdd = 2;
            return temporal.plus(dayToAdd, ChronoUnit.DAYS);
        }
    }

    private static void useDateFormatter() {
        System.out.println("=========useDateFormatter==============");
        LocalDate date = LocalDate.of(2014, 3, 18);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter italianFormatter = DateTimeFormatter.ofPattern("d. MMMM yyyy", Locale.ITALIAN);

        System.out.println(date.format(DateTimeFormatter.ISO_LOCAL_DATE));
        System.out.println(date.format(formatter));
        System.out.println(date.format(italianFormatter));

        DateTimeFormatter complexFormatter = new DateTimeFormatterBuilder()
                .appendText(ChronoField.DAY_OF_MONTH)
                .appendLiteral("... ")
                .appendText(ChronoField.MONTH_OF_YEAR)
                .appendLiteral(" ### ")
                .appendText(ChronoField.YEAR)
                .parseCaseInsensitive()
                .toFormatter(Locale.CHINESE);

        System.out.println(date.format(complexFormatter));
    }

}
