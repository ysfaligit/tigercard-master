package com.tigercard.master.entity.repository;

import com.tigercard.master.entity.Zone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ZoneRepository extends JpaRepository<Zone, String> {
    Zone getZoneByZoneId(String id);
}
