package com.tigercard.master.repository;

import com.tigercard.master.entity.Zone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ZoneRepository extends JpaRepository<Zone, Long> {
    Zone getZoneByZoneId(Long id);
}
