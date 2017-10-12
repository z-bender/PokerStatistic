package ru.bender.pokerstatistic.bankroll;

class AddItemForTodayException extends Exception {

    public AddItemForTodayException() {
        super("Method addItemForDate() can not use today date. Use addItem() for that.");
    }

}
