package com.travel.hotelsearcheschallenge.application.service;

import com.travel.hotelsearcheschallenge.application.port.input.CreateSearchUseCase;
import com.travel.hotelsearcheschallenge.application.port.output.SearchEventRepository;
import com.travel.hotelsearcheschallenge.domain.HotelSearch;
import java.util.UUID;

public class CreateSearchService implements CreateSearchUseCase {

  private final SearchEventRepository searchEventRepository;

  public CreateSearchService(SearchEventRepository searchEventRepository) {
    this.searchEventRepository = searchEventRepository;
  }

  @Override
  public HotelSearch createSearch(CreateSearchCommand createSearchCommand) {
    HotelSearch hotelSearch =
        new HotelSearch(
            UUID.randomUUID(),
            createSearchCommand.hotelId(),
            createSearchCommand.checkIn(),
            createSearchCommand.checkOut(),
            createSearchCommand.ages());

    searchEventRepository.sendSearchEvent(hotelSearch);

    return hotelSearch;
  }
}
