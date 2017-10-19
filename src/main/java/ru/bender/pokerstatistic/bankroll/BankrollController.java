package ru.bender.pokerstatistic.bankroll;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

import static ru.bender.pokerstatistic.utils.Utils.currentDate;

@RestController
@RequestMapping("/bankroll")
class BankrollController {

    private final BankrollService service;

    @Autowired
    BankrollController(BankrollService bankrollService) {
        this.service = bankrollService;
    }

    @PostMapping(value = "/add")
    public void add(@RequestParam LocalDate date,
                    @RequestParam Integer money,
                    @RequestParam Integer points,
                    @RequestParam BankrollItem.Type type,
                    @RequestParam String comment)
            throws AddItemInFutureException, ExistFutureItemException, AddItemForTodayException {
        // todo: validation
        if (date.equals(currentDate())) {
            service.addItem(money, points, type, comment);
        } else {
            service.addItemForDate(date, money, points, type, comment);
        }
    }

}
