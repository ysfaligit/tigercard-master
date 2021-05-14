package com.tigercard.master.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WeeklyTrip {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long weeklyTripId;

    private String maxCappedZone;

    private Integer maxCappedFare;

    private Integer calculatedDailyFare;

    private String explanation;

    private int weekOfYear;

    @NonNull
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "fk_day", nullable = false)
    private DayOfWeek day;

    @NonNull
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "fk_card", nullable = false, updatable = false)
    private TigerCard card;

    public WeeklyTrip(String maxDailyCappedZoneKey, int maxDailyCapping, int weekOfYear, DayOfWeek day, TigerCard card) {
        this.maxCappedZone = maxDailyCappedZoneKey;
        this.maxCappedFare = maxDailyCapping;
        this.weekOfYear = weekOfYear;
        this.day = day;
        this.card = card;

    }
}
