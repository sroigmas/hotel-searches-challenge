package com.travel.hotelsearcheschallenge.infrastructure.kafka;

import com.travel.hotelsearcheschallenge.application.port.output.SearchEventRepository;
import com.travel.hotelsearcheschallenge.domain.HotelSearch;
import com.travel.hotelsearcheschallenge.infrastructure.exception.InfrastructureExternalException;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class SearchKafkaRepository implements SearchEventRepository {

  @Value("${spring.kafka.properties.topic.name}")
  private String topicName;

  private final KafkaTemplate<UUID, HotelSearchEvent> kafkaTemplate;

  public SearchKafkaRepository(KafkaTemplate<UUID, HotelSearchEvent> kafkaTemplate) {
    this.kafkaTemplate = kafkaTemplate;
  }

  @Override
  public void sendSearchEvent(HotelSearch hotelSearch) {
    HotelSearchEvent event =
        new HotelSearchEvent(
            hotelSearch.searchId(),
            hotelSearch.hotelId(),
            hotelSearch.checkIn(),
            hotelSearch.checkOut(),
            hotelSearch.ages());

    try {
      kafkaTemplate.send(topicName, hotelSearch.hotelId(), event);
    } catch (Exception e) {
      throw new InfrastructureExternalException("Error sending search event.");
    }
  }
}
