package com.tigercard.master.controller;


import com.tigercard.master.entity.Rate;
import com.tigercard.master.service.RateService;
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

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") long id) {
        rateService.delete(id);
    }
}
