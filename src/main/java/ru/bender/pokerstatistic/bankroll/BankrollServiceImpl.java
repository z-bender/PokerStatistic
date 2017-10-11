package ru.bender.pokerstatistic.bankroll;

import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;

@Service
@Transactional
public class BankrollServiceImpl implements BankrollService {

    @Override
    public void addItem(LocalDateTime date, BankrollItem.Type type, int money, int points, String comment) {
    }

    @Override
    public BankrollOfPeriod getPeriodItems(DatePeriod period) {
        return null;
    }

    @Override
    public BankrollItem getLastItem(LocalDateTime dateTime) {
        return null;
    }

}
