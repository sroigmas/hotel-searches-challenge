package com.travel.hotelsearcheschallenge.infrastructure.configuration;

import com.travel.hotelsearcheschallenge.application.port.output.SearchEventRepository;
import com.travel.hotelsearcheschallenge.application.port.output.SearchRepository;
import com.travel.hotelsearcheschallenge.application.service.CreateSearchService;
import com.travel.hotelsearcheschallenge.application.service.FindSearchCountService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SearchConfiguration {

  @Bean
  public CreateSearchService createSearchService(SearchEventRepository searchEventRepository) {
    return new CreateSearchService(searchEventRepository);
  }

  @Bean
  public FindSearchCountService findSearchCountService(SearchRepository searchRepository) {
    return new FindSearchCountService(searchRepository);
  }
}
