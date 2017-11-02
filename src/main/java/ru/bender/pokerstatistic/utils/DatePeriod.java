package ru.bender.pokerstatistic.utils;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.LocalDate;

@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class DatePeriod {

    public final LocalDate start;
    public final LocalDate end;

}
