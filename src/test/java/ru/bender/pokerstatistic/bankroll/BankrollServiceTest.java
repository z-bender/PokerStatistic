package ru.bender.pokerstatistic.bankroll;

import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;
import ru.bender.pokerstatistic.bankroll.BankrollItem.Type;
import ru.bender.pokerstatistic.testing.UnitTest;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static ru.bender.pokerstatistic.bankroll.BankrollItem.Type.DEPOSIT;
import static ru.bender.pokerstatistic.bankroll.BankrollItem.Type.GAME;
import static ru.bender.pokerstatistic.bankroll.BankrollItem.newItem;
import static ru.bender.pokerstatistic.testing.TestUtils.assertCollectionsEquals;
import static ru.bender.pokerstatistic.testing.TestUtils.endOfDay;
import static ru.bender.pokerstatistic.testing.TestUtils.getDateTimeAfterPeriod;
import static ru.bender.pokerstatistic.testing.TestUtils.getDateTimeBeforePeriod;

@UnitTest
public class BankrollServiceTest {

    private final BankrollService service;
    private final BankrollItemDao dao;

    @Autowired
    public BankrollServiceTest(BankrollService bankrollService, BankrollItemDao dao) {
        this.service = bankrollService;
        this.dao = dao;
    }

    @Test
    public void testAddItem() {
        throw new NotImplementedException();
    }

    @Test
    public void testGetPeriodItems() {
        DatePeriod period = new DatePeriod(
                LocalDate.of(2, 2, 2017), 
                LocalDate.of(10, 2, 2017)
        );
        addNewItem(getDateTimeBeforePeriod(period), 500, 300, GAME);
        addNewItem(getDateTimeAfterPeriod(period), 800, 320, DEPOSIT);
        List<BankrollItem> expected = new ArrayList<>();
        expected.add(addNewItem(period.start.atStartOfDay(), 600, 310, GAME));
        expected.add(addNewItem(period.start.atStartOfDay(), 620, 315, GAME));
        expected.add(addNewItem(period.start.plusDays(1).atTime(13,13,13), 620, 315, GAME));
        expected.add(addNewItem(endOfDay(period.end), 620, 315, GAME));

        List<BankrollItem> actual = service.getPeriodItems(period).getItems();

        assertCollectionsEquals(actual, expected);
    }

    private BankrollItem addNewItem(LocalDateTime dateTime, Integer money, Integer points, Type type) {
        return dao.save(newItem(dateTime, money, points, type, null));
    }

    @Test
    public void testGetLastItem() {
        throw new NotImplementedException();
    }

}