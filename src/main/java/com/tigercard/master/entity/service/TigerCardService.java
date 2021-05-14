package com.tigercard.master.entity.service;

import com.tigercard.master.entity.TigerCard;
import com.tigercard.master.entity.repository.TigerCardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TigerCardService {
    @Autowired
    private TigerCardRepository repository;

    public TigerCard register(TigerCard card) {
        return repository.save(card);
    }

    public List<TigerCard> getAllCardDetails() {
        return repository.findAll();
    }

    public TigerCard getCardDetails(double id) {
        return repository.getOne(id);
    }

    public void delete(double id) {
        repository.deleteByCardId(id);
    }
}
