package ru.bender.pokerstatistic.testing;


import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import ru.bender.pokerstatistic.utils.DatePeriod;

import java.time.LocalDateTime;
import java.util.Collection;

import static java.lang.System.lineSeparator;
import static org.testng.Assert.assertTrue;
import static ru.bender.pokerstatistic.utils.Utils.endOfDay;

public class AbstractTest extends AbstractTransactionalTestNGSpringContextTests {

    protected static void assertCollectionsEquals(Collection actual, Collection expected) {
        boolean equals = actual.size() == expected.size() && actual.containsAll(expected);
        assertTrue(equals,
                "Failed collections equals" + lineSeparator() +
                        "Actual: " + actual + lineSeparator() +
                        "Expected: " + expected);
    }

    protected static LocalDateTime getDateTimeBeforePeriod(DatePeriod period) {
        return endOfDay(period.start.minusDays(1));
    }

    protected static LocalDateTime getDateTimeAfterPeriod(DatePeriod period) {
        return period.end.plusDays(1).atStartOfDay();
    }

}
