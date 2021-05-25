package com.tigercard.master.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RuleAction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = CascadeType.MERGE)
    private RuleCondition ruleCondition;

    private Boolean flagPeak;
    private Integer discPerc;
    private Integer discFixed;

    public RuleAction(boolean flagPeak) {
        this.flagPeak = flagPeak;
    }
}
