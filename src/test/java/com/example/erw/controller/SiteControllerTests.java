package com.example.erw.controller;

import com.example.erw.dto.SiteScoreRequest;
import com.example.erw.dto.SiteScoreResponse;
import com.example.erw.service.SiteScoringService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.util.Map;

@WebMvcTest(SiteController.class)
public class SiteControllerTests {

    @Autowired private MockMvc mockMvc;
    @Autowired private ObjectMapper objectMapper;
    @MockBean private SiteScoringService scoringService;

    @Test
    void scoreEndpointReturnsScore() throws Exception {
        SiteScoreRequest req = new SiteScoreRequest();
        req.setLatitude(12.34);
        req.setLongitude(56.78);
        req.setRainfallMm(1200.0);
        req.setSoilPh(7.2);
        req.setDistanceToRoadKm(10.0);
        req.setBasaltAvailabilityIndex(0.8);

        Mockito.when(scoringService.scoreSite(Mockito.any()))
               .thenReturn(new SiteScoreResponse(0.85, Map.of("soilPh", 0.9)));

        mockMvc.perform(post("/api/sitescore")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(req)))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.score").exists());
    }
}
