package com.tigercard.master.entity.repository;

import com.tigercard.master.entity.DayOfWeek;
import com.tigercard.master.entity.TigerCard;
import com.tigercard.master.entity.WeeklyTrip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

import javax.transaction.Transactional;
import java.util.List;

@Transactional()
@Repository
public interface WeeklyTripRepository extends JpaRepository<WeeklyTrip, Long> {

    List<WeeklyTrip> getWeeklyTripsByCardAndWeekOfYear(TigerCard card, Integer weekOfYear);
    @Modifying
    @Query("UPDATE WeeklyTrip wt SET wt.maxCappedZone = :maxCappedZone, wt.maxCappedFare = :maxCappedFare, wt.calculatedDailyFare = :calculatedDailyFare WHERE wt.weeklyTripId = :weeklyTripId")
    void updateWeeklyTripByDayAndWeekOfYear(@PathVariable("maxCappedZone") String maxCappedZone,
                                            @PathVariable("maxCappedFare") int maxCappedFare,
                                            @PathVariable("calculatedDailyFare") Integer calculatedDailyFare,
                                            @PathVariable("weeklyTripId")  long weeklyTripId
                                            );

}
