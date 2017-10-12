package ru.bender.pokerstatistic.bankroll;

import java.time.LocalDate;

class AddItemInFutureException extends Exception {

    public AddItemInFutureException(LocalDate futureDate) {
        super("Bankroll item can not be in the future - " + futureDate);
    }

}
