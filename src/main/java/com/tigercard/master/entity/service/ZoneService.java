package com.tigercard.master.entity.service;

import com.tigercard.master.entity.Zone;
import com.tigercard.master.entity.repository.ZoneRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ZoneService {
    @Autowired
    private ZoneRepository zoneRepository;

    public Zone getZoneById(String id) {
        return zoneRepository.getZoneByZoneId(id);
    }

    public List<Zone> getAllZones() {
        return zoneRepository.findAll();
    }

    public Zone save(Zone zone) {
        return zoneRepository.save(zone);
    }

    public void delete(String id) {
        zoneRepository.deleteById(id);
    }
}
