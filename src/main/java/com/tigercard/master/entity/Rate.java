package com.tigercard.master.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Rate {
    @EmbeddedId
    private RatePK id;

    private int peakRate;

    private int offPeakRate;


    public Rate(RatePK ratePK) {
        this.id = ratePK;
    }
}
