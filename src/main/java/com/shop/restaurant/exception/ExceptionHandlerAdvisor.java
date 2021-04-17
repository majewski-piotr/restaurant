package com.shop.restaurant.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import com.shop.restaurant.model.Error;

@RestControllerAdvice
public class ExceptionHandlerAdvisor {

  @ExceptionHandler({BoughtPositionNotFoundException.class})
  public ResponseEntity<?> handleBoughtPositionNotFoundException(BoughtPositionNotFoundException ex){
    Error error = createError(ex);
    return ResponseEntity.badRequest().body(error);
  }

  @ExceptionHandler({OrderNotFoundException.class})
  public ResponseEntity<?> handleOrderNotFoundException(OrderNotFoundException ex){
    Error error = createError(ex);
    return ResponseEntity.badRequest().body(error);
  }

  @ExceptionHandler({PositionNotFoundException.class})
  public ResponseEntity<?> handlePositionNotFoundException(PositionNotFoundException ex){
    Error error = createError(ex);
    return ResponseEntity.badRequest().body(error);
  }

  @ExceptionHandler({CategoryNotFoundException.class})
  public ResponseEntity<?> handleCategoryNotFoundException(CategoryNotFoundException ex){
    Error error = createError(ex);
    return ResponseEntity.badRequest().body(error);
  }

  private Error createError(ApplicationException ex){
    ErrorDefinitions errorDefinitions = ex.getErrorCodeDefinitions();
    Error error = new Error(
        errorDefinitions.getCode(),
        errorDefinitions.getMessage(),
        errorDefinitions.getDescription()
    );
    return error;
  }
}
