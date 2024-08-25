package com.travel.hotelsearcheschallenge.infrastructure.kafka;

import com.travel.hotelsearcheschallenge.application.port.output.SearchRepository;
import com.travel.hotelsearcheschallenge.domain.HotelSearch;
import java.util.UUID;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class SearchEventConsumer {

  private final SearchRepository searchRepository;

  public SearchEventConsumer(SearchRepository searchRepository) {
    this.searchRepository = searchRepository;
  }

  @KafkaListener(
      topics = "${spring.kafka.properties.topic.name}",
      groupId = "${spring.kafka.consumer.group-id}")
  public void consumeSearchEvent(ConsumerRecord<UUID, HotelSearchEvent> record) {
    HotelSearch hotelSearch =
        new HotelSearch(
            record.value().getSearchId(),
            record.value().getHotelId(),
            record.value().getCheckIn(),
            record.value().getCheckOut(),
            record.value().getAges());

    searchRepository.saveSearch(hotelSearch);
  }
}
