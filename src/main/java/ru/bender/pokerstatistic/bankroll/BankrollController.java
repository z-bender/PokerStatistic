package ru.bender.pokerstatistic.bankroll;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/bankroll")
class BankrollController {

    @PostMapping(value = "/add")
    public void add() {
        // todo
    }

    @PostMapping(value = "/addForDate")
    public void addForDate() {
        // todo
    }

}
