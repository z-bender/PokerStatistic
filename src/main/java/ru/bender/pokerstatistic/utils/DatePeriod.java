package ru.bender.pokerstatistic.utils;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.LocalDate;

import static java.time.temporal.TemporalAdjusters.lastDayOfMonth;

@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class DatePeriod {

    public final LocalDate start;
    public final LocalDate end;

    public static DatePeriod ofMonth(int year, int month) {
        LocalDate firstDay = LocalDate.of(year, month, 1);
        return new DatePeriod(firstDay, firstDay.with(lastDayOfMonth()));
    }

    public static DatePeriod ofYear(int year) {
        LocalDate firstDay = LocalDate.of(year, 1, 1);
        LocalDate lastDay = LocalDate.of(year, 12, 31);
        return new DatePeriod(firstDay, lastDay);
    }

}
