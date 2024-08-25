package com.travel.hotelsearcheschallenge.application.port.input;

import com.travel.hotelsearcheschallenge.domain.HotelSearch;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface CreateSearchUseCase {

  HotelSearch createSearch(CreateSearchCommand createSearchCommand);

  record CreateSearchCommand(
      UUID hotelId, LocalDate checkIn, LocalDate checkOut, List<Integer> ages) {}
}
