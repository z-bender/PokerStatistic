package ru.bender.pokerstatistic.bankroll;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.bender.pokerstatistic.bankroll.BankrollItem.Type;

import java.time.LocalDate;

@RestController
@CrossOrigin
@RequestMapping("/api/bankroll")
class BankrollController {

    private final BankrollService service;

    @Autowired
    BankrollController(BankrollService bankrollService) {
        this.service = bankrollService;
    }

    @PostMapping(value = "/add")
    public BankrollItemDto add(@RequestBody BankrollItemDto newItem)
            throws AddItemInFutureException, ExistFutureItemException {
        BankrollItem addedItem = service.addItem(
                newItem.dateTime.toLocalDate(),
                newItem.money,
                newItem.points,
                Type.fromString(newItem.type),
                newItem.comment
        );
        return service.mapToDto(addedItem);
    }

    @GetMapping(value = "/getMonthResults")
    public PeriodResultWithItems getMonthResults(@RequestParam Integer year, @RequestParam Integer month) {
        return service.getMonthResults(year, month);
    }

    // todo: exclude items
    // todo: year results structure
    @GetMapping(value = "/getYearResults")
    public PeriodResultWithItems getYearResults(@RequestParam Integer year) {
        return null;
    }

    public PeriodResult getAllPeriodResults() {
        return null;
    }

    @GetMapping(value = "/getLast")
    public BankrollItemDto getLast() {
        return service.mapToDto(service.getLastItem());
    }

    // todo: лишний метод?
    @GetMapping(value = "/getLastByDate")
    public BankrollItemDto getLastByDate(LocalDate date) {
        return service.mapToDto(service.getLastItemByDate(date));
    }

}
