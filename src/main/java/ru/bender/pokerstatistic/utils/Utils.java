package ru.bender.pokerstatistic.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Utils {

    public static LocalDateTime now() {
        return LocalDateTime.now();
    }

    public static LocalDate currentDate() {
        return LocalDate.now();
    }

    public static LocalDateTime endOfDay(LocalDate date) {
        return date.atTime(23, 59, 59);
    }

}
