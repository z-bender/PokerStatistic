package ru.bender.pokerstatistic.utils;

import lombok.AllArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;

@AllArgsConstructor
@ToString
public class DatePeriod {

    public LocalDate start;
    public LocalDate end;

}
