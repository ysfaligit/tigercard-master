package com.tigercard.master.entity.controller;


import com.tigercard.master.entity.Rate;
import com.tigercard.master.entity.service.RateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rate")
public class RateController {
    @Autowired
    private RateService rateService;

    @GetMapping(name = "/")
    public List<Rate> getRates(){
        return rateService.getRates();
    }

    @PutMapping("/")
    public Rate save(@RequestBody Rate rate){
        return rateService.save(rate);
    }

    @GetMapping("/{zoneFrom}/{zoneTo}")
    public Rate getRateByZones(@PathVariable("zoneFrom") Long zoneFrom, @PathVariable("zoneTo") Long zoneTo) {
        return rateService.getRateByZones(zoneFrom,zoneTo);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") long id) {
        rateService.delete(id);
    }
}
