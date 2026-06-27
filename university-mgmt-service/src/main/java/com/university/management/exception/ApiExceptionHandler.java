package com.university.management.exception;

import com.university.management.common.model.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import java.time.OffsetDateTime;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApiExceptionHandler {

  @ExceptionHandler(ResourceNotFoundException.class)
  ResponseEntity<ErrorResponse> handleNotFound(
      ResourceNotFoundException exception, HttpServletRequest request) {
    return build(HttpStatus.NOT_FOUND, exception.getMessage(), request);
  }

  @ExceptionHandler(ResourceConflictException.class)
  ResponseEntity<ErrorResponse> handleConflict(
      ResourceConflictException exception, HttpServletRequest request) {
    return build(HttpStatus.CONFLICT, exception.getMessage(), request);
  }

  @ExceptionHandler({
    ConstraintViolationException.class,
    HttpMessageNotReadableException.class,
    MethodArgumentNotValidException.class
  })
  ResponseEntity<ErrorResponse> handleBadRequest(Exception exception, HttpServletRequest request) {
    return build(HttpStatus.BAD_REQUEST, exception.getMessage(), request);
  }

  private ResponseEntity<ErrorResponse> build(
      HttpStatus status, String message, HttpServletRequest request) {
    ErrorResponse body =
        new ErrorResponse()
            .timestamp(OffsetDateTime.now())
            .status(status.value())
            .error(status.getReasonPhrase())
            .message(message)
            .path(request.getRequestURI());
    return ResponseEntity.status(status).body(body);
  }
}
