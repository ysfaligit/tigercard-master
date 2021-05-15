package com.tigercard.master.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Transient;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TripRequestDto {

    private Date punchTime;
    private Date fromDate;
    private Date toDate;
    private long cardId;
}
