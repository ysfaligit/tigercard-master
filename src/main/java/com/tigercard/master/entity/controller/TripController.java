package com.tigercard.master.entity.controller;

import com.tigercard.master.dto.TripRequestDto;
import com.tigercard.master.dto.TripResponseDto;
import com.tigercard.master.entity.TigerCard;
import com.tigercard.master.entity.Trip;
import com.tigercard.master.entity.service.TripService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.List;

@RestController
@RequestMapping("/trip")
@Slf4j
public class TripController {
    @Autowired
    private TripService tripService;
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");

    @PostMapping("/")
    public Trip save(@RequestBody Trip trip) {
        return tripService.save(trip);
    }

    @GetMapping("/{card}")
    public List<Trip> getAllTripsByCard(@PathVariable("card") long cardId) {
        return tripService.getTripsByCard(new TigerCard(cardId));
    }

    @PostMapping("/report")
    public ResponseEntity<TripResponseDto> getTripsByCardAndDateRange(@RequestBody TripRequestDto tripRequestDto) {
        TripResponseDto tripResponseDto = new TripResponseDto();
        try {
            if (tripRequestDto.getFromDate().compareTo(tripRequestDto.getToDate()) > 0) {
                tripResponseDto.setErrorMessage("From Date cannot be bigger than To Date.");
                return ResponseEntity.badRequest().body(tripResponseDto);
            }


            tripResponseDto.setTrips(tripService.
                    getTripsByCardAndDateRange(tripRequestDto.getCardId(),
                            tripRequestDto.getFromDate(), tripRequestDto.getToDate()));
            tripResponseDto.setTotalTrip(tripResponseDto.getTrips().stream().mapToInt(value -> value.getFare()).sum());

            return ResponseEntity.ok(tripResponseDto);


        } catch (Exception e) {
            tripResponseDto.setErrorMessage("Internal Error : " + e.getMessage());
        }
        return ResponseEntity.badRequest().body(tripResponseDto);
    }

    @GetMapping("/report")
    public List<Trip> getAllTrips() {
        return tripService.getTrips();
    }
}
