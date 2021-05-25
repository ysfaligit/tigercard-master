package com.tigercard.master.service;

import com.tigercard.master.entity.TigerCard;
import com.tigercard.master.repository.TigerCardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
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

    @Cacheable(
            value = "fifteen-min-cache",
            key = "'cardCache-' + #cardId")
    public TigerCard getCardDetails(long cardId) {
        return repository.getCardByCardId(cardId);
    }

    public void delete(long id) {
        repository.deleteByCardId(id);
    }
}
