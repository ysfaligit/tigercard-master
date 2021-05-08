package com.tigercard.master.entity.repository;

import com.tigercard.master.entity.RideRule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RideRulesRepository extends JpaRepository<RideRule, Long> {
}
