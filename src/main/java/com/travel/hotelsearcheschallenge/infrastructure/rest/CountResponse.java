package com.travel.hotelsearcheschallenge.infrastructure.rest;

import java.util.UUID;

public record CountResponse(UUID searchId, CountSearchResponse search, Long count) {}
