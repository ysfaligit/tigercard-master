package com.tigercard.master.dto;

import com.tigercard.master.entity.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Data
public class TripContextDto {
    private TigerCard card;
    private Trip newTrip;

    // DAILY TRIP DATA
    private List<Trip> totalDailyTrips;
    private int dailyCapping;
    private Trip dailyCappingMaxFaredTrip;

    // WEEKLY CAP DATA
    private WeeklyTrip weeklyTripOfCurrentDay;
    private int summationWeeklyFareExclCurrentDayTrips;
    private int weeklyCapping;


    // RULE CONDITION
    private RuleCondition ruleConditionApplied;

    // RULE ACTION APPLIED
    private RuleAction ruleActionApplied;
}
