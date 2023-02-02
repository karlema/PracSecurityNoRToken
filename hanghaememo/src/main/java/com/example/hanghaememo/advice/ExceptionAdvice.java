//package com.example.hanghaememo.advice;
package com.example.hanghaememo.advice;
import com.example.hanghaememo.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionAdvice {
  @ExceptionHandler(value = { IllegalArgumentException.class })
  public ResponseEntity<Object> handleApiRequestException(IllegalArgumentException ex) {
    RestApiException restApiException = new RestApiException();
    restApiException.setHttpStatus(HttpStatus.FORBIDDEN);
    restApiException.setErrorMessage(ex.getMessage());

    return new ResponseEntity(
        restApiException,
        HttpStatus.BAD_GATEWAY
    );
  }
}