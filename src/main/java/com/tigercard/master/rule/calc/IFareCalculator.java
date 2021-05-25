package com.tigercard.master.rule.calc;

import com.tigercard.master.dto.TripContextDto;
import org.springframework.stereotype.Service;

@Service
public interface IFareCalculator<T extends TripContextDto> {
    void calculate(T context);
}
