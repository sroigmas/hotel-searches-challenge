package com.travel.hotelsearcheschallenge;

import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.containers.Network;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.utility.DockerImageName;

public interface TestcontainersConfiguration {

  Network network = Network.newNetwork();

  @Container
  MongoDBContainer mongoDbContainer =
      new MongoDBContainer(DockerImageName.parse("mongo:latest"));

  @Container
  KafkaContainer kafkaContainer =
      new KafkaContainer(DockerImageName.parse("confluentinc/cp-kafka:latest"))
          .withNetwork(network);

  @Container
  GenericContainer<?> schemaRegistryContainer =
      new GenericContainer<>(DockerImageName.parse("confluentinc/cp-schema-registry:latest"))
          .dependsOn(kafkaContainer)
          .withNetwork(network)
          .withExposedPorts(8081)
          .withEnv("SCHEMA_REGISTRY_HOST_NAME", "schema-registry")
          .withEnv("SCHEMA_REGISTRY_LISTENERS", "http://0.0.0.0:8081")
          .withEnv(
              "SCHEMA_REGISTRY_KAFKASTORE_BOOTSTRAP_SERVERS",
              "PLAINTEXT://" + kafkaContainer.getNetworkAliases().getFirst() + ":9092");

  @DynamicPropertySource
  static void setProperties(DynamicPropertyRegistry properties) {
    properties.add("spring.data.mongodb.uri", mongoDbContainer::getReplicaSetUrl);
    properties.add("spring.kafka.bootstrap-servers", kafkaContainer::getBootstrapServers);
    properties.add(
        "spring.kafka.properties.schema.registry.url",
        () ->
            "http://"
                + schemaRegistryContainer.getHost()
                + ":"
                + schemaRegistryContainer.getFirstMappedPort());
  }
}
