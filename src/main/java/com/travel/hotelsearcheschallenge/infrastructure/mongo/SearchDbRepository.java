package com.travel.hotelsearcheschallenge.infrastructure.mongo;

import com.travel.hotelsearcheschallenge.application.port.output.SearchRepository;
import com.travel.hotelsearcheschallenge.domain.HotelSearch;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Repository;

@Repository
public class SearchDbRepository implements SearchRepository {

  private final SearchMongoRepository searchMongoRepository;

  public SearchDbRepository(SearchMongoRepository searchMongoRepository) {
    this.searchMongoRepository = searchMongoRepository;
  }

  @Override
  public HotelSearch saveSearch(HotelSearch hotelSearch) {
    SearchDocument searchDocument =
        new SearchDocument(
            hotelSearch.searchId().toString(),
            hotelSearch.hotelId().toString(),
            hotelSearch.checkIn(),
            hotelSearch.checkOut(),
            hotelSearch.ages());

    SearchDocument savedSearchDocument = searchMongoRepository.save(searchDocument);

    return new HotelSearch(
        UUID.fromString(savedSearchDocument.getSearchId()),
        UUID.fromString(savedSearchDocument.getHotelId()),
        savedSearchDocument.getCheckIn(),
        savedSearchDocument.getCheckOut(),
        savedSearchDocument.getAges());
  }

  @Override
  public Optional<HotelSearch> findSearchById(UUID id) {
    Optional<SearchDocument> searchDocumentOpt = searchMongoRepository.findById(id.toString());

    return searchDocumentOpt.map(
        document ->
            new HotelSearch(
                UUID.fromString(document.getSearchId()),
                UUID.fromString(document.getHotelId()),
                document.getCheckIn(),
                document.getCheckOut(),
                document.getAges()));
  }

  @Override
  public Long countSearches(HotelSearch hotelSearch) {
    return searchMongoRepository.countSearches(
        hotelSearch.hotelId().toString(),
        hotelSearch.checkIn(),
        hotelSearch.checkOut(),
        hotelSearch.ages().size());
  }
}
