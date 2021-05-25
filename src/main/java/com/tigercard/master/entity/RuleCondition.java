package com.tigercard.master.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Time;
import java.util.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RuleCondition {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ruleId;

    // time in format "hh:mm:ss"
    @Column(name = "from_time", nullable = false)
    private Time fromTime;

    // time in format "hh:mm:ss"
    @Column(name = "to_time", nullable = false)
    private Time toTime;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "fk_zone_from")
    private Zone zoneFrom;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "fk_zone_to")
    private Zone zoneTo;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE, mappedBy = "ruleCondition")
    private RuleAction ruleAction;


    @ManyToMany(cascade = {CascadeType.MERGE})
    private Set<DayOfWeek> days = new HashSet<>();

    private boolean active;

    @Temporal(TemporalType.DATE)
    private Date fromDate;

    @Temporal(TemporalType.DATE)
    private Date toDate;

    private Integer fromAge;
    private Integer toAge;

    public RuleCondition(long id) {
        this.ruleId = id;
    }

    public RuleCondition(RuleAction ruleAction) {
        this.ruleAction = ruleAction;
    }
}
