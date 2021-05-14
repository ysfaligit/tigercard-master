package com.tigercard.master.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "ride_rules")
public class RideRule {
    @Id
    @Column(name = "rule_id", length = 11, updatable = false, nullable = false, unique = true)
    private Long ruleId;

    // time in format "hh:mm:ss"
    @Column(name = "from_time", nullable = false)
    private Time fromTime;

    // time in format "hh:mm:ss"
    @Column(name = "to_time", nullable = false)
    private Time toTime;

    @Column(name = "flag_peak")
    private Boolean peak;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "fk_zone_from")
    private Zone zoneFrom;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "fk_zone_to")
    private Zone zoneTo;

    @ManyToMany(cascade = {CascadeType.MERGE})
    private Set<DayOfWeek> days = new HashSet<>();

    @Transient
    private Boolean weekdays;

    @Transient
    private Boolean weekend;

    public RideRule(long id) {
        this.ruleId = id;
    }

    public RideRule(boolean isPeak) {
        this.peak = isPeak;
    }
}
