package com.tigercard.master.entity.service;

import com.tigercard.master.dto.TripRequestDto;
import com.tigercard.master.dto.TripResponseDto;
import com.tigercard.master.entity.*;
import com.tigercard.master.entity.repository.TripRepository;
import com.tigercard.master.entity.repository.WeeklyTripRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

@Service
@Slf4j
public class TripService {
    Map<String, Integer> dailyCappings = new HashMap<>();
    Map<String, Integer> weeklyCappings = new HashMap<>();
    Map<String, Rate> rates = new HashMap<>();

    @Autowired
    private TripRepository tripRepository;

    @Autowired
    private RideRuleService rideRuleService;

    @Autowired
    private RateService rateService;

    @Autowired
    private CappingService cappingService;

    @Autowired
    private WeeklyTripRepository weeklyTripRepository;


    @Value("punchTime,time")
    private String[] sortColumns;


    public TripResponseDto getTripsByCard(TigerCard card) {
        TripResponseDto tripResponseDto = new TripResponseDto();

        tripResponseDto.setTrips(tripRepository.getTripsByCard(card, Sort.by(sortColumns)));
        tripResponseDto.setTotalTrip(tripResponseDto.getTrips().stream().mapToInt(value -> value.getFare()).sum());
        return tripResponseDto;
    }

    Trip prepareData(TripRequestDto newTrip) {
        Trip t = new Trip();
        t.setPunchTime(newTrip.getPunchTime());
        t.setZoneFrom(new Zone(newTrip.getZoneFrom()));
        t.setZoneTo(new Zone(newTrip.getZoneTo()));
        t.setCard(new TigerCard(newTrip.getCardId()));
        return t;
    }

    public Trip save(TripRequestDto newTrip) {
        return processDailyTrip(prepareData(newTrip));
    }

    private Trip processDailyTrip(Trip newTrip) {
        populateDateAttributes(newTrip);

        // LOAD DAILY & WEEKLY CAPINGS
        cappingService.getAllCappings().stream().forEach(capping -> {
            String key = capping.getZoneFrom().getZoneId() + "-" + capping.getZoneTo().getZoneId();
            this.dailyCappings.put(key, capping.getDailyCap());
            this.weeklyCappings.put(key, capping.getWeeklyCap());
        });


        // LOAD ALL RATES
        rateService.getRates().stream().forEach(rate -> {
            String key = rate.getId().getZoneFrom().getZoneId() + "-" + rate.getId().getZoneTo().getZoneId();
            rates.put(key, rate);
        });

        // LOAD ALL DAILY TRIPS + ADD NEW TRIP
        List<Trip> totalDailyTrips = tripRepository.getTripsByCardAndDayAndWeekOfYear(newTrip.getCard(),
                newTrip.getDay(), newTrip.getWeekOfYear());
        totalDailyTrips.add(newTrip);

        // POPULATE ORIGINAL FARE IN OLD + NEWLY ADDED TRIP
        populateOriginalFares(totalDailyTrips);

        // SORT BY ORIGINAL FARE TO CALCULATE MAX_DAILY_FARE_CAPPING
        Collections.sort(totalDailyTrips, (o1, o2) -> o2.getOriginalFare().compareTo(o1.getOriginalFare()));

        Trip maxFaredTrip = totalDailyTrips.get(0);
        String maxDailyCappedZoneKey = maxFaredTrip.getZoneFrom().getZoneId() + "-" + maxFaredTrip.getZoneTo().getZoneId();
        int dailyCapping = dailyCappings.get(maxDailyCappedZoneKey);


        // WEEKLY TRIP PROCESSING
        // LOAD ALL WEEKLY TRIPS + ADD NEW TRIP
        List<WeeklyTrip> weeklyTrips = weeklyTripRepository.getWeeklyTripsByCardAndWeekOfYear(newTrip.getCard(),
                newTrip.getWeekOfYear());

        int summationWeeklyFareExclCurrentDay =
                weeklyTrips.stream()
                        .filter(weeklyTrip -> weeklyTrip.getDay().getDayId() != newTrip.getDay().getDayId())
                        .mapToInt(value -> value.getCalculatedDailyFare()).sum();

        WeeklyTrip weeklyTripOfCurrentDay = weeklyTrips.stream()
                .filter(weeklyTrip -> weeklyTrip.getDay().getDayId() == newTrip.getDay().getDayId())
                .findFirst().orElse(new WeeklyTrip(maxDailyCappedZoneKey, maxFaredTrip.getOriginalFare(),
                        newTrip.getWeekOfYear(), newTrip.getDay(),
                        newTrip.getCard()));

        if (weeklyTripOfCurrentDay.getWeeklyTripId() == null)
            weeklyTrips.add(weeklyTripOfCurrentDay);
        else {
            weeklyTripOfCurrentDay.setMaxCappedFare(maxFaredTrip.getOriginalFare());
            weeklyTripOfCurrentDay.setMaxCappedZone(maxDailyCappedZoneKey);
        }

        Collections.sort(weeklyTrips, (o1, o2) -> Integer.valueOf(o2.getMaxCappedFare())
                .compareTo(o1.getMaxCappedFare()));

        int weeklyCapping = weeklyCappings.get(weeklyTrips.get(0).getMaxCappedZone());

        // WEEKLY CAP PROCESSING END...
        int summationOfDailyFareTrips = 0;

        boolean flagDailyFareMaxReached = Boolean.FALSE;
        boolean flagWeeklyFareMaxReached = Boolean.FALSE;

        Collections.sort(totalDailyTrips, (o1, o2) -> o1.getPunchTime().compareTo(o2.getPunchTime()));
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

            if (summationOfDailyFareTrips + summationWeeklyFareExclCurrentDay >= weeklyCapping) {
                flagWeeklyFareMaxReached = Boolean.TRUE;

                trip.setFare(weeklyCapping - (summationWeeklyFareExclCurrentDay + summationOfDailyFareTrips - trip.getOriginalFare()));
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
        totalDailyTrips.stream().filter(trip -> trip.getTripId() != null && trip.getTripId() > 0)
                .forEach(trip -> tripRepository.updateTripDetails(trip.getTripId(), trip.getExplanation(), trip.getFare()));

        tripRepository.save(newTrip);

        if (weeklyTripOfCurrentDay.getWeeklyTripId() == null)
            weeklyTripRepository.save(weeklyTripOfCurrentDay);
        else
            weeklyTripRepository.updateWeeklyTripByDayAndWeekOfYear(weeklyTripOfCurrentDay.getMaxCappedZone(),
                    weeklyTripOfCurrentDay.getMaxCappedFare(), weeklyTripOfCurrentDay.getCalculatedDailyFare(),
                    weeklyTripOfCurrentDay.getWeeklyTripId());

        return newTrip;
    }

    private void populateDateAttributes(Trip trip) {
        // LOADING RULE TO BE APPLIED
        LocalDate l = trip.getPunchTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        Calendar c = Calendar.getInstance();
        c.setTime(trip.getPunchTime());

        trip.setTime(new Time(c.getTimeInMillis()));
        trip.setWeekOfYear(c.get(Calendar.WEEK_OF_YEAR));
        trip.setDay(new DayOfWeek(l.getDayOfWeek().getValue()));
    }

    boolean isPeak(Trip newTrip) {
        return rideRuleService.getRideRuleByTrip(newTrip).getPeak();
    }

    void populateOriginalFares(List<Trip> totalDailyTrips) {
        totalDailyTrips.stream().forEach(trip -> {
            // CREATE ZONE KEY
            String key = trip.getZoneFrom().getZoneId() + "-" + trip.getZoneTo().getZoneId();
            if (isPeak(trip)) {
                trip.setFlagPeak(Boolean.TRUE);
                trip.setOriginalFare(rates.get(key).getPeakRate());
                trip.setExplanation("Peak hours Single fare");
            } else {
                trip.setFlagPeak(Boolean.FALSE);
                trip.setOriginalFare(rates.get(key).getOffPeakRate());
                trip.setExplanation("Off-peak single fare");
            }
        });
    }

    void applyRates(Trip newTrip) {
        // CREATE ZONE KEY
        String key = newTrip.getZoneFrom().getZoneId() + "-" + newTrip.getZoneTo().getZoneId();

        int currentTripRate;

        if (isPeak(newTrip)) {
            currentTripRate = rates.get(key).getPeakRate();
            newTrip.setFare(currentTripRate);
            newTrip.setOriginalFare(currentTripRate);
            newTrip.setExplanation("peak hours single fare");

            log.info("peak hours single fare");
        } else {
            currentTripRate = rates.get(key).getOffPeakRate();
            newTrip.setFare(currentTripRate);
            newTrip.setOriginalFare(currentTripRate);
            newTrip.setExplanation("off peak single fare");

            log.info("off peak single fare");
        }
    }

    public TripResponseDto getTrips() {
        TripResponseDto tripResponseDto = new TripResponseDto();
        tripResponseDto.setTrips(tripRepository.findAll());
        tripResponseDto.setTotalTrip(tripResponseDto.getTrips().stream().mapToInt(value -> value.getFare()).sum());
        return tripResponseDto;
    }


    public TripResponseDto getTripsByCardAndDateRange(TripRequestDto tripRequestDto) {
        TripResponseDto tripResponseDto = new TripResponseDto();


        tripResponseDto.setTrips(tripRepository.getTripsByCardAndPunchTimeBetween
                (new TigerCard(tripRequestDto.getCardId()), tripRequestDto.getFromDate(), tripRequestDto.getToDate(),
                        Sort.by(sortColumns)));
        tripResponseDto.setTotalTrip(tripResponseDto.getTrips().stream().mapToInt(value -> value.getFare()).sum());
        return tripResponseDto;
    }

    public void save(List<TripRequestDto> trips) throws Exception {
        trips.forEach(tripRequestDto -> {
            save(tripRequestDto);
        });
    }
}
