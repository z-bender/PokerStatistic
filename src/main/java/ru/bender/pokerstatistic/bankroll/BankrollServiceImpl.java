package ru.bender.pokerstatistic.bankroll;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.bender.pokerstatistic.utils.DatePeriod;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.nonNull;
import static ru.bender.pokerstatistic.utils.Utils.currentDate;
import static ru.bender.pokerstatistic.utils.Utils.endOfDay;
import static ru.bender.pokerstatistic.utils.Utils.now;

@Service
@Transactional
class BankrollServiceImpl implements BankrollService {

    private static final Logger LOG = LoggerFactory.getLogger(BankrollServiceImpl.class);

    private final BankrollItemDao dao;
    private final ModelMapper mapper;

    @Autowired
    BankrollServiceImpl(BankrollItemDao dao, ModelMapper mapper) {
        this.dao = dao;
        this.mapper = mapper;
    }

    private BankrollItem saveNewItem(LocalDateTime itemDateTime, int money, int points,
                                     BankrollItem.Type type, String comment) {
        final BankrollItem newItem = BankrollItem.newItem(itemDateTime, money, points, type, comment);
        LOG.debug("Add new item - " + newItem);
        return dao.save(newItem);
    }

    @Override
    public BankrollItem addItem(LocalDate date, int money, int points, BankrollItem.Type type, String comment)
            throws ExistFutureItemException, AddItemInFutureException {

        final LocalDateTime newItemDateTime = validationDateAndGetNewItemDateTime(date);
        return saveNewItem(newItemDateTime, money, points, type, comment);
    }

    private LocalDateTime validationDateAndGetNewItemDateTime(LocalDate date)
            throws AddItemInFutureException, ExistFutureItemException {

        checkThatDateIsNotAFuture(date);
        checkThatNotExistItemWithLaterDate(date);
        if (currentDate().equals(date)) {
            return now();
        }
        LocalDateTime lastItemDateTime = getLastItemDateTimeByDate(date);
        boolean isLastItemAfterDefaultTimeOfNewItem =
                nonNull(lastItemDateTime) && !lastItemDateTime.isBefore(date.atTime(TIME_FOR_NEW_ITEM_FROM_PAST_DAY));
        return isLastItemAfterDefaultTimeOfNewItem ?
                lastItemDateTime.plusSeconds(1) : date.atTime(TIME_FOR_NEW_ITEM_FROM_PAST_DAY);

    }

    private LocalDateTime getLastItemDateTimeByDate(LocalDate date) {
        final BankrollItem lastItem = getLastItemByDate(date);
        return lastItem != null ? lastItem.getDateTime() : null;
    }

    private void checkThatDateIsNotAFuture(LocalDate date) throws AddItemInFutureException {
        if (date.isAfter(LocalDate.now())) {
            throw new AddItemInFutureException(date);
        }
    }

    private void checkThatNotExistItemWithLaterDate(LocalDate date) throws ExistFutureItemException {
        final BankrollItem lastItem = getLastItem();
        if (nonNull(lastItem) && lastItem.getDateTime().toLocalDate().isAfter(date)) {
            throw new ExistFutureItemException(date, lastItem);
        }
    }

    @Override
    public PeriodResultWithItems getMonthResults(Integer year, Integer month) {
        return getBankrollOfPeriod(DatePeriod.ofMonth(year, month)).getPeriodResult();
    }

    @Override
    public BankrollOfPeriod getBankrollOfPeriod(DatePeriod period) {
        List<BankrollItem> items = dao.findAllByDateTimeBetween(period.start.atStartOfDay(), endOfDay(period.end));
        BankrollItem lastItemBeforePeriod = getLastItemByDate(period.start.minusDays(1));
        return new BankrollOfPeriod(items, lastItemBeforePeriod, period);
    }

    @Override
    public ParentChildPeriodResults getYearWithChildResults(Integer year) {
        PeriodResult yearResults = getYearResults(year);
        List<PeriodResult> monthsResults = new ArrayList<>();
        for (int month = 1; month <= 12; month++) {
            monthsResults.add(getMonthResults(year, month).setMonthToPeriodName());
        }
        return new ParentChildPeriodResults(yearResults, monthsResults);
    }

    private PeriodResult getYearResults(Integer year) {
        return getBankrollOfPeriod(DatePeriod.ofYear(year)).getPeriodResult();
    }

    @Override
    public ParentChildPeriodResults getAllPeriodWithChildResults() {
        List<BankrollItem> items = dao.findAllByOrderByDateTime();
        PeriodResultWithItems allPeriodResult = new BankrollOfPeriod(items, null, null).getPeriodResult();
        allPeriodResult.setPeriodName("All time");
        List<PeriodResult> yearsResults = new ArrayList<>();
        if (!items.isEmpty()) {
            int firstYear = items.get(0).getDateTime().getYear();
            int lastYear = items.get(items.size() - 1).getDateTime().getYear();
            for (int year = firstYear; year <= lastYear; year++) {
                yearsResults.add(getYearResults(year).setYearToPeriodName());
            }
        }
        return new ParentChildPeriodResults(allPeriodResult, yearsResults);
    }

    @Override
    public BankrollItem getLastItemByDate(LocalDate date) {
        return dao.findFirstByDateTimeBeforeOrderByDateTimeDesc(date.plusDays(1).atStartOfDay());
    }

    @Override
    public BankrollItem getLastItem() {
        return dao.findFirstByOrderByDateTimeDesc();
    }

    @Override
    public BankrollItemDto mapToDto(BankrollItem item) {
        return mapper.map(item, BankrollItemDto.class);
    }

    @Override
    public DatePeriod getStatisticsPeriod() {
        return getPeriodBySortedItems(dao.findAllByOrderByDateTime());
    }

    private DatePeriod getPeriodBySortedItems(List<BankrollItem> sortedByDateItems) {
        LocalDate start = sortedByDateItems.get(0).getDateTime().toLocalDate();
        LocalDate end = sortedByDateItems.get(sortedByDateItems.size() - 1).getDateTime().toLocalDate();
        return new DatePeriod(start, end);
    }
}
