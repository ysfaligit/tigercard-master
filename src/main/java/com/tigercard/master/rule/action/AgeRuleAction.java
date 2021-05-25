package com.tigercard.master.rule.action;

import com.tigercard.master.entity.RuleAction;
import com.tigercard.master.entity.Trip;
import org.springframework.stereotype.Component;

@Component
public class AgeRuleAction extends AbstractRuleAction {
    public AgeRuleAction(){
        super.priority = 2;
    }

    @Override
    protected void apply(RuleAction ruleAction, Trip newTrip) {
        if (ruleAction.getDiscFixed() != null
                && ruleAction.getDiscFixed() > 0
                && ruleAction.getDiscFixed() <= newTrip.getFare())
            newTrip.setFare(newTrip.getFare() - ruleAction.getDiscFixed());

        else if (ruleAction.getDiscPerc() != null
                && ruleAction.getDiscPerc() > 0)
            newTrip.setFare((newTrip.getFare() * ruleAction.getDiscPerc()) / 100);
    }
}
