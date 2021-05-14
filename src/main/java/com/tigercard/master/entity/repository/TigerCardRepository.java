package com.tigercard.master.entity.repository;

import com.tigercard.master.entity.TigerCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TigerCardRepository extends JpaRepository<TigerCard, Double> {

    void deleteByCardId(double id);
}
