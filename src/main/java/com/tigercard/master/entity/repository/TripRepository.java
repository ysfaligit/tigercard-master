package com.tigercard.master.entity.repository;

import com.tigercard.master.entity.DayOfWeek;
import com.tigercard.master.entity.TigerCard;
import com.tigercard.master.entity.Trip;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;

@Repository
@Transactional()
public interface TripRepository extends JpaRepository<Trip, Long> {

    List<Trip> getTripsByCard(TigerCard card, Sort punchTime);

    List<Trip> getTripsByCardAndWeekOfYear(TigerCard card, Integer weekOfYear, Sort fare);

    List<Trip> getTripsByCardAndDayAndWeekOfYear(TigerCard card, DayOfWeek day, Integer weekOfYear);

    @Modifying
    @Query("UPDATE Trip t SET t.fare = :fare, t.explanation = :explanation WHERE t.tripId = :tripId")
    void updateTripDetails(@Param("tripId") long tripId, @Param("explanation") String explanation,
                           @Param("fare") int fare);

}
