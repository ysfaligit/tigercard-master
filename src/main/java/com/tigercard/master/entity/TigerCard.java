package com.tigercard.master.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tiger_card")
public class TigerCard {
    @Id
    @Column(name = "card_id")
    private long cardId = (long) Math.floor(Math.random() * (100000 - 1000)) + 1000;

    private String customer;
    private int age;

    @Column(name = "created_at", nullable = false)
    private Date createdAt = new Date();

    public TigerCard(long cardId) {
        this.cardId = cardId;
    }
}
