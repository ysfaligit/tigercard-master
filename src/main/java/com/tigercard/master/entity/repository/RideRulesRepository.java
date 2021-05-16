package com.tigercard.master.entity.repository;

import com.tigercard.master.entity.DayOfWeek;
import com.tigercard.master.entity.RideRule;
import com.tigercard.master.entity.TigerCard;
import com.tigercard.master.entity.Zone;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.Time;
import java.util.Date;
import java.util.List;

@Repository
public interface RideRulesRepository extends JpaRepository<RideRule, Long> {

    @Query(value = "SELECT r FROM RideRule r join r.days d " +
            "WHERE r.fromTime <= ?1 AND r.toTime >= ?2 " +
            "AND (r.fromDate is NULL or r.fromDate <= ?3) AND (r.toDate is NULL or r.toDate >= ?4) " +
            "and (r.zoneFrom = ?5 OR r.zoneFrom is NULL) and (r.zoneTo =?6 OR r.zoneTo is NULL) " +
            "and d.dayId = ?7 and r.active = true")
    List<RideRule> getRideRule(Time fromTime, Time toTime, Date fromDate, Date toDate, Zone fromZone,
                               Zone toZone, long dayOfWeek);

    List<RideRule> findTripsByActive(Boolean aTrue, Sort priority);
}
