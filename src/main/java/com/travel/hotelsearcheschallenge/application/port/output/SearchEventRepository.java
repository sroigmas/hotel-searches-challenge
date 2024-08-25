package com.travel.hotelsearcheschallenge.application.port.output;

import com.travel.hotelsearcheschallenge.domain.HotelSearch;

public interface SearchEventRepository {

  void sendSearchEvent(HotelSearch hotelSearch);
}
