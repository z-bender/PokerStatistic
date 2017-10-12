package ru.bender.pokerstatistic.bankroll;

class AddItemForTodayException extends Exception {

    public AddItemForTodayException(BankrollItem bankrollItem) {
        super("Method addItemForDate can not use today date - " + bankrollItem);
    }

}
