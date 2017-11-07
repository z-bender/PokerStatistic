package ru.bender.pokerstatistic.bankroll;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

// fix: rename
@Data
@AllArgsConstructor
public class ParentChildPeriodResults {

    PeriodResult parent;
    List<PeriodResult> child;

    public ParentChildPeriodResults() {
        child = new ArrayList<>();
    }

}
