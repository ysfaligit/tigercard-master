package com.tigercard.master.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.lang.NonNull;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "days_of_week")
public class DayOfWeek {
    @Id
    @NonNull
    @Column(name = "day_id", unique = true, updatable = false)
    private long dayId;

    private String day;
    @ManyToMany(mappedBy = "days", fetch = FetchType.LAZY)
//    @Fetch(value= FetchMode.SELECT)
    private Set<RideRule> rideRules = new HashSet<>();

    public DayOfWeek(long dayId) {
        this.dayId = dayId;
    }
}
