package ru.bender.pokerstatistic.bankroll;

import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;
import ru.bender.pokerstatistic.bankroll.BankrollItem.Type;
import ru.bender.pokerstatistic.testing.AbstractTest;
import ru.bender.pokerstatistic.testing.UnitTest;
import ru.bender.pokerstatistic.utils.DatePeriod;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import static java.time.LocalDate.of;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.lessThan;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static ru.bender.pokerstatistic.bankroll.BankrollItem.Type.DEPOSIT;
import static ru.bender.pokerstatistic.bankroll.BankrollItem.Type.GAME;
import static ru.bender.pokerstatistic.bankroll.BankrollItem.Type.OTHER;
import static ru.bender.pokerstatistic.bankroll.BankrollItem.Type.WITHDRAWAL;
import static ru.bender.pokerstatistic.bankroll.BankrollItem.newItem;
import static ru.bender.pokerstatistic.bankroll.BankrollService.TIME_FOR_NEW_ITEM_FROM_PAST_DAY;

@UnitTest
public class BankrollServiceTest extends AbstractTest {

    private final static int IRRELEVANT_VALUE = 500;

    @Autowired
    private BankrollService service;
    @Autowired
    private BankrollItemDao dao;


    @Test
    public void addItem() {
        final int money = 300;
        final int points = 400;
        final Type type = DEPOSIT;
        final String comment = "Just comment";

        BankrollItem newItem = service.addItem(money, points, type, comment);

        assertNotNull(newItem.getType());
        assertEquals(newItem.getType(), type);
        assertEquals(newItem.getMoney().intValue(), money);
        assertEquals(newItem.getPoints().intValue(), points);
        assertEquals(newItem.getComment(), comment);
        assertDateTimeInFiveSecondsFromNow(newItem);
    }

    @Test
    public void addItemForDate() throws Exception {
        LocalDate date = LocalDate.now().minusDays(5);
        final int money = 300;
        final int points = 400;
        final Type type = DEPOSIT;
        final String comment = "Just comment";

        BankrollItem newItem = service.addItemForDate(date, money, points, type, comment);

        assertNotNull(newItem.getType());
        assertEquals(newItem.getType(), type);
        assertEquals(newItem.getMoney().intValue(), money);
        assertEquals(newItem.getPoints().intValue(), points);
        assertEquals(newItem.getComment(), comment);
        assertEquals(newItem.getDateTime(), date.atTime(TIME_FOR_NEW_ITEM_FROM_PAST_DAY));
    }

    @Test
    public void addNotFirstItemForDate() throws Exception {
        LocalDate date = LocalDate.now().minusDays(7);

        BankrollItem itemAtStartOfDay = addNewItem(date.atTime(0, 0));
        BankrollItem newItem1 = service.addItemForDate(date, IRRELEVANT_VALUE, IRRELEVANT_VALUE, DEPOSIT, null);
        assertEquals(service.getLastItemByDate(date), newItem1);

        BankrollItem itemAtEndOfDay = addNewItem(date.atTime(23, 59));
        BankrollItem newItem2 = service.addItemForDate(date, IRRELEVANT_VALUE, IRRELEVANT_VALUE, OTHER, null);
        assertEquals(service.getLastItemByDate(date), newItem2);
    }

    @Test(expectedExceptions = ExistFutureItemException.class)
    public void addWithExistingFewDaysFutureItem() throws ExistFutureItemException {
        addNewItem(LocalDateTime.now().plusDays(5));
        service.addItem(IRRELEVANT_VALUE, IRRELEVANT_VALUE, GAME, null);
    }

    @Test(expectedExceptions = ExistFutureItemException.class)
    public void addForDateWithExistingTodayItem() throws Exception {
        LocalDateTime now = LocalDateTime.now();
        addNewItem(now);
        service.addItemForDate(now.minusDays(1).toLocalDate(), IRRELEVANT_VALUE, IRRELEVANT_VALUE, WITHDRAWAL, null);
    }

    @Test(expectedExceptions = ExistFutureItemException.class)
    public void addForDateWithExistingFutureItem() throws Exception {
        LocalDateTime date = LocalDateTime.now().minusDays(5);
        addNewItem(date.plusDays(1));
        service.addItemForDate(date.toLocalDate(), IRRELEVANT_VALUE, IRRELEVANT_VALUE, WITHDRAWAL, null);
    }

    @Test(expectedExceptions = AddItemForTodayException.class)
    public void addItemForDateToday() throws Exception {
        service.addItemForDate(LocalDate.now(), IRRELEVANT_VALUE, IRRELEVANT_VALUE, DEPOSIT, null);
    }

    @Test(expectedExceptions = AddItemInFutureException.class)
    public void addItemForDateInFuture() throws Exception {
        service.addItemForDate(LocalDate.now().plusDays(1), IRRELEVANT_VALUE, IRRELEVANT_VALUE, GAME, null);
    }

    @Test
    public void getPeriodItems() {
        DatePeriod period = new DatePeriod(
                of(2017, 2, 2),
                of(2017, 2, 10)
        );
        addNewItem(getDateTimeBeforePeriod(period));
        addNewItem(getDateTimeAfterPeriod(period));
        List<BankrollItem> expected = new ArrayList<>();
        expected.add(addNewItem(period.start.atStartOfDay()));
        expected.add(addNewItem(period.start.atStartOfDay()));
        expected.add(addNewItem(period.start.plusDays(1).atTime(13, 13, 13)));
        expected.add(addNewItem(endOfDay(period.end)));

        List<BankrollItem> actual = service.getPeriodItems(period).getItems();

        assertCollectionsEquals(actual, expected);
    }

    @Test
    public void getLastItem() {
        LocalDateTime now = LocalDateTime.now();
        addNewItem(now.minusDays(6).plusMinutes(10));
        addNewItem(now.minusDays(5));
        BankrollItem lastItemFewDaysAgo = addNewItem(now.minusDays(5).plusSeconds(1));
        assertEquals(service.getLastItem(), lastItemFewDaysAgo);

        BankrollItem lastItemToday = addNewItem(now);
        assertEquals(service.getLastItem(), lastItemToday);
    }

    @Test
    public void getLastItemByDate() {
        LocalDate date = LocalDate.now().minusDays(6);
        addNewItem(date.minusDays(1).atStartOfDay());
        addNewItem(date.atStartOfDay());
        addNewItem(date.atTime(13, 22));
        BankrollItem expected = addNewItem(date.atTime(13, 22, 1));
        addNewItem(date.plusDays(1).atStartOfDay());
        addNewItem(LocalDateTime.now());

        assertEquals(service.getLastItemByDate(date), expected);
    }

    @Test
    public void getLastItemByDateAtAnotherDay() {
        LocalDate date = LocalDate.now().minusDays(10);
        addNewItem(endOfDay(date.minusDays(1)));
        addNewItem(date.atStartOfDay());
        addNewItem(date.atTime(18, 33));
        BankrollItem expected = addNewItem(date.atTime(18, 33, 1));
        addNewItem(date.plusDays(1).atStartOfDay());

        assertEquals(service.getLastItemByDate(date), expected);
    }

    private BankrollItem addNewItem(LocalDateTime dateTime, Integer money, Integer points, Type type) {
        return dao.save(newItem(dateTime, money, points, type, null));
    }

    private BankrollItem addNewItem(LocalDateTime dateTime) {
        return addNewItem(dateTime, IRRELEVANT_VALUE, IRRELEVANT_VALUE, GAME);
    }

    private void assertDateTimeInFiveSecondsFromNow(BankrollItem newItem) {
        assertThat(ChronoUnit.SECONDS.between(newItem.getDateTime(), LocalDateTime.now()), lessThan(5L));
    }

}