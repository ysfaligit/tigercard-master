package com.tigercard.master.entity.service;

import com.tigercard.master.entity.DayOfWeek;
import com.tigercard.master.entity.repository.DayOfWeekRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DayOfWeekService {
    @Autowired
    private DayOfWeekRepository repository;

    public List<DayOfWeek> getDaysOfWeek() {
        return repository.findAll();
    }

    public DayOfWeek saveDay(DayOfWeek dayOfWeek) {
        return repository.saveAndFlush(dayOfWeek);
    }
}
