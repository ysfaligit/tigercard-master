package com.tigercard.master.controller;

import com.tigercard.master.entity.Capping;
import com.tigercard.master.service.CappingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/capping")
public class CappingController {

    @Autowired
    private CappingService cappingService;

    @GetMapping("/{zoneFrom}/{zoneTo}")
    public Capping getCappingForZones(@PathVariable("zoneFrom") Long zoneFrom, @PathVariable("zoneTo") Long zoneTo){
        return cappingService.getCappingByZoneFromAndZoneTo(zoneFrom,zoneTo);
    }

    @GetMapping("/")
    public List<Capping> getAllCappings() {
        return cappingService.getCappings();
    }

    @PutMapping("/")
    public Capping save(@RequestBody Capping capping) {
        return cappingService.save(capping);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") long id) {
        cappingService.delete(id);
    }
}
