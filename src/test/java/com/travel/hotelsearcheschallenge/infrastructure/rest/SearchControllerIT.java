package com.travel.hotelsearcheschallenge.infrastructure.rest;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.travel.hotelsearcheschallenge.TestcontainersConfiguration;
import com.travel.hotelsearcheschallenge.infrastructure.mongo.SearchDocument;
import com.travel.hotelsearcheschallenge.infrastructure.mongo.SearchMongoRepository;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.context.ImportTestcontainers;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest
@Testcontainers
@ImportTestcontainers(TestcontainersConfiguration.class)
@AutoConfigureMockMvc
class SearchControllerIT {

  @Autowired private MockMvc mvc;

  private final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

  @Autowired private SearchMongoRepository searchMongoRepository;

  @Test
  void givenHotelSearch_whenCreatingSearch_thenHotelSearchIsCreated() throws Exception {
    SearchRequest searchRequest =
        new SearchRequest(
            UUID.randomUUID(),
            LocalDate.of(2025, 6, 15),
            LocalDate.of(2025, 6, 20),
            List.of(30, 32));

    ResultActions result =
        mvc.perform(
                post("/search")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(searchRequest)))
            .andDo(print());

    result.andExpect(status().isCreated()).andExpect(jsonPath("$.searchId").exists());

    Thread.sleep(2000);

    SearchResponse searchResponse =
        objectMapper.readValue(
            result.andReturn().getResponse().getContentAsString(), SearchResponse.class);
    Optional<SearchDocument> searchDocumentOpt =
        searchMongoRepository.findById(searchResponse.searchId().toString());

    assertTrue(searchDocumentOpt.isPresent());
    assertEquals(searchResponse.searchId().toString(), searchDocumentOpt.get().getSearchId());
    assertEquals(searchRequest.hotelId().toString(), searchDocumentOpt.get().getHotelId());
    assertEquals(searchRequest.checkIn(), searchDocumentOpt.get().getCheckIn());
    assertEquals(searchRequest.checkOut(), searchDocumentOpt.get().getCheckOut());
    assertEquals(searchRequest.ages(), searchDocumentOpt.get().getAges());
  }

  @Test
  void givenSearchId_whenFindingSearchById_thenReturnsHotelSearchCount() throws Exception {
    SearchRequest searchRequest =
        new SearchRequest(
            UUID.randomUUID(),
            LocalDate.of(2025, 6, 15),
            LocalDate.of(2025, 6, 20),
            List.of(30, 32));

    ResultActions searchResult =
        mvc.perform(
                post("/search")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(searchRequest)))
            .andDo(print());

    searchResult.andExpect(status().isCreated()).andExpect(jsonPath("$.searchId").exists());

    Thread.sleep(2000);

    SearchResponse searchResponse =
        objectMapper.readValue(
            searchResult.andReturn().getResponse().getContentAsString(), SearchResponse.class);

    ResultActions countResult =
        mvc.perform(
                get("/count")
                    .param("searchId", searchResponse.searchId().toString())
                    .contentType(MediaType.APPLICATION_JSON))
            .andDo(print());

    countResult
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.searchId").value(searchResponse.searchId().toString()))
        .andExpect(jsonPath("$.search.hotelId").value(searchRequest.hotelId().toString()))
        .andExpect(
            jsonPath("$.search.checkIn")
                .value(searchRequest.checkIn().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))))
        .andExpect(
            jsonPath("$.search.checkOut")
                .value(searchRequest.checkOut().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))))
        .andExpect(jsonPath("$.search.ages", hasSize(searchRequest.ages().size())))
        .andExpect(jsonPath("$.search.ages", hasItem(searchRequest.ages().get(0))))
        .andExpect(jsonPath("$.search.ages", hasItem(searchRequest.ages().get(1))))
        .andExpect(jsonPath("$.count").value(1));
  }

  @Test
  void givenMissingSearchId_whenFindingSearchById_thenHotelSearchNotFound() throws Exception {
    ResultActions result =
        mvc.perform(
                get("/count")
                    .param("searchId", UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON))
            .andDo(print());

    result.andExpect(status().isNotFound());
  }
}
