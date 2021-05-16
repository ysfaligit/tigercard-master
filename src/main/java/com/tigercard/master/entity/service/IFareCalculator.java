package com.tigercard.master.entity.service;

import org.springframework.stereotype.Service;

@Service
public interface IFareCalculator<T extends TripContext> {
    void calculate(T context);
}
