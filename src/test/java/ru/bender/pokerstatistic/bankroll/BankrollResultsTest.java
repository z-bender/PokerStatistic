package ru.bender.pokerstatistic.bankroll;

import org.testng.annotations.Test;
import ru.bender.pokerstatistic.bankroll.BankrollItem.Type;
import ru.bender.pokerstatistic.testing.UnitTest;
import ru.bender.pokerstatistic.utils.DatePeriod;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static java.time.LocalDate.of;
import static org.testng.Assert.assertEquals;
import static ru.bender.pokerstatistic.bankroll.BankrollItem.Type.CONVERT_BONUS;
import static ru.bender.pokerstatistic.bankroll.BankrollItem.Type.DEPOSIT;
import static ru.bender.pokerstatistic.bankroll.BankrollItem.Type.GAME;
import static ru.bender.pokerstatistic.bankroll.BankrollItem.Type.OTHER;
import static ru.bender.pokerstatistic.bankroll.BankrollItem.Type.WITHDRAWAL;
import static ru.bender.pokerstatistic.utils.Utils.endOfDay;

@UnitTest
public class BankrollResultsTest extends AbstractBankrollTest {

    @Test
    public void getItemsResults() {
        DatePeriod period = new DatePeriod(
                of(2017, 1, 1),
                of(2017, 1, 10)
        );
        int initMoney = 300;
        int initPoints = 500;
        BankrollItem itemBeforePeriod = addNewItem(endOfDay(period.start.minusDays(1)), initMoney, initPoints, DEPOSIT);
        BankrollItem itemAfterPeriod = addNewItem(period.start.plusDays(1).atStartOfDay());

        List<ItemResult> expected = new ArrayList<>();
        BankrollItem item1 = addNewItem(period.start.atStartOfDay(),
                initMoney - 7,
                initPoints + 10,
                GAME,
                "comment");
        expected.add(createItemResult(item1, -7, 10));
        BankrollItem item2 = addNewItem(period.start.plusDays(1).atTime(12, 0),
                item1.getMoney() + 15,
                item1.getPoints() - 3,
                CONVERT_BONUS);
        expected.add(createItemResult(item2, 15, -3));
        BankrollItem item3 = addNewItem(item2.getDateTime().plusSeconds(1),
                item2.getMoney() - 200,
                item2.getPoints(),
                WITHDRAWAL);
        expected.add(createItemResult(item3, -200, 0));
        BankrollItem item4 = addNewItem(endOfDay(period.end),
                item3.getMoney(),
                item2.getPoints() + 20,
                OTHER);
        expected.add(createItemResult(item4, 0, 20));

        List<ItemResult> actual = service.getBankrollOfPeriod(period).getItemsResults();
        assertEquals(actual, expected);
    }

    private ItemResult createItemResult(int id, LocalDateTime date, int money, int points, Type type, String comment) {
        return new ItemResult(id, date.toLocalDate(), money, points, type, comment);
    }

    private ItemResult createItemResult(BankrollItem bankrollItem, int money, int points) {
        return createItemResult(
                bankrollItem.getId(),
                bankrollItem.getDateTime(),
                money,
                points,
                bankrollItem.getType(),
                bankrollItem.getComment()
        );
    }

    @Test
    public void getPeriodResult() {
        DatePeriod period = new DatePeriod(of(2017, 5, 10), of(2017, 5, 16));
        BankrollItem itemBeforePeriod = addNewItem(endOfDay(period.start).minusSeconds(1));
        BankrollItem lastItemBeforePeriod = addNewItem(endOfDay(period.start), 500, 1000, DEPOSIT);
        BankrollItem itemAfterPeriod = addNewItem(period.end.plusDays(1).atStartOfDay(), 10000, 10000, GAME);

        BankrollItem item1 = addNewItem(period.start.atStartOfDay(), 510, 1200, GAME);
        BankrollItem item2 = addNewItem(period.start.plusDays(3).atTime(12, 0), 610, 1200, DEPOSIT);
        BankrollItem item3 = addNewItem(item2.getDateTime().plusSeconds(1), 611, 1200, GAME);
        BankrollItem item4 = addNewItem(item3.getDateTime().plusSeconds(1), 620, 700, CONVERT_BONUS);
        BankrollItem item5 = addNewItem(item4.getDateTime().plusDays(1), 650, 720, OTHER);
        BankrollItem item6 = addNewItem(item5.getDateTime().plusSeconds(1), 50, 720, WITHDRAWAL);
        BankrollItem item7 = addNewItem(item6.getDateTime().plusSeconds(1), 100, 720, DEPOSIT);
        BankrollItem item8 = addNewItem(item7.getDateTime().plusSeconds(1), 95, 750, GAME);
        BankrollItem item9 = addNewItem(endOfDay(period.end), 5, 750, WITHDRAWAL);

        PeriodResult expected = new PeriodResult(period);
        expected.moneyAtEnd = 5;
        expected.winning = 10 + 1 - 5;
        expected.deposit = 100 + 50;
        expected.bonus = 9;
        expected.otherChanges = 30;
        expected.withdrawal = 600 + 90;
        expected.pointsAtEnd = 750;
        expected.earnedPoints = 200 + 20 + 30;
        expected.spentPoints = 500;
        expected.period = period;

        PeriodResult actual = service.getBankrollOfPeriod(period).getPeriodResult();
        assertEquals(actual, expected);
    }

    @Test
    public void getPeriodResultWithoutLastItem() {
        DatePeriod period = new DatePeriod(of(2017, 3, 1), of(2017, 10, 1));
        BankrollItem firstItemInDb = addNewItem(period.start.atStartOfDay(), 500, 700, DEPOSIT);
        BankrollItem item2 = addNewItem(firstItemInDb.getDateTime().plusDays(1), 600, 710, GAME);
        BankrollItem item3 = addNewItem(item2.getDateTime().plusDays(1), 300, 710, WITHDRAWAL);

        PeriodResult expected = new PeriodResult(period);
        expected.moneyAtEnd = 300;
        expected.winning = 100;
        expected.deposit = 0;
        expected.bonus = 0;
        expected.otherChanges = 0;
        expected.withdrawal = 300;
        expected.pointsAtEnd = 710;
        expected.earnedPoints = 10;
        expected.spentPoints = 0;

        PeriodResult actual = service.getBankrollOfPeriod(period).getPeriodResult();
        assertEquals(actual, expected);
    }

    @Test
    public void getEmptyPeriodResult() {
        DatePeriod period = new DatePeriod(of(2017, 1, 1), of(2017, 1, 31));
        BankrollItem item1BeforePeriod = addNewItem(period.start.minusDays(1).atStartOfDay(), 100, 200, DEPOSIT);
        BankrollItem item2BeforePeriod = addNewItem(endOfDay(period.start.minusDays(1)), 120, 220, GAME);
        BankrollItem itemAfterPeriod = addNewItem(period.end.plusDays(1).atStartOfDay(), 150, 300, GAME);

        PeriodResult expected = new PeriodResult(period);
        expected.moneyAtEnd = 120;
        expected.pointsAtEnd = 220;

        PeriodResult actual = service.getBankrollOfPeriod(period).getPeriodResult();
        assertEquals(actual, expected);
    }

}
