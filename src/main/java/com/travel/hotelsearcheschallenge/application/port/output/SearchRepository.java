package com.travel.hotelsearcheschallenge.application.port.output;

import com.travel.hotelsearcheschallenge.domain.HotelSearch;
import java.util.Optional;
import java.util.UUID;

public interface SearchRepository {

  HotelSearch saveSearch(HotelSearch hotelSearch);

  Optional<HotelSearch> findSearchById(UUID id);

  Long countSearches(HotelSearch hotelSearch);
}
