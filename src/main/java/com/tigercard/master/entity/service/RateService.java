package com.tigercard.master.entity.service;

import com.tigercard.master.entity.Rate;
import com.tigercard.master.entity.RatePK;
import com.tigercard.master.entity.Zone;
import com.tigercard.master.entity.repository.RatesRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

    public Rate getRateByZones(@NonNull String zoneFrom, @NonNull String zoneTo) {
        return repository.findByIdZoneFromAndIdZoneTo(new Zone(zoneFrom), new Zone(zoneTo));
    }

    public void delete(long id) {
        repository.deleteByIdRateId(id);
    }
}
