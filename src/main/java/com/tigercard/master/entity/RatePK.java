package com.tigercard.master.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RatePK implements Serializable {
    @Column(name = "rate_id", nullable = false, unique = true, updatable = false, length = 11)
    private Long rateId;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ManyToOne(fetch = FetchType.LAZY, optional = false, cascade = CascadeType.MERGE)
    @JoinColumn(name = "fk_zone_from", nullable = false)
    private Zone zoneFrom;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ManyToOne(fetch = FetchType.LAZY, optional = false, cascade = CascadeType.MERGE)
    @JoinColumn(name = "fk_zone_to", nullable = false)
    private Zone zoneTo;

    public RatePK(Zone from, Zone to) {
        this.zoneFrom = from;
        this.zoneTo = to;
    }
}
