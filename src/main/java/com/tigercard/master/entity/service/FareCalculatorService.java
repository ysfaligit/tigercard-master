package com.tigercard.master.entity.service;

import com.tigercard.master.entity.Trip;
import com.tigercard.master.entity.WeeklyTrip;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FareCalculatorService implements IFareCalculator {
    @Override
    public void calculate(TripContext tripContext) {
        List<Trip> totalDailyTrips = tripContext.getTotalDailyTrips();
        int summationOfDailyFareTrips = 0;
        int dailyCapping = tripContext.getDailyCapping();

        WeeklyTrip weeklyTripOfCurrentDay = tripContext.getWeeklyTripOfCurrentDay();
        int weeklyCapping = tripContext.getWeeklyCapping();
        int summationWeeklyFareExclCurrentDayTrips = tripContext.getSummationWeeklyFareExclCurrentDayTrips();

        boolean flagDailyFareMaxReached = Boolean.FALSE;
        boolean flagWeeklyFareMaxReached = Boolean.FALSE;

        for (Trip trip : totalDailyTrips) {
            if (flagWeeklyFareMaxReached) {
                trip.setFare(0);
                String explanation = "Weekly cap reached of Rs : " + weeklyCapping;

                trip.setExplanation(explanation);
                weeklyTripOfCurrentDay.setExplanation(explanation);
                continue;

            } else if (flagDailyFareMaxReached) {
                trip.setFare(0);
                trip.setExplanation("Daily cap reached of Rs : " + dailyCapping);

                weeklyTripOfCurrentDay.setExplanation("Daily cap reached of Rs : " + dailyCapping);
                continue;
            }

            summationOfDailyFareTrips += trip.getOriginalFare();

            if (summationOfDailyFareTrips + summationWeeklyFareExclCurrentDayTrips >= weeklyCapping) {
                flagWeeklyFareMaxReached = Boolean.TRUE;

                trip.setFare(weeklyCapping - (summationWeeklyFareExclCurrentDayTrips + summationOfDailyFareTrips - trip.getOriginalFare()));
                summationOfDailyFareTrips = summationOfDailyFareTrips - trip.getOriginalFare() + trip.getFare();

                trip.setExplanation("Weekly cap reached of Rs : " + weeklyCapping + " for Zone " + trip.getZoneFrom().getZoneId()
                        + "-" + trip.getZoneTo().getZoneId() + ". Charging " + trip.getFare()
                        + " instead of " + trip.getOriginalFare());

                weeklyTripOfCurrentDay.setExplanation("Weekly cap reached of Rs : " + weeklyCapping + " before daily cap of " + dailyCapping);

            } else if (summationOfDailyFareTrips >= dailyCapping) {
                flagDailyFareMaxReached = Boolean.TRUE;

                trip.setFare(dailyCapping - (summationOfDailyFareTrips - trip.getOriginalFare()));
                summationOfDailyFareTrips = summationOfDailyFareTrips - trip.getOriginalFare() + trip.getFare();

                trip.setExplanation("Daily cap reached of Rs : " + dailyCapping + ". Charging " + trip.getFare()
                        + " instead of " + trip.getOriginalFare());


                weeklyTripOfCurrentDay.setExplanation("Daily Cap of " + dailyCapping + " Reached");
            } else if (summationOfDailyFareTrips < dailyCapping) {
                trip.setFare(trip.getOriginalFare());
                if (trip.getDay().getDayId() == 1)
                    weeklyTripOfCurrentDay.setExplanation("New Week Started");
                weeklyTripOfCurrentDay.setExplanation("Daily Cap Not Reached");
            }
        }
        weeklyTripOfCurrentDay.setCalculatedDailyFare(summationOfDailyFareTrips);
    }
}
