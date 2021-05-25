package com.tigercard.master.rule.action;

import com.tigercard.master.dto.TripContextDto;

public interface IRuleAction {
    void apply(TripContextDto contextDto);
}
