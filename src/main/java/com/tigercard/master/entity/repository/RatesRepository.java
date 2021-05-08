package com.tigercard.master.entity.repository;

import com.tigercard.master.entity.Rate;
import com.tigercard.master.entity.Zone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RatesRepository extends JpaRepository<Rate, Long> {
    public Rate findByIdZoneFromAndIdZoneTo(Zone zoneFrom, Zone zoneTo);

    void deleteByIdRateId(long id);
}
