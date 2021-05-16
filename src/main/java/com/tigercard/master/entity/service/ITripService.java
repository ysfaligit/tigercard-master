package com.tigercard.master.entity.service;

import com.tigercard.master.dto.TripRequestDto;
import com.tigercard.master.dto.TripResponseDto;
import com.tigercard.master.entity.TigerCard;
import com.tigercard.master.entity.Trip;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ITripService {
    public TripResponseDto getTrips();

    public TripResponseDto getTripsByCard(TigerCard card);

    public TripResponseDto getTripsByCardAndDateRange(TripRequestDto tripRequestDto);

    public void save(List<TripRequestDto> trips) throws Exception;

    public Trip save(TripRequestDto newTrip);
}
