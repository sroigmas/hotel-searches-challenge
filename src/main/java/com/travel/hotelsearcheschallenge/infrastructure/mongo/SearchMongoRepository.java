package com.travel.hotelsearcheschallenge.infrastructure.mongo;

import java.time.LocalDate;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.ListCrudRepository;

public interface SearchMongoRepository extends ListCrudRepository<SearchDocument, String> {

  @Query(value = "{hotelId: ?0, checkIn: ?1, checkOut: ?2, ages: {$size: ?3}}", count = true)
  Long countSearches(String hotelId, LocalDate checkIn, LocalDate checkOut, int size);
}
