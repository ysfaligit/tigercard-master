package com.tigercard.master.entity.service;

import com.tigercard.master.entity.RuleAction;
import com.tigercard.master.entity.RuleCondition;
import com.tigercard.master.entity.Trip;
import com.tigercard.master.entity.repository.RuleConditionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class RideRuleService {
    @Autowired
    private RuleConditionRepository repository;


    public RuleCondition saveRule(RuleCondition ruleCondition) {
        return repository.save(ruleCondition);
    }

    public List<RuleCondition> getRideRules() {
        return repository.findTripsByActive(Boolean.TRUE, Sort.by("priority"));
    }

    public void delete(long id) {
        RuleCondition rule = new RuleCondition(id);

        repository.delete(rule);
    }

    public RuleCondition getRideRuleByTrip(Trip trip) {
        List<RuleCondition> rules = repository
                .getRideRule(trip.getTime(), trip.getTime(),
                        trip.getFromDate(), trip.getToDate(),
                        trip.getZoneFrom(), trip.getZoneTo(),
                        trip.getCard().getAge(),
                        trip.getDay().getDayId());

        RuleCondition finalRule = null;
        if (rules != null && rules.size() > 1) {
            finalRule = rules.stream()
                    .filter(rideRule ->
                            rideRule.getZoneFrom() != null && rideRule.getZoneTo() != null
                                    && rideRule.getZoneFrom().getZoneId() == trip.getZoneFrom().getZoneId()
                                    && rideRule.getZoneTo().getZoneId() == trip.getZoneTo().getZoneId())
                    .findFirst().get();
        } else if (rules != null && rules.size() == 1)
            finalRule = rules.get(0);

        else
            finalRule = new RuleCondition(new RuleAction(false));

        log.info("Rule Applied " + finalRule.getRuleId());
        return finalRule;
    }
}
