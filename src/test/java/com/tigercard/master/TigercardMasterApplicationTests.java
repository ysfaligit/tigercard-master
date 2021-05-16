package com.tigercard.master;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tigercard.master.dto.TripResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.io.File;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@Slf4j
@EnableConfigurationProperties
@TestPropertySource(locations = "classpath:application-test.properties")
class TigercardMasterApplicationTests {

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
                        .post("/trip/saveAll")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(l))

        ).andReturn();
        assertEquals("Trips saved successfully", r.getResponse().getContentAsString());
        log.info("DailyCapTest : Trips saved successfully");

        MvcResult results = mockMvc.perform(
                MockMvcRequestBuilders.get("/trip/report/{cardId}", String.valueOf(dailyCapCardId))
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn();

        log.info("DailyCapTest : Retrieved Total Trips by " + dailyCapCardId);

        TripResponseDto tripResponse = objectMapper.readValue(results.getResponse().getContentAsString(), TripResponseDto.class);

        assertEquals(dailyCapExpectedTripTotal, tripResponse.getTotalTrip());
        log.info("DailyCapTest : trips total : " + tripResponse.getTotalTrip());
    }

    @Test
    void testD_getWeeklyCapReachedByCard() throws Exception {

        List l = objectMapper.readValue(new File("testData" + File.separator + "weeklyTrips.json"), List.class);
        log.info("WeeklyCapTest : loaded trips from json");

        MvcResult r = mockMvc.perform(
                MockMvcRequestBuilders
                        .post("/trip/saveAll")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(l))

        ).andReturn();

        assertEquals("Trips saved successfully", r.getResponse().getContentAsString());
        log.info("WeeklyCapTest : Trips saved successfully");

        MvcResult results = mockMvc.perform(
                MockMvcRequestBuilders.get("/trip/report/{cardId}", String.valueOf(weeklyCapCardId))
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn();
        log.info("WeeklyCapTest : Retrieved Total Trips by " + dailyCapCardId);

        TripResponseDto tripResponse = objectMapper.readValue(results.getResponse().getContentAsString(), TripResponseDto.class);

        assertEquals(weeklyCapExpectedTripTotal, tripResponse.getTotalTrip());
        log.info("WeeklyCapTest : trips total : " + tripResponse.getTotalTrip());
    }

}
