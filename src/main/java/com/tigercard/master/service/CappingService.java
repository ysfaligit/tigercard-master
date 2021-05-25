package com.tigercard.master.service;

import com.tigercard.master.entity.Capping;
import com.tigercard.master.entity.Zone;
import com.tigercard.master.repository.CappingRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class CappingService {
    @Autowired
    private CappingRepository cappingRepository;

    List<Capping> getAllCappings(){
        return cappingRepository.findAll();
    }


    @Cacheable(
            value = "fifteen-min-cache",
            key = "'capping-' + #zoneFrom + '-' + #zoneTo")
    public Capping getCappingByZoneFromAndZoneTo(Long zoneFrom, Long zoneTo) {
        return cappingRepository.findByZoneFromAndZoneTo(new Zone(zoneFrom), new Zone(zoneTo));
    }

    public Capping getCappingByZoneFromAndZoneTo(Zone zoneFrom, Zone zoneTo) {
        return cappingRepository.findByZoneFromAndZoneTo(zoneFrom, zoneTo);
    }

    public List<Capping> getCappings() {
        return cappingRepository.findAll();
    }

    public Capping save(Capping capping) {
        return cappingRepository.save(capping);
    }

    public void delete(long id) {
        cappingRepository.delete(new Capping(id));
    }
}
