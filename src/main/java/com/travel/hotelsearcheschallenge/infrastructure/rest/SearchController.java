package com.travel.hotelsearcheschallenge.infrastructure.rest;

import com.travel.hotelsearcheschallenge.application.port.input.CreateSearchUseCase;
import com.travel.hotelsearcheschallenge.application.port.input.CreateSearchUseCase.CreateSearchCommand;
import com.travel.hotelsearcheschallenge.application.port.input.FindSearchCountUseCase;
import com.travel.hotelsearcheschallenge.application.port.input.FindSearchCountUseCase.FindSearchCountQuery;
import com.travel.hotelsearcheschallenge.domain.HotelSearch;
import com.travel.hotelsearcheschallenge.domain.HotelSearchCount;
import jakarta.validation.Valid;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SearchController {

  private final CreateSearchUseCase createSearchUseCase;
  private final FindSearchCountUseCase findSearchCountUseCase;

  public SearchController(
      CreateSearchUseCase createSearchUseCase, FindSearchCountUseCase findSearchCountUseCase) {
    this.createSearchUseCase = createSearchUseCase;
    this.findSearchCountUseCase = findSearchCountUseCase;
  }

  @PostMapping("/search")
  @ResponseStatus(HttpStatus.CREATED)
  public SearchResponse createSearch(@Valid @RequestBody SearchRequest searchRequest) {
    CreateSearchCommand createSearchCommand =
        new CreateSearchCommand(
            searchRequest.hotelId(),
            searchRequest.checkIn(),
            searchRequest.checkOut(),
            searchRequest.ages());

    HotelSearch hotelSearch = createSearchUseCase.createSearch(createSearchCommand);

    return new SearchResponse(hotelSearch.searchId());
  }

  @GetMapping("/count")
  public CountResponse getSearchCountById(@RequestParam UUID searchId) {
    FindSearchCountQuery findSearchCountQuery = new FindSearchCountQuery(searchId);

    HotelSearchCount hotelSearchCount =
        findSearchCountUseCase.findSearchCountById(findSearchCountQuery);

    return new CountResponse(
        hotelSearchCount.searchId(),
        new CountSearchResponse(
            hotelSearchCount.hotelId(),
            hotelSearchCount.checkIn(),
            hotelSearchCount.checkOut(),
            hotelSearchCount.ages()),
        hotelSearchCount.count());
  }
}
