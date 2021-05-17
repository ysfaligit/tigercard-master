package com.tigercard.master;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tigercard.master.entity.Capping;
import com.tigercard.master.entity.RideRule;
import com.tigercard.master.entity.Zone;

import java.io.File;
import java.io.IOException;

public class Main {
    static ObjectMapper objectMapper = new ObjectMapper();
    public static void main(String[] args) throws IOException {
        RideRule rideRule = objectMapper.readValue(new File("testData" + File.separator + "rideRule.json"), RideRule.class);
        System.out.println(rideRule);
    }
}
