package com.tigercard.master.rule.action;

import com.tigercard.master.dto.TripContextDto;
import com.tigercard.master.entity.RuleAction;
import com.tigercard.master.entity.Trip;
import org.springframework.stereotype.Component;

@Component
public abstract class AbstractRuleAction implements IRuleAction {
    protected Integer priority;

    @Override
    public void apply(TripContextDto contextDto) {
        Trip newTrip = contextDto.getNewTrip();
        RuleAction ruleAction = contextDto.getRuleConditionApplied().getRuleAction();

        if (ruleAction != null) {
            apply(ruleAction, newTrip);
        }

    }

    public Integer getPriority() {
        return priority;
    }

    abstract protected void apply(RuleAction ruleAction, Trip newTrip);
}
