package com.travel.hotelsearcheschallenge.infrastructure.mongo;

import java.time.LocalDate;
import java.util.List;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("searches")
@CompoundIndex(
    name = "hotelid_checkin_checkout_idx",
    def = "{'hotelId': 1, 'checkIn': -1, 'checkOut': -1}")
public class SearchDocument {

  @Id private String searchId;

  private String hotelId;

  private LocalDate checkIn;

  private LocalDate checkOut;

  private List<Integer> ages;

  public SearchDocument() {}

  public SearchDocument(
      String searchId, String hotelId, LocalDate checkIn, LocalDate checkOut, List<Integer> ages) {
    this.searchId = searchId;
    this.hotelId = hotelId;
    this.checkIn = checkIn;
    this.checkOut = checkOut;
    this.ages = ages;
  }

  public String getSearchId() {
    return searchId;
  }

  public void setSearchId(String searchId) {
    this.searchId = searchId;
  }

  public String getHotelId() {
    return hotelId;
  }

  public void setHotelId(String hotelId) {
    this.hotelId = hotelId;
  }

  public LocalDate getCheckIn() {
    return checkIn;
  }

  public void setCheckIn(LocalDate checkIn) {
    this.checkIn = checkIn;
  }

  public LocalDate getCheckOut() {
    return checkOut;
  }

  public void setCheckOut(LocalDate checkOut) {
    this.checkOut = checkOut;
  }

  public List<Integer> getAges() {
    return ages;
  }

  public void setAges(List<Integer> ages) {
    this.ages = ages;
  }
}
