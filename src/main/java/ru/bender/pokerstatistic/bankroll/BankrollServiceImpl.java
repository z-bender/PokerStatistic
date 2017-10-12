package ru.bender.pokerstatistic.bankroll;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.bender.pokerstatistic.utils.DatePeriod;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;

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
    public BankrollItem addItem(int money, int points, BankrollItem.Type type, String comment) {
        return saveNewItem(now(), money, points, type, comment);
    }

    private BankrollItem saveNewItem(LocalDateTime itemDateTime, int money, int points,
                                     BankrollItem.Type type, String comment) {
        final BankrollItem newItem = BankrollItem.newItem(itemDateTime, money, points, type, comment);
        return dao.save(newItem);
    }

    @Override
    public BankrollItem addItemForDate(LocalDate date, int money, int points, BankrollItem.Type type, String comment)
            throws ExistFutureItemException, AddItemForTodayException, AddItemInFutureException {

        final LocalDateTime newItemDateTime = validationDateAndGetNewItemDateTime(date);
        return saveNewItem(newItemDateTime, money, points, type, comment);
    }

    private LocalDateTime validationDateAndGetNewItemDateTime(LocalDate date)
            throws AddItemInFutureException, ExistFutureItemException, AddItemForTodayException {

        checkThatDateIsNotToday(date);
        checkThatDateIsNotAFuture(date);
        checkThatNotExistItemWithLaterDate(date);
        final LocalDateTime lastItemDateTime = getLastItemByDate(date).getDateTime();
        return lastItemDateTime.isBefore(date.atTime(TIME_FOR_NEW_ITEM_FROM_PAST_DAY)) ?
                date.atTime(TIME_FOR_NEW_ITEM_FROM_PAST_DAY) : lastItemDateTime.plusSeconds(1);

    }

    private void checkThatDateIsNotToday(LocalDate date) throws AddItemForTodayException {
        if (LocalDate.now().equals(date)) {
            throw new AddItemForTodayException();
        }
    }

    private void checkThatDateIsNotAFuture(LocalDate date) throws AddItemInFutureException {
        if (date.isAfter(LocalDate.now())) {
            throw new AddItemInFutureException(date);
        }
    }

    private void checkThatNotExistItemWithLaterDate(LocalDate date) throws ExistFutureItemException {
        final BankrollItem lastItem = getLastItem();
        if (lastItem.getDateTime().toLocalDate().isAfter(date)) {
            throw new ExistFutureItemException(date, lastItem);
        }
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
