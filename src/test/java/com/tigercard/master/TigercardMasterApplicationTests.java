package com.tigercard.master;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tigercard.master.dto.TripResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
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
class TigercardMasterApplicationTests {

    @Autowired
    MockMvc mockMvc;

    ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void saveTrips() throws Exception {

        List l = objectMapper.readValue(new File("testData" + File.separator + "trips.json"), List.class);


        MvcResult r = mockMvc.perform(
                MockMvcRequestBuilders
                        .post("/trip/saveAll")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(l))

        ).andReturn();

        assertEquals("Trips saved successfully", r.getResponse().getContentAsString());
    }

    @Test
    void getTripsByCardTest() throws Exception {
        MvcResult results = mockMvc.perform(
                MockMvcRequestBuilders.get("/trip/report/{cardId}", "1000")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn();

        TripResponseDto tripResponse = objectMapper.readValue(results.getResponse().getContentAsString(), TripResponseDto.class);

        assertEquals(720, tripResponse.getTotalTrip());


    }

}
