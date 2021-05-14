package com.tigercard.master.entity.service;

import com.tigercard.master.entity.Capping;
import com.tigercard.master.entity.Rate;
import com.tigercard.master.entity.RideRule;
import com.tigercard.master.entity.Trip;
import com.tigercard.master.entity.repository.DayOfWeekRepository;
import com.tigercard.master.entity.repository.RideRulesRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class RideRuleService {
    @Autowired
    private RideRulesRepository repository;


    public RideRule saveRule(RideRule rideRule) {
        return repository.save(rideRule);
    }

    public List<RideRule> getRideRules() {
        return repository.findAll();
    }

    public void delete(long id) {
        RideRule rule = new RideRule(id);

        repository.delete(rule);
    }

    public RideRule getRideRuleByTrip(Trip trip) {
        List<RideRule> rules = repository.getRideRule(trip.getTime(), trip.getTime(),
                trip.getZoneFrom(), trip.getZoneTo(), trip.getDay().getDayId());

        RideRule finalRule = null;
        if (rules != null && rules.size() > 1) {
            finalRule = rules.stream()
                    .filter(rideRule ->
                            rideRule.getZoneFrom() != null && rideRule.getZoneTo() != null
                                    && rideRule.getZoneFrom().getZoneId() == trip.getZoneFrom().getZoneId()
                                    && rideRule.getZoneTo().getZoneId() == trip.getZoneTo().getZoneId())
                    .findFirst().get();
        } else if (rules != null && rules.size() == 1)
            finalRule = rules.get(0);

        // DEFAULT
        if (finalRule == null)
            finalRule = new RideRule(false);

        log.info("Rule Applied " + finalRule.getRuleId());
        return finalRule;
    }
}
