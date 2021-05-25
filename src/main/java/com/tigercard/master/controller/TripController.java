package com.tigercard.master.controller;

import com.tigercard.master.dto.TripRequestDto;
import com.tigercard.master.dto.TripResponseDto;
import com.tigercard.master.entity.TigerCard;
import com.tigercard.master.entity.Trip;
import com.tigercard.master.service.ITripService;
import com.tigercard.master.service.TripService;
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
    private ITripService tripService;

    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");

    @PutMapping("/save")
    public ResponseEntity<Trip> save(@RequestBody TripRequestDto trip) {
         return ResponseEntity.ok(tripService.save(trip));
    }

    @PutMapping("/saveAll")
    public ResponseEntity<String> saveAll(@RequestBody List<TripRequestDto> trips) {
        try {
            tripService.save(trips);

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.ok("Trips saved successfully");
    }

    @GetMapping("/{cardId}")
    public ResponseEntity<TripResponseDto> getAllTripsByCard(@PathVariable("cardId") long cardId) {
        return ResponseEntity.ok(tripService.getTripsByCard(new TigerCard(cardId)));
    }

    @GetMapping("/{cardId}/tripsTotal")
    public ResponseEntity<Integer> getTripsTotal(@PathVariable("cardId") long cardId) {
        return ResponseEntity.ok(tripService.getTripsByCard(new TigerCard(cardId)).getTripsTotal());
    }


    @PostMapping("/report")
    public ResponseEntity<TripResponseDto> getTripsByCardAndDateRange(@RequestBody TripRequestDto tripRequestDto) {
        TripResponseDto tripResponseDto = new TripResponseDto();
        try {
            if (tripRequestDto.getFromDate().compareTo(tripRequestDto.getToDate()) > 0) {
                tripResponseDto.setErrorMessage("From Date cannot be bigger than To Date.");
                return ResponseEntity.badRequest().body(tripResponseDto);
            }

            return ResponseEntity.ok(tripService.getTripsByCardAndDateRange(tripRequestDto));


        } catch (Exception e) {
            tripResponseDto.setErrorMessage("Internal Error : " + e.getMessage());
        }
        return ResponseEntity.badRequest().body(tripResponseDto);
    }

    @GetMapping("/")
    public ResponseEntity<TripResponseDto> getAllTrips() {
        return ResponseEntity.ok(tripService.getTrips());
    }
}
