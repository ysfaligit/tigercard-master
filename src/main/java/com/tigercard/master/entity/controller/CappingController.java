package com.tigercard.master.entity.controller;

import com.tigercard.master.entity.Capping;
import com.tigercard.master.entity.service.CappingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Calendar;
import java.util.List;

@RestController
@RequestMapping("/capping")
public class CappingController {

    @Autowired
    private CappingService cappingService;

    @GetMapping("/{zoneFrom}/{zoneTo}")
    public Capping getCappingForZones(@PathVariable("zoneFrom") String zoneFrom, @PathVariable("zoneTo") String zoneTo){
        return cappingService.getCappingByZoneFromAndZoneTo(zoneFrom,zoneTo);
    }

    @GetMapping("/")
    public List<Capping> getAllCappings() {
        return cappingService.getCappings();
    }

    @PostMapping("/")
    public Capping save(@RequestBody Capping capping) {
        return cappingService.save(capping);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") long id) {
        cappingService.delete(id);
    }
}
