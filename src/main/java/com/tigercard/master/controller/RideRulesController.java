package com.tigercard.master.controller;

import com.tigercard.master.entity.RuleCondition;
import com.tigercard.master.service.RideRuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/riderules")
public class RideRulesController {
    @Autowired
    private RideRuleService rideRuleService;

    @PutMapping("/")
    public RuleCondition save(@RequestBody RuleCondition ruleCondition){
        return rideRuleService.saveRule(ruleCondition);
    }

    @GetMapping("/")
    public List<RuleCondition> getAllRideRules() {
        return rideRuleService.getRideRules();
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") long id) {
        rideRuleService.delete(id);
    }
}
