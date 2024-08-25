package com.travel.hotelsearcheschallenge.application.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;

import com.travel.hotelsearcheschallenge.application.port.input.CreateSearchUseCase.CreateSearchCommand;
import com.travel.hotelsearcheschallenge.application.port.output.SearchEventRepository;
import com.travel.hotelsearcheschallenge.domain.HotelSearch;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CreateSearchServiceTest {

  @Mock private SearchEventRepository searchEventRepository;
  @InjectMocks private CreateSearchService createSearchService;

  @Test
  void givenHotelSearch_whenCreatingSearch_thenHotelSearchEventIsSent() {
    UUID hotelId = UUID.randomUUID();
    LocalDate checkIn = LocalDate.of(2024, 6, 15);
    LocalDate checkOut = LocalDate.of(2024, 6, 20);
    List<Integer> ages = List.of(30, 32);

    CreateSearchCommand createSearchCommand =
        new CreateSearchCommand(hotelId, checkIn, checkOut, ages);

    HotelSearch hotelSearch = createSearchService.createSearch(createSearchCommand);

    ArgumentCaptor<HotelSearch> captor = ArgumentCaptor.forClass(HotelSearch.class);
    verify(searchEventRepository).sendSearchEvent(captor.capture());
    assertEquals(hotelId, captor.getValue().hotelId());
    assertEquals(checkIn, captor.getValue().checkIn());
    assertEquals(checkOut, captor.getValue().checkOut());
    assertEquals(ages, captor.getValue().ages());

    assertNotNull(hotelSearch.searchId());
    assertEquals(hotelId, hotelSearch.hotelId());
    assertEquals(checkIn, hotelSearch.checkIn());
    assertEquals(checkOut, hotelSearch.checkOut());
    assertEquals(ages, hotelSearch.ages());
  }
}
