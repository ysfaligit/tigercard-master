package com.tigercard.master.entity.rule.eval;

import com.tigercard.master.dto.TripContextDto;

abstract public class AbstractRuleCalculator {
    public abstract  void calculate(TripContextDto context);
}
