package ru.bender.pokerstatistic.bankroll;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.bender.pokerstatistic.utils.DatePeriod;

import javax.transaction.Transactional;
import java.time.LocalDate;

import static ru.bender.pokerstatistic.utils.Utils.now;

@Service
@Transactional
public class BankrollServiceImpl implements BankrollService {

    private final BankrollItemDao dao;

    @Autowired
    BankrollServiceImpl(BankrollItemDao dao) {
        this.dao = dao;
    }

    @Override
    public BankrollItem addItemForDate(LocalDate date, int money, int points, BankrollItem.Type type, String comment)
            throws ExistFutureItemException, AddItemForTodayException, AddItemInFutureException {
        return null;
    }

    @Override
    public BankrollItem addItem(int money, int points, BankrollItem.Type type, String comment) {
        BankrollItem newItem = BankrollItem.newItem(now(), money, points, type, comment);
        return dao.save(newItem);
    }

    @Override
    public BankrollOfPeriod getPeriodItems(DatePeriod period) {
        return null;
    }

    @Override
    public BankrollItem getLastItemByDate(LocalDate date) {
        return null;
    }

    @Override
    public BankrollItem getLastItem() {
        return null;
    }
}
