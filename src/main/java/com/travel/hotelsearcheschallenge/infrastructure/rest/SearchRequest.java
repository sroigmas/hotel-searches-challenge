package com.travel.hotelsearcheschallenge.infrastructure.rest;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.travel.hotelsearcheschallenge.infrastructure.exception.InfrastructureValidationException;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public record SearchRequest(
    @NotNull UUID hotelId,
    @NotNull @JsonFormat(pattern = "dd/MM/yyyy") LocalDate checkIn,
    @NotNull @JsonFormat(pattern = "dd/MM/yyyy") LocalDate checkOut,
    @NotEmpty List<Integer> ages) {

  public SearchRequest {
    if (checkIn.isBefore(LocalDate.now())) {
      throw new InfrastructureValidationException("checkIn must be today or later.");
    }

    if (checkIn.isEqual(checkOut) || checkIn.isAfter(checkOut)) {
      throw new InfrastructureValidationException("checkIn must be before checkOut.");
    }

    if (ages.stream().anyMatch(age -> age < 0)) {
      throw new InfrastructureValidationException("ages must be greater than zero.");
    }
  }
}
