package com.tigercard.master.entity.service;

import com.tigercard.master.dto.TripRequestDto;
import com.tigercard.master.dto.TripResponseDto;
import com.tigercard.master.entity.*;
import com.tigercard.master.entity.repository.TripRepository;
import com.tigercard.master.entity.repository.WeeklyTripRepository;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

@Service
@Slf4j
public class TripService implements ITripService {

    @Autowired
    IFareCalculator fareCalculator;
    private TripContext tripContext = new TripContext();
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
    @Value("${trip.sort.columns}")
    private String[] sortColumns;

    @Override
    public TripResponseDto getTripsByCard(TigerCard card) {
        TripResponseDto tripResponseDto = new TripResponseDto();

        tripResponseDto.setTrips(tripRepository.getTripsByCard(card, Sort.by(sortColumns)));
        tripResponseDto.setTripsTotal(tripResponseDto.getTrips().stream().mapToInt(value -> value.getFare()).sum());
        return tripResponseDto;
    }

    @Override
    public Trip save(TripRequestDto newTrip) {
        return processTrip(prepareData(newTrip));
    }

    @Override
    public TripResponseDto getTrips() {
        TripResponseDto tripResponseDto = new TripResponseDto();
        tripResponseDto.setTrips(tripRepository.findAll());
        tripResponseDto.setTripsTotal(tripResponseDto.getTrips().stream().mapToInt(value -> value.getFare()).sum());
        return tripResponseDto;
    }

    @Override
    public TripResponseDto getTripsByCardAndDateRange(TripRequestDto tripRequestDto) {
        TripResponseDto tripResponseDto = new TripResponseDto();


        tripResponseDto.setTrips(tripRepository.getTripsByCardAndPunchTimeBetween
                (new TigerCard(tripRequestDto.getCardId()), tripRequestDto.getFromDate(), tripRequestDto.getToDate(),
                        Sort.by(sortColumns)));
        tripResponseDto.setTripsTotal(tripResponseDto.getTrips().stream().mapToInt(value -> value.getFare()).sum());
        return tripResponseDto;
    }

    @Override
    public void save(List<TripRequestDto> trips) throws Exception {
        trips.forEach(tripRequestDto -> {
            save(tripRequestDto);
        });
    }


    /////////////////
//    PRIVATE METHODS  //
    /////////////////
    private Trip processTrip(Trip newTrip) {
        populatePreRequisiteData(newTrip);

        log.info("Calculating Trip Fare..");
        fareCalculator.calculate(tripContext);
        log.info("Calculation done");

        saveUpdateNewTrip();
        log.info("Newly Trip Saved Successfully");

        saveUpdateWeeklyTrip();
        log.info("Weekly Trip Saved Successfully");

        return tripContext.getNewTrip();
    }


    private Trip prepareData(TripRequestDto newTrip) {
        Trip t = new Trip();
        t.setPunchTime(newTrip.getPunchTime());
        t.setZoneFrom(new Zone(newTrip.getZoneFrom()));
        t.setZoneTo(new Zone(newTrip.getZoneTo()));
        t.setCard(new TigerCard(newTrip.getCardId()));
        return t;
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

    private boolean isPeak(Trip newTrip) {
        return rideRuleService.getRideRuleByTrip(newTrip).getPeak();
    }

    private void populateBaseFareByRideRuleConditionApplied(Trip trip) {
        // CREATE ZONE KEY
        String key = trip.getZoneFrom().getZoneId() + "-" + trip.getZoneTo().getZoneId();
        Rate rate = rateService.getRateByZones(trip.getZoneFrom().getZoneId(), trip.getZoneTo().getZoneId());
        if (isPeak(trip)) {
            trip.setFlagPeak(Boolean.TRUE);
            trip.setOriginalFare(rate.getPeakRate());
            trip.setExplanation("Peak hours Single fare");
        } else {
            trip.setFlagPeak(Boolean.FALSE);
            trip.setOriginalFare(rate.getOffPeakRate());
            trip.setExplanation("Off-peak single fare");
        }
    }

    private void populateDailyCappingData(TripContext tripContext) {
        // LOAD ALL DAILY TRIPS + ADD NEW TRIP
        tripContext.setTotalDailyTrips(tripRepository.getTripsByCardAndDayAndWeekOfYear(tripContext.getNewTrip().getCard(),
                tripContext.getNewTrip().getDay(), tripContext.getNewTrip().getWeekOfYear()));

        //
        tripContext.getTotalDailyTrips().add(tripContext.getNewTrip());

        // SORT BY ORIGINAL FARE TO CALCULATE MAX_DAILY_FARE_CAPPING
        Collections.sort(tripContext.getTotalDailyTrips(), (o1, o2) -> o2.getOriginalFare().compareTo(o1.getOriginalFare()));

        // AFTER SORTING BY BASE FARE DESC, TAKING FIRST ELEMENT FOR HIGHEST CAPPED FARE
        Trip dailyCappingMaxFaredTrip = tripContext.getTotalDailyTrips().get(0);
        tripContext.setDailyCappingMaxFaredTrip(dailyCappingMaxFaredTrip);

        // DETERMINING DAILY CAPPING
        tripContext.setDailyCapping(cappingService
                .getCappingByZoneFromAndZoneTo(dailyCappingMaxFaredTrip.getZoneFrom().getZoneId(),
                        dailyCappingMaxFaredTrip.getZoneTo().getZoneId()).getDailyCap());

        // RE-ARRANGING BY PUNCHTIME DESC
        Collections.sort(tripContext.getTotalDailyTrips(), (o1, o2) -> o1.getPunchTime().compareTo(o2.getPunchTime()));

    }

    private void populateWeeklyCappingData(TripContext tripContext) {
        // WEEKLY TRIP PROCESSING
        Trip newTrip = tripContext.getNewTrip();
        Trip dailyCappingMaxFaredTrip = tripContext.getDailyCappingMaxFaredTrip();
        String maxDailyCappedZoneKey = dailyCappingMaxFaredTrip.getZoneFrom().getZoneId() + "-" + dailyCappingMaxFaredTrip.getZoneTo().getZoneId();

        // LOAD ALL WEEKLY TRIPS + ADD NEW TRIP
        List<WeeklyTrip> weeklyTrips = weeklyTripRepository.getWeeklyTripsByCardAndWeekOfYear(newTrip.getCard(),
                newTrip.getWeekOfYear());

        // SUMMATION OF TRIPS FARE FOR ENTIRE WEEK EXCLUDING PUNCHED DATE TRIPS
        tripContext.setSummationWeeklyFareExclCurrentDayTrips(
                weeklyTrips.stream()
                        .filter(weeklyTrip -> weeklyTrip.getDay().getDayId() != newTrip.getDay().getDayId())
                        .mapToInt(value -> value.getCalculatedDailyFare()).sum());

        // WEEKLY TRIP RECORD OF CURRENT DAY
        WeeklyTrip weeklyTripOfCurrentDay = weeklyTrips.stream()
                .filter(weeklyTrip -> weeklyTrip.getDay().getDayId() == newTrip.getDay().getDayId())
                .findFirst()
                .orElse(new WeeklyTrip(maxDailyCappedZoneKey, dailyCappingMaxFaredTrip.getOriginalFare(),
                        newTrip.getWeekOfYear(), newTrip.getDay(),
                        newTrip.getCard()));

        if (weeklyTripOfCurrentDay.getWeeklyTripId() == null)
            weeklyTrips.add(weeklyTripOfCurrentDay);
        else {
            weeklyTripOfCurrentDay.setMaxCappedFare(dailyCappingMaxFaredTrip.getOriginalFare());
            weeklyTripOfCurrentDay.setMaxCappedZone(maxDailyCappedZoneKey);
        }
        tripContext.setWeeklyTripOfCurrentDay(weeklyTripOfCurrentDay);

        // SORTING WEEKLY TRIPS BY BASE FARE DESC
        Collections.sort(weeklyTrips, (o1, o2) -> Integer.valueOf(o2.getMaxCappedFare())
                .compareTo(o1.getMaxCappedFare()));

        // DETERMINING WEEKLY CAPPING BY PROCESS FIRST ELEMENT SORTED WEEKLY TRIPS
        // BELOW METHOD USES EHCACHE
        tripContext.setWeeklyCapping(cappingService.getCappingByZoneFromAndZoneTo(
                Long.parseLong(weeklyTrips.get(0).getMaxCappedZone().split("-")[0]),
                Long.parseLong(weeklyTrips.get(0).getMaxCappedZone().split("-")[1])).getWeeklyCap());
    }

    private void populatePreRequisiteData(Trip newTrip) {
        tripContext.setNewTrip(newTrip);
        populateDateAttributes(newTrip);
        populateBaseFareByRideRuleConditionApplied(newTrip);
        populateDailyCappingData(tripContext);
        log.info("DailyTrips Data Loaded..");

        populateWeeklyCappingData(tripContext);
        log.info("Weekly Data Loaded..");
    }

    private void saveUpdateWeeklyTrip() {
        WeeklyTrip weeklyTripOfCurrentDay = tripContext.getWeeklyTripOfCurrentDay();

        // UPDATING
        if (weeklyTripOfCurrentDay.getWeeklyTripId() == null)
            weeklyTripRepository.save(weeklyTripOfCurrentDay);
        else
            // CREATING NEW
            weeklyTripRepository.updateWeeklyTripByDayAndWeekOfYear(weeklyTripOfCurrentDay.getMaxCappedZone(),
                    weeklyTripOfCurrentDay.getMaxCappedFare(), weeklyTripOfCurrentDay.getCalculatedDailyFare(),
                    weeklyTripOfCurrentDay.getWeeklyTripId());
    }

    private void saveUpdateNewTrip() {
        // UPDATING EXISTING TRIP WITH NEWLY CALCULATED FARES
        tripContext.getTotalDailyTrips().stream().filter(trip -> trip.getTripId() != null && trip.getTripId() > 0)
                .forEach(trip -> tripRepository.updateTripDetails(trip.getTripId(), trip.getExplanation(), trip.getFare()));

        // SAVE NEW TRIP
        tripRepository.save(tripContext.getNewTrip());
    }
}


@NoArgsConstructor
@Data
class TripContext {
    private Trip newTrip;

    // DAILY TRIP DATA
    private List<Trip> totalDailyTrips;
    private int dailyCapping;
    private Trip dailyCappingMaxFaredTrip;

    // WEEKLY CAP DATA
    private WeeklyTrip weeklyTripOfCurrentDay;
    private int summationWeeklyFareExclCurrentDayTrips;
    private int weeklyCapping;

}