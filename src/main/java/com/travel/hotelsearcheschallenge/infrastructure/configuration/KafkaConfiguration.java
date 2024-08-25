package com.travel.hotelsearcheschallenge.infrastructure.configuration;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaConfiguration {

  @Value("${spring.kafka.properties.topic.name}")
  private String topicName;

  @Value("${spring.kafka.properties.topic.partitions}")
  private int partitions;

  @Value("${spring.kafka.properties.topic.replication}")
  private short replication;

  @Bean
  public NewTopic hotelSearchesTopic() {
    return new NewTopic(topicName, partitions, replication);
  }
}
