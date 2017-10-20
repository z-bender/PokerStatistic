package ru.bender.pokerstatistic.bankroll;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;

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
    public void add(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDateTime date,
                    @RequestParam Integer money,
                    @RequestParam Integer points,
                    @RequestParam BankrollItem.Type type,
                    @RequestParam String comment)
            throws AddItemInFutureException, ExistFutureItemException, AddItemForTodayException {
        if (date.toLocalDate().equals(currentDate())) {
            service.addItem(money, points, type, comment);
        } else {
            service.addItemForDate(date.toLocalDate(), money, points, type, comment);
        }
    }

    @GetMapping(value = "/getLast")
    public BankrollItemDto getLast() {
        return service.mapToDto(service.getLastItem());
    }

    @GetMapping(value = "/getLastByDate")
    public BankrollItemDto getLastByDate(LocalDate date) {
        return service.mapToDto(service.getLastItemByDate(date));
    }

}
