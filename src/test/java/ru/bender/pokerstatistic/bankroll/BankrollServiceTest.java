package ru.bender.pokerstatistic.bankroll;

import org.testng.annotations.Test;
import ru.bender.pokerstatistic.bankroll.BankrollItem.Type;
import ru.bender.pokerstatistic.testing.UnitTest;
import ru.bender.pokerstatistic.utils.DatePeriod;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import static java.time.LocalDate.of;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.lessThan;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static ru.bender.pokerstatistic.bankroll.BankrollItem.Type.DEPOSIT;
import static ru.bender.pokerstatistic.bankroll.BankrollItem.Type.GAME;
import static ru.bender.pokerstatistic.bankroll.BankrollItem.Type.OTHER;
import static ru.bender.pokerstatistic.bankroll.BankrollItem.Type.WITHDRAWAL;
import static ru.bender.pokerstatistic.bankroll.BankrollService.TIME_FOR_NEW_ITEM_FROM_PAST_DAY;
import static ru.bender.pokerstatistic.utils.Utils.currentDate;
import static ru.bender.pokerstatistic.utils.Utils.endOfDay;

@UnitTest
public class BankrollServiceTest extends AbstractBankrollTest {

    @Test
    public void addItem() throws AddItemInFutureException, ExistFutureItemException {
        final int money = 300;
        final int points = 400;
        final Type type = DEPOSIT;
        final String comment = "Just comment";

        BankrollItem newItem = service.addItem(currentDate(), money, points, type, comment);

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
        final String comment = "comment";

        BankrollItem newItem = service.addItem(date, money, points, type, comment);

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
        BankrollItem newItem1 = service.addItem(date, IRRELEVANT_VALUE, IRRELEVANT_VALUE, DEPOSIT, null);
        assertEquals(service.getLastItemByDate(date), newItem1);

        BankrollItem itemAtEndOfDay = addNewItem(date.atTime(23, 59));
        BankrollItem newItem2 = service.addItem(date, IRRELEVANT_VALUE, IRRELEVANT_VALUE, OTHER, null);
        assertEquals(service.getLastItemByDate(date), newItem2);
    }

    @Test(expectedExceptions = ExistFutureItemException.class)
    public void addForDateWithExistingTodayItem() throws Exception {
        LocalDateTime now = LocalDateTime.now();
        addNewItem(now);
        service.addItem(now.minusDays(1).toLocalDate(), IRRELEVANT_VALUE, IRRELEVANT_VALUE, WITHDRAWAL, null);
    }

    @Test(expectedExceptions = ExistFutureItemException.class)
    public void addForDateWithExistingFutureItem() throws Exception {
        LocalDateTime date = LocalDateTime.now().minusDays(5);
        addNewItem(date.plusDays(1));
        service.addItem(date.toLocalDate(), IRRELEVANT_VALUE, IRRELEVANT_VALUE, WITHDRAWAL, null);
    }

    @Test(expectedExceptions = AddItemInFutureException.class)
    public void addItemForDateInFuture() throws Exception {
        service.addItem(LocalDate.now().plusDays(1), IRRELEVANT_VALUE, IRRELEVANT_VALUE, GAME, null);
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

        List<BankrollItem> actual = service.getBankrollOfPeriod(period).getItems();

        assertCollectionsEquals(actual, expected);
    }

    @Test
    public void notExistItemsInPeriod() {
        DatePeriod period = new DatePeriod(
                of(2017, 2, 2),
                of(2017, 2, 10)
        );
        addNewItem(getDateTimeBeforePeriod(period));
        addNewItem(getDateTimeAfterPeriod(period));

        assertThat(service.getBankrollOfPeriod(period).getItems(), is(empty()));
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

    private void assertDateTimeInFiveSecondsFromNow(BankrollItem newItem) {
        assertThat(ChronoUnit.SECONDS.between(newItem.getDateTime(), LocalDateTime.now()), lessThan(5L));
    }

    @Test
    public void getStatisticsPeriod() {
        LocalDate start = of(2013, 10, 1);
        LocalDate end = of(2017, 6, 6);
        addNewItem(start.plusYears(2));
        addNewItem(start.plusYears(2).plusDays(5));
        addNewItem(start);
        addNewItem(start.plusYears(2).plusDays(6));
        addNewItem(end);
        addNewItem(end.minusYears(1).minusMonths(1));

        DatePeriod expected = new DatePeriod(start, end);
        DatePeriod actual = service.getStatisticsPeriod();
        assertEquals(actual, expected);
    }

}