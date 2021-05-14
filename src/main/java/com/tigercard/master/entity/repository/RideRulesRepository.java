package com.tigercard.master.entity.repository;

import com.tigercard.master.entity.DayOfWeek;
import com.tigercard.master.entity.RideRule;
import com.tigercard.master.entity.Zone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.Time;
import java.util.List;

@Repository
public interface RideRulesRepository extends JpaRepository<RideRule, Long> {

    @Query(value = "SELECT r FROM RideRule r join r.days d " +
            "WHERE r.fromTime <= ?1 AND r.toTime > ?2 " +
            "and (r.zoneFrom = ?3 OR r.zoneFrom is NULL) and (r.zoneTo =?4 OR r.zoneTo is NULL) "
            + "and d.dayId = ?5")
    List<RideRule> getRideRule(Time time, Time time1, Zone fromZone, Zone toZone, long dayOfWeek);
}
