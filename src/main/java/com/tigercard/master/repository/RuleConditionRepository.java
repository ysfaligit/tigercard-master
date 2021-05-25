package com.tigercard.master.repository;

import com.tigercard.master.entity.RuleCondition;
import com.tigercard.master.entity.Zone;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.Time;
import java.util.Date;
import java.util.List;

@Repository
public interface RuleConditionRepository extends JpaRepository<RuleCondition, Long> {

    @Query(value = "SELECT r FROM RuleCondition r join r.days d " +
            "WHERE r.fromTime <= ?1 AND r.toTime > ?2 " +
            "AND (r.fromDate is NULL or r.fromDate <= ?3) AND (r.toDate is NULL or r.toDate > ?4) " +
            "AND (r.zoneFrom = ?5 OR r.zoneFrom is NULL) and (r.zoneTo =?6 OR r.zoneTo is NULL) " +
            "AND (r.fromAge <= ?7 OR r.fromAge is NULL) and (r.toAge =?7 OR r.toAge is NULL) " +
            "AND d.dayId = ?8 and r.active = true")
    List<RuleCondition> getRideRule(Time fromTime, Time toTime, Date fromDate, Date toDate, Zone fromZone,
                                    Zone toZone, Integer age, long dayOfWeek);

    List<RuleCondition> findTripsByActive(Boolean aTrue, Sort priority);
}
