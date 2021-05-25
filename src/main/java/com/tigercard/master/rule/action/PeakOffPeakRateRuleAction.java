package com.tigercard.master.rule.action;

import com.tigercard.master.entity.Rate;
import com.tigercard.master.entity.RuleAction;
import com.tigercard.master.entity.Trip;
import com.tigercard.master.service.RateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PeakOffPeakRateRuleAction extends AbstractRuleAction {

    public PeakOffPeakRateRuleAction() {
        super.priority = 1;
    }

    @Autowired
    private RateService rateService;

    @Override
    protected void apply(RuleAction ruleAction, Trip newTrip) {
        // CREATE ZONE KEY
        String key = newTrip.getZoneFrom().getZoneId() + "-" + newTrip.getZoneTo().getZoneId();
        Rate rate = rateService.getRateByZones(newTrip.getZoneFrom().getZoneId(), newTrip.getZoneTo().getZoneId());
        if (ruleAction.getFlagPeak()) {
            newTrip.setFlagPeak(Boolean.TRUE);
            newTrip.setOriginalFare(rate.getPeakRate());
            newTrip.setExplanation("Peak hours Single fare");
        } else {
            newTrip.setFlagPeak(Boolean.FALSE);
            newTrip.setOriginalFare(rate.getOffPeakRate());
            newTrip.setExplanation("Off-peak single fare");
        }
    }
}
