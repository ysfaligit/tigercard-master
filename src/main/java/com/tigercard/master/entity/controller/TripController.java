package com.tigercard.master.entity.controller;

import com.tigercard.master.entity.TigerCard;
import com.tigercard.master.entity.Trip;
import com.tigercard.master.entity.service.TripService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Time;
import java.util.Calendar;
import java.util.List;

@RestController
@RequestMapping("/trip")
@Slf4j
public class TripController {
    @Autowired
    private TripService tripService;

    @GetMapping("/card/{id}")
    public List<Trip> getTripsByCardId(@PathVariable("id") long cardId) {

        return tripService.getTripsByCard(new TigerCard(cardId));
    }

    @PostMapping("/")
    public void save(@RequestBody Trip trip) {
        tripService.save(trip);

//        return trip;
    }

    @GetMapping("/")
    public List<Trip> getAllTrips(){
        return tripService.getTrips();
    }
}
