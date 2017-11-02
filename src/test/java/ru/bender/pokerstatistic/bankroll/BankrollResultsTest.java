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


}
