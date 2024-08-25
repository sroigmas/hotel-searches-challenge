package com.travel.hotelsearcheschallenge.application.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import com.travel.hotelsearcheschallenge.application.exception.ApplicationNotFoundException;
import com.travel.hotelsearcheschallenge.application.port.input.FindSearchCountUseCase.FindSearchCountQuery;
import com.travel.hotelsearcheschallenge.application.port.output.SearchRepository;
import com.travel.hotelsearcheschallenge.domain.HotelSearch;
import com.travel.hotelsearcheschallenge.domain.HotelSearchCount;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class FindSearchCountServiceTest {

  @Mock private SearchRepository searchRepository;
  @InjectMocks private FindSearchCountService findSearchCountService;

  @Test
  void givenSearchId_whenFindingSearchById_thenReturnsHotelSearchCount() {
    UUID searchId = UUID.randomUUID();
    FindSearchCountQuery findSearchCountQuery = new FindSearchCountQuery(searchId);

    HotelSearch hotelSearch =
        new HotelSearch(
            searchId,
            UUID.randomUUID(),
            LocalDate.of(2024, 6, 15),
            LocalDate.of(2024, 6, 20),
            List.of(30, 32));
    Long count = 2L;

    when(searchRepository.findSearchById(searchId)).thenReturn(Optional.of(hotelSearch));
    when(searchRepository.countSearches(hotelSearch)).thenReturn(count);

    HotelSearchCount hotelSearchCount =
        findSearchCountService.findSearchCountById(findSearchCountQuery);

    assertEquals(hotelSearch.searchId(), hotelSearchCount.searchId());
    assertEquals(hotelSearch.hotelId(), hotelSearchCount.hotelId());
    assertEquals(hotelSearch.checkIn(), hotelSearchCount.checkIn());
    assertEquals(hotelSearch.checkOut(), hotelSearchCount.checkOut());
    assertEquals(hotelSearch.ages(), hotelSearchCount.ages());
    assertEquals(count, hotelSearchCount.count());
  }

  @Test
  void givenMissingSearchId_whenFindingSearchById_thenHotelSearchNotFound() {
    UUID searchId = UUID.randomUUID();
    FindSearchCountQuery findSearchCountQuery = new FindSearchCountQuery(searchId);

    when(searchRepository.findSearchById(searchId)).thenReturn(Optional.empty());

    ApplicationNotFoundException exception =
        assertThrows(
            ApplicationNotFoundException.class,
            () -> findSearchCountService.findSearchCountById(findSearchCountQuery));

    assertEquals("Search with id=" + searchId + " could not be found.", exception.getMessage());
  }
}
