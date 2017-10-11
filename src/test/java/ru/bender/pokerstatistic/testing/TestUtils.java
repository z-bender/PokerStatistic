package ru.bender.pokerstatistic.testing;

import ru.bender.pokerstatistic.bankroll.DatePeriod;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;

import static java.lang.System.lineSeparator;
import static org.testng.Assert.assertTrue;

public class TestUtils {

    public static void assertCollectionsEquals(Collection actual, Collection expected) {
        boolean equals = actual.size() == expected.size() && actual.containsAll(expected);
        assertTrue(equals,
                "Failed collections equals" + lineSeparator() +
                        "Actual: " + actual + lineSeparator() +
                        "Expected: " + expected);
    }

    public static LocalDateTime endOfDay(LocalDate date) {
        return date.atTime(23, 59, 59);
    }

    public static LocalDateTime getDateTimeBeforePeriod(DatePeriod period) {
        return endOfDay(period.start.minusDays(1));
    }

    public static LocalDateTime getDateTimeAfterPeriod(DatePeriod period) {
        return period.end.plusDays(1).atStartOfDay();
    }

}
