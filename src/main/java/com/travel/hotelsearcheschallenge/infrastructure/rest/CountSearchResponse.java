package com.travel.hotelsearcheschallenge.infrastructure.rest;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public record CountSearchResponse(
    UUID hotelId,
    @JsonFormat(pattern = "dd/MM/yyyy") LocalDate checkIn,
    @JsonFormat(pattern = "dd/MM/yyyy") LocalDate checkOut,
    List<Integer> ages) {}
