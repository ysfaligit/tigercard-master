package com.tigercard.master.rule;

import com.tigercard.master.entity.Trip;

public abstract class AbstractRuleCondition {
    abstract void evaluate(Trip tripToBeEvaluated);
}
