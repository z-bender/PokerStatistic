package ru.bender.pokerstatistic.bankroll;

class AddItemInFutureException extends Exception {

    public AddItemInFutureException(BankrollItem bankrollItem) {
        super("Bankroll item can not be in the future - " + bankrollItem);
    }

}
