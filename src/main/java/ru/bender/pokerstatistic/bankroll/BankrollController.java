package ru.bender.pokerstatistic.bankroll;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.bender.pokerstatistic.bankroll.BankrollItem.Type;

import java.time.LocalDate;

@RestController
@CrossOrigin
@RequestMapping("/bankroll")
class BankrollController {

    private final BankrollService service;

    @Autowired
    BankrollController(BankrollService bankrollService) {
        this.service = bankrollService;
    }

    @PostMapping(value = "/add")
    public void add(@RequestBody BankrollItemDto newItem)
            throws AddItemInFutureException, ExistFutureItemException {
        service.addItem(
                newItem.dateTime.toLocalDate(),
                newItem.money,
                newItem.points,
                Type.parse(newItem.type),
                newItem.comment
        );
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
