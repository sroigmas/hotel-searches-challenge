package com.travel.hotelsearcheschallenge.application.service;

import com.travel.hotelsearcheschallenge.application.exception.ApplicationNotFoundException;
import com.travel.hotelsearcheschallenge.application.port.input.FindSearchCountUseCase;
import com.travel.hotelsearcheschallenge.application.port.output.SearchRepository;
import com.travel.hotelsearcheschallenge.domain.HotelSearch;
import com.travel.hotelsearcheschallenge.domain.HotelSearchCount;

public class FindSearchCountService implements FindSearchCountUseCase {

  private final SearchRepository searchRepository;

  public FindSearchCountService(SearchRepository searchRepository) {
    this.searchRepository = searchRepository;
  }

  @Override
  public HotelSearchCount findSearchCountById(FindSearchCountQuery findSearchCountQuery) {
    HotelSearch hotelSearch =
        searchRepository
            .findSearchById(findSearchCountQuery.searchId())
            .orElseThrow(
                () ->
                    new ApplicationNotFoundException(
                        String.format(
                            "Search with id=%s could not be found.",
                            findSearchCountQuery.searchId())));

    Long count = searchRepository.countSearches(hotelSearch);

    return new HotelSearchCount(
        hotelSearch.searchId(),
        hotelSearch.hotelId(),
        hotelSearch.checkIn(),
        hotelSearch.checkOut(),
        hotelSearch.ages(),
        count);
  }
}
