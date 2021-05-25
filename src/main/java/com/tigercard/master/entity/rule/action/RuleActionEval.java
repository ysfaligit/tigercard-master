package com.tigercard.master.entity.rule.action;

import com.tigercard.master.dto.TripContextDto;
import com.tigercard.master.entity.service.RideRuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

@Service
public class RuleActionEval {
    @Autowired
    private List<IRuleAction> ruleActions;

    @Autowired
    RideRuleService rideRuleService;

    public void fireRules (TripContextDto contextDto) {
        contextDto.setRuleConditionApplied(rideRuleService.getRideRuleByTrip(contextDto.getNewTrip()));
        ruleActions.stream().forEach(iRuleAction -> iRuleAction.apply(contextDto));
    }

    @PostConstruct
    public void arrayActions(){
        Collections.sort(ruleActions, new Comparator<IRuleAction>() {
            @Override
            public int compare(IRuleAction o1, IRuleAction o2) {
                return ((AbstractRuleAction)o1).getPriority()
                        .compareTo(
                                ((AbstractRuleAction)o2).getPriority()
                        );
            }
        });
    }

}
