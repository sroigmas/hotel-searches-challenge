package com.travel.hotelsearcheschallenge.application.port.input;

import com.travel.hotelsearcheschallenge.domain.HotelSearchCount;
import java.util.UUID;

public interface FindSearchCountUseCase {

  HotelSearchCount findSearchCountById(FindSearchCountQuery findSearchCountQuery);

  record FindSearchCountQuery(UUID searchId) {}
}
