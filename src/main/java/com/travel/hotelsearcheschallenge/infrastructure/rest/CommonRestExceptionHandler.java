package com.travel.hotelsearcheschallenge.infrastructure.rest;

import com.travel.hotelsearcheschallenge.application.exception.ApplicationNotFoundException;
import com.travel.hotelsearcheschallenge.infrastructure.exception.InfrastructureExternalException;
import com.travel.hotelsearcheschallenge.infrastructure.exception.InfrastructureValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestControllerAdvice
public class CommonRestExceptionHandler {

  private final Logger logger = LoggerFactory.getLogger(CommonRestExceptionHandler.class);

  @ExceptionHandler(MethodArgumentTypeMismatchException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ErrorResponse handleMethodArgumentTypeMismatchExceptions(
      MethodArgumentTypeMismatchException e) {
    String message = e.getName() + " should be of type " + e.getRequiredType().getName();
    logger.error(message);
    return new ErrorResponse(HttpStatus.BAD_REQUEST, message);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ErrorResponse handleMethodArgumentNotValidExceptions(MethodArgumentNotValidException e) {
    logger.error(e.getMessage());
    return new ErrorResponse(HttpStatus.BAD_REQUEST, e.getMessage());
  }

  @ExceptionHandler(HttpMessageNotReadableException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ErrorResponse handleHttpMessageNotReadableExceptions(HttpMessageNotReadableException e) {
    logger.error(e.getMessage());
    return new ErrorResponse(HttpStatus.BAD_REQUEST, e.getMessage());
  }

  @ExceptionHandler(InfrastructureValidationException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ErrorResponse handleInfrastructureValidationExceptions(
      InfrastructureValidationException e) {
    logger.error(e.getMessage());
    return new ErrorResponse(HttpStatus.BAD_REQUEST, e.getMessage());
  }

  @ExceptionHandler(ApplicationNotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public ErrorResponse handleApplicationNotFoundExceptions(ApplicationNotFoundException e) {
    logger.error(e.getMessage());
    return new ErrorResponse(HttpStatus.NOT_FOUND, e.getMessage());
  }

  @ExceptionHandler(InfrastructureExternalException.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public ErrorResponse handleInfrastructureExternalExceptions(InfrastructureExternalException e) {
    logger.error(e.getMessage());
    return new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
  }

  @ExceptionHandler(Exception.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public ErrorResponse handleUnexpectedExceptions(Exception e) {
    logger.error(e.getMessage(), e);
    return new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
  }
}
