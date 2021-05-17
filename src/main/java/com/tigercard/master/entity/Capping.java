package com.tigercard.master.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Capping implements Serializable {
    @Id
    @Column(name = "capping_id")
    private Long cappingId;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "fk_zone_from", nullable = false)
    private Zone zoneFrom;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "fk_zone_to", nullable = false)
    private Zone zoneTo;

    @Column(name = "daily_cap", length = 11)
    private Integer dailyCap;

    @Column(name = "weekly_cap", length = 11)
    private Integer weeklyCap;

    public Capping(long id) {
        this.cappingId = id;
    }
}
