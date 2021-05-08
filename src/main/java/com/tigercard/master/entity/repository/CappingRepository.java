package com.tigercard.master.entity.repository;

import com.tigercard.master.entity.Capping;
import com.tigercard.master.entity.Zone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CappingRepository extends JpaRepository<Capping, Long> {
    Capping findByZoneFromAndZoneTo(Zone zoneFrom, Zone zoneTo);
}
