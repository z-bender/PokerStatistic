package ru.bender.pokerstatistic.bankroll;

class ExistFutureItemException extends Exception {

    public ExistFutureItemException(BankrollItem newItem, BankrollItem existItem) {
        super(String.format("Error create new item - %s, because exist later item - %s", newItem, existItem));
    }

}
