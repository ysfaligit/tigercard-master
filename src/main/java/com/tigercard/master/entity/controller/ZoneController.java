package com.tigercard.master.entity.controller;

import com.tigercard.master.entity.Zone;
import com.tigercard.master.entity.service.ZoneService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/zone")
@Slf4j
public class ZoneController {
    @Autowired
    private ZoneService zoneService;

    @GetMapping("/{id}")
    public Zone getZone(@PathVariable("id") Long id){
        return zoneService.getZoneById(id);
    }

    @GetMapping("/")
    public List<Zone> getAllZones(){
        return zoneService.getAllZones();
    }

    @PostMapping("/")
    public Zone save(@RequestBody Zone zone){
        return zoneService.save(zone);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        zoneService.delete(id);
    }
}
