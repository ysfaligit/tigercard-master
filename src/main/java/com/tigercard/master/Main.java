package com.tigercard.master;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class Main {
    static ObjectMapper objectMapper = new ObjectMapper();
    public static void main(String[] args) throws IOException {
//        RuleCondition rideRuleCondition = objectMapper.readValue(new File("testData" + File.separator + "rideRuleCondition.json"), RuleCondition.class);
//        System.out.println(rideRuleCondition);

        System.out.println("hi");
    }
}
