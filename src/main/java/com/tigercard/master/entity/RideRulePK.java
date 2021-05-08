package com.tigercard.master.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

//@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RideRulePK implements Serializable {
    @Column(name = "rule_id", length = 11, updatable = false, nullable = false, unique = true)
    private Long ruleId;

    // time in format "hh:mm:ss"
    @Temporal(TemporalType.TIME)
    @Column(name = "from_time")
    private Date fromTime;

    // time in format "hh:mm:ss"
    @Temporal(TemporalType.TIME)
    @Column(name = "to_time")
    private Date toTime;

    public RideRulePK(long id) {
        this.ruleId = id;
    }
}
