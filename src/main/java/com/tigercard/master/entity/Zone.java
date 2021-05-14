package com.tigercard.master.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "")
public class Zone implements Serializable {
    @Id
    @Column(name = "zone_id", unique = true, nullable = false,length = 11,updatable = false)
    private Long zoneId;

    private String name;

    public Zone(Long zone) {
        this.zoneId = zone;
    }
}
