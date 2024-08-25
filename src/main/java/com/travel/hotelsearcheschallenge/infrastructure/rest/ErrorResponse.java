package com.travel.hotelsearcheschallenge.infrastructure.rest;

import org.springframework.http.HttpStatus;

public record ErrorResponse(HttpStatus status, String message) {}
