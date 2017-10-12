package ru.bender.pokerstatistic.bankroll;

import java.time.LocalDate;

class ExistFutureItemException extends Exception {

    public ExistFutureItemException(LocalDate date, BankrollItem existingItem) {
        super(String.format("Error adding item at %s. Exist later item - %s.", date, existingItem));
    }

}
