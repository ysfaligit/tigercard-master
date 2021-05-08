package com.tigercard.master.entity.service;

import com.tigercard.master.entity.Capping;
import com.tigercard.master.entity.Zone;
import com.tigercard.master.entity.repository.CappingRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class CappingService {
    @Autowired
    private CappingRepository cappingRepository;


    public Capping getCappingByZoneFromAndZoneTo(String zoneFrom, String zoneTo) {
        return cappingRepository.findByZoneFromAndZoneTo(new Zone(zoneFrom), new Zone(zoneTo));
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
