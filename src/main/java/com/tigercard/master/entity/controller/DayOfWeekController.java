package com.tigercard.master.entity.controller;

import com.tigercard.master.entity.DayOfWeek;
import com.tigercard.master.entity.service.DayOfWeekService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/dow") 
public class DayOfWeekController {
    @Autowired
    private DayOfWeekService dayOfWeekService;


    @PutMapping("/")
    public DayOfWeek saveDay(@RequestBody DayOfWeek dayOfWeek) {
        return dayOfWeekService.saveDay(dayOfWeek);
    }

    @GetMapping("/")
    public List<DayOfWeek> getDaysOfWeek() {
        return dayOfWeekService.getDaysOfWeek();
    }
}
