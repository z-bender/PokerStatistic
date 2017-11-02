package ru.bender.pokerstatistic.bankroll;

import org.springframework.beans.factory.annotation.Autowired;
import ru.bender.pokerstatistic.testing.AbstractTest;

import java.time.LocalDateTime;

import static ru.bender.pokerstatistic.bankroll.BankrollItem.Type.GAME;
import static ru.bender.pokerstatistic.bankroll.BankrollItem.newItem;

abstract public class AbstractBankrollTest extends AbstractTest {

    protected final static int IRRELEVANT_VALUE = 500;

    @Autowired
    protected BankrollService service;
    @Autowired
    protected BankrollItemDao dao;

    protected BankrollItem addNewItem(LocalDateTime dateTime, Integer money, Integer points, BankrollItem.Type type) {
        return dao.save(newItem(dateTime, money, points, type, null));
    }

    protected BankrollItem addNewItem(LocalDateTime dateTime) {
        return addNewItem(dateTime, IRRELEVANT_VALUE, IRRELEVANT_VALUE, GAME);
    }

}
