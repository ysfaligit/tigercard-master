package com.tigercard.master.entity.repository;

import com.tigercard.master.entity.RuleAction;
import com.tigercard.master.entity.RuleCondition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RuleActionRepository extends JpaRepository<RuleAction, Long> {

//    @Cacheable(
//            value = "fifteen-min-cache",
//            key = "'ruleAction-' + #ruleCondition.ruleId")
    public Optional<RuleAction> findByRuleCondition(RuleCondition ruleCondition);
}
