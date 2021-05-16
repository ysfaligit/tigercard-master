package com.tigercard.master.entity.service;

import com.tigercard.master.entity.Rate;
import com.tigercard.master.entity.RatePK;
import com.tigercard.master.entity.Zone;
import com.tigercard.master.entity.repository.RatesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RateService {
    @Autowired
    private RatesRepository repository;

    public List<Rate> getRates() {
        return repository.findAll();
    }

    public Rate save(Rate rate) {
        return repository.save(rate);
    }


    public Rate getRateByZones(@NonNull Zone zoneFrom, @NonNull Zone zoneTo) {
        return repository.findByIdZoneFromAndIdZoneTo(zoneFrom, zoneTo);
    }

    @Cacheable(
            value = "fifteen-min-cache",
            key = "'rate-' + #zoneFrom + '-' + #zoneTo")
    public Rate getRateByZones(@NonNull long zoneFrom, @NonNull long zoneTo) {
        return getRateByZones(new Zone(zoneFrom), new Zone(zoneTo));
    }

    public void delete(long id) {
        repository.deleteByIdRateId(id);
    }
}
