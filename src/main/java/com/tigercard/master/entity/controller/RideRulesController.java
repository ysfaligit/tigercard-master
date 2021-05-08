package com.tigercard.master.entity.controller;

import com.tigercard.master.entity.DayOfWeek;
import com.tigercard.master.entity.RideRule;
import com.tigercard.master.entity.service.RideRuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/riderules")
public class RideRulesController {
    @Autowired
    private RideRuleService rideRuleService;

    @PutMapping("/")
    public RideRule save(@RequestBody RideRule rideRule){
        return rideRuleService.saveRule(rideRule);
    }

    @GetMapping("/")
    public List<RideRule> getAllRideRules() {
        return rideRuleService.getRideRules();
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") long id) {
        rideRuleService.delete(id);
    }
}
