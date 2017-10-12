package ru.bender.pokerstatistic.bankroll;

import org.springframework.stereotype.Service;
import ru.bender.pokerstatistic.utils.DatePeriod;

import javax.transaction.Transactional;
import java.time.LocalDate;

@Service
@Transactional
class BankrollServiceImpl implements BankrollService {

    @Override
    public BankrollItem addItemForDate(LocalDate date, int money, int points, BankrollItem.Type type, String comment) {
        return null;
    }

    @Override
    public BankrollItem addItem(int money, int points, BankrollItem.Type type, String comment) {
        return null;
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
