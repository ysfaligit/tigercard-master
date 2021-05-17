package com.tigercard.master.dto;

import com.tigercard.master.entity.Trip;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TripResponseDto {
    private List<Trip> trips;
    private int tripsTotal;
    private String errorMessage;
}
