package com.tigercard.master.entity.service;

import com.tigercard.master.entity.DayOfWeek;
import com.tigercard.master.entity.RideRule;
import com.tigercard.master.entity.RideRulePK;
import com.tigercard.master.entity.repository.DayOfWeekRepository;
import com.tigercard.master.entity.repository.RideRulesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RideRuleService {
    @Autowired
    private RideRulesRepository repository;

    @Autowired
    private DayOfWeekRepository dayOfWeekRepository;


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
}
