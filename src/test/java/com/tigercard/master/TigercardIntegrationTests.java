package com.tigercard.master;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tigercard.master.dto.TripResponseDto;
import com.tigercard.master.entity.Capping;
import com.tigercard.master.entity.Rate;
import com.tigercard.master.entity.RideRule;
import com.tigercard.master.entity.Zone;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@Slf4j
@EnableConfigurationProperties
@TestPropertySource(locations = "classpath:application-test.properties")
class TigercardIntegrationTests {

    @Autowired
    MockMvc mockMvc;

    ObjectMapper objectMapper = new ObjectMapper();

    @Value("${daily.cap.test.card}")
    long dailyCapCardId;

    @Value("${daily.cap.test.expected.trip.total}")
    long dailyCapExpectedTripTotal;

    @Value("${weekly.cap.test.card}")
    long weeklyCapCardId;

    @Value("${weekly.cap.test.expected.trip.total}")
    long weeklyCapExpectedTripTotal;

    @Test
    void testDailyCapReachedByCard() throws Exception {

        List l = objectMapper.readValue(new File("testData" + File.separator + "dailyTrips.json"), List.class);
        log.info("DailyCapTest : loaded trips from json");

        MvcResult r = mockMvc.perform(
                MockMvcRequestBuilders
                        .put("/trip/saveAll")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(l))

        ).andReturn();
        assertEquals("Trips saved successfully", r.getResponse().getContentAsString());
        log.info("DailyCapTest : Trips saved successfully");

        MvcResult results = mockMvc.perform(
                MockMvcRequestBuilders.get("/trip/{cardId}", String.valueOf(dailyCapCardId))
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn();

        log.info("DailyCapTest : Retrieved Total Trips by " + dailyCapCardId);

        TripResponseDto tripResponse = objectMapper.readValue(results.getResponse().getContentAsString(), TripResponseDto.class);

        assertEquals(dailyCapExpectedTripTotal, tripResponse.getTripsTotal());
        log.info("DailyCapTest : trips total : " + tripResponse.getTripsTotal());
    }

    @Test
    void testWeeklyCapReachedByCard() throws Exception {

        List l = objectMapper.readValue(new File("testData" + File.separator + "weeklyTrips.json"), List.class);
        log.info("WeeklyCapTest : loaded trips from json");

        MvcResult r = mockMvc.perform(
                MockMvcRequestBuilders
                        .put("/trip/saveAll")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(l))

        ).andReturn();

        assertEquals("Trips saved successfully", r.getResponse().getContentAsString());
        log.info("WeeklyCapTest : Trips saved successfully");

        MvcResult results = mockMvc.perform(
                MockMvcRequestBuilders.get("/trip/{cardId}", String.valueOf(weeklyCapCardId))
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn();
        log.info("WeeklyCapTest : Retrieved Total Trips by " + dailyCapCardId);

        TripResponseDto tripResponse = objectMapper.readValue(results.getResponse().getContentAsString(), TripResponseDto.class);

        assertEquals(weeklyCapExpectedTripTotal, tripResponse.getTripsTotal());
        log.info("WeeklyCapTest : trips total : " + tripResponse.getTripsTotal());
    }

    @Test
    void testZoneMethods() throws Exception {
        Zone zone = objectMapper.readValue(new File("testData" + File.separator + "zone.json"), Zone.class);
        MvcResult r = mockMvc.perform(
                MockMvcRequestBuilders
                        .put("/zone/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(zone))

        ).andReturn();

        assertNotNull(r.getResponse().getContentAsString());
        Zone zoneResult = objectMapper.readValue(r.getResponse().getContentAsString(), Zone.class);

        assertNotNull(zoneResult);
        assertNotNull(zoneResult.getZoneId());
        assertEquals(zone.getName(), zoneResult.getName());

        log.info(zoneResult.getName() + " added successfully. Test case passed.");

        log.info("Testing GET zone/ call");

        MvcResult results = mockMvc.perform(
                MockMvcRequestBuilders.get("/zone/")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn();

        ArrayList zones = objectMapper.readValue(results.getResponse().getContentAsString(), ArrayList.class);
        assertNotNull(zones);
        assertEquals(3, zones.size());

        log.info("GET zone/ Test cases passed");
//        Capping capping = objectMapper.readValue(new File("testData" + File.separator + "capping.json"), Capping.class);

    }

    @Test
    void testCappingMethods() throws Exception {
        Zone zone = objectMapper.readValue(new File("testData" + File.separator + "zone.json"), Zone.class);
        MvcResult saveZoneResult = mockMvc.perform(
                MockMvcRequestBuilders
                        .put("/zone/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(zone))

        ).andReturn();

        assertNotNull(saveZoneResult.getResponse().getContentAsString());
        Zone zoneResult = objectMapper.readValue(saveZoneResult.getResponse().getContentAsString(), Zone.class);

        assertNotNull(zoneResult);
        assertNotNull(zoneResult.getZoneId());
        assertEquals(zone.getName(), zoneResult.getName());

        log.info("testing capping put mapping");
        Capping capping = objectMapper.readValue(new File("testData" + File.separator + "capping.json"), Capping.class);
        MvcResult saveCappingResult = mockMvc.perform(
                MockMvcRequestBuilders
                        .put("/capping/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(capping))

        ).andReturn();

        assertNotNull(saveCappingResult.getResponse().getContentAsString());
        Capping cappingResult = objectMapper.readValue(saveCappingResult.getResponse().getContentAsString(), Capping.class);

        assertNotNull(cappingResult);
        assertNotNull(cappingResult.getCappingId());
        assertEquals(capping.getDailyCap(), cappingResult.getDailyCap());
        assertEquals(capping.getWeeklyCap(), cappingResult.getWeeklyCap());
        assertEquals(capping.getZoneFrom().getZoneId(), cappingResult.getZoneFrom().getZoneId());
        assertEquals(capping.getZoneTo().getZoneId(), cappingResult.getZoneTo().getZoneId());
        log.info("PASSED :  capping put mapping");

        log.info("Testing GET capping/ call");

        MvcResult results = mockMvc.perform(
                MockMvcRequestBuilders.get("/capping/")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn();

        ArrayList cappings = objectMapper.readValue(results.getResponse().getContentAsString(), ArrayList.class);
        assertNotNull(cappings);
        assertEquals(5, cappings.size());

        log.info("GET capping/ Test cases passed");

    }

    @Test
    void testRideRuleMethods() throws Exception {
        RideRule rideRule = objectMapper.readValue(new File("testData" + File.separator + "rideRule.json"), RideRule.class);

        MvcResult savedRideRuleResult = mockMvc.perform(
                MockMvcRequestBuilders
                        .put("/riderules/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(rideRule))

        ).andReturn();

        assertNotNull(savedRideRuleResult.getResponse().getContentAsString());
        RideRule savedRideRule = objectMapper.readValue(savedRideRuleResult.getResponse().getContentAsString(), RideRule.class);

        assertNotNull(savedRideRule);
        assertNotNull(savedRideRule.getRuleId());
        assertEquals(rideRule.getFromTime(), savedRideRule.getFromTime());
        assertEquals(rideRule.getToTime(), savedRideRule.getToTime());
        assertEquals(rideRule.getZoneFrom(), savedRideRule.getZoneFrom());
        assertEquals(rideRule.getZoneTo(), savedRideRule.getZoneTo());
        log.info("PASSED :  rideRule put mapping");

        log.info("Testing GET capping/ call");

        MvcResult results = mockMvc.perform(
                MockMvcRequestBuilders.get("/riderules/")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn();

        ArrayList rideRules = objectMapper.readValue(results.getResponse().getContentAsString(), ArrayList.class);
        assertNotNull(rideRules);
        assertEquals(7, rideRules.size());

        log.info("GET capping/ PASSED");
    }

    @Test
    void testRateMethods() throws Exception {
        Zone zone = objectMapper.readValue(new File("testData" + File.separator + "zone.json"), Zone.class);
        MvcResult saveZoneResult = mockMvc.perform(
                MockMvcRequestBuilders
                        .put("/zone/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(zone))

        ).andReturn();

        assertNotNull(saveZoneResult.getResponse().getContentAsString());
        Zone zoneResult = objectMapper.readValue(saveZoneResult.getResponse().getContentAsString(), Zone.class);

        assertNotNull(zoneResult);
        assertNotNull(zoneResult.getZoneId());
        assertEquals(zone.getName(), zoneResult.getName());

        log.info("testing rate put mapping");
        Rate rateUnsaved = objectMapper.readValue(new File("testData" + File.separator + "rate.json"), Rate.class);
        MvcResult savedMvcRateResult = mockMvc.perform(
                MockMvcRequestBuilders
                        .put("/rate/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(rateUnsaved))

        ).andReturn();

        assertNotNull(savedMvcRateResult.getResponse().getContentAsString());
        Rate savedRate = objectMapper.readValue(savedMvcRateResult.getResponse().getContentAsString(), Rate.class);

        assertNotNull(savedRate);
        assertNotNull(savedRate.getId());
        assertNotNull(savedRate.getId().getRateId());
        assertEquals(rateUnsaved.getId().getRateId(), savedRate.getId().getRateId());

        log.info("PASSED :  rate put/ mapping");

        log.info("Testing GET rate get/ call");

        MvcResult results = mockMvc.perform(
                MockMvcRequestBuilders.get("/rate/")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn();

        ArrayList rates = objectMapper.readValue(results.getResponse().getContentAsString(), ArrayList.class);
        assertNotNull(rates);
        assertEquals(5, rates.size());

        log.info(" PASSED GET MAPPING rate/");

    }

}
