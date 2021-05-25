package com.tigercard.master.entity.controller;

import com.tigercard.master.entity.TigerCard;
import com.tigercard.master.entity.service.TigerCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/card")
public class TigerCardController {
    @Autowired
    private TigerCardService tigerCardService;

    @PutMapping("/")
    public TigerCard register(@RequestBody TigerCard card){
        return tigerCardService.register(card);
    }

    @GetMapping("/")
    public List<TigerCard> getAllCardDetails() {
        return tigerCardService.getAllCardDetails();
    }

    @GetMapping("/{id}")
    public TigerCard getCardDetails(@PathVariable("id") long id) {
        return tigerCardService.getCardDetails(id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") long id) {
        tigerCardService.delete(id);
    }
}
