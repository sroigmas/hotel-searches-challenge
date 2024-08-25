package com.travel.hotelsearcheschallenge.domain;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public record HotelSearch(
    UUID searchId, UUID hotelId, LocalDate checkIn, LocalDate checkOut, List<Integer> ages) {}
