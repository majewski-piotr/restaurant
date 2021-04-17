package com.shop.restaurant.exception;

public class ApplicationException extends Exception{
  private final ErrorDefinitions errorDefinitions;

  public ApplicationException(ErrorDefinitions errorDefinitions) {
    this.errorDefinitions = errorDefinitions;
  }

  public ErrorDefinitions getErrorCodeDefinitions() {
    return errorDefinitions;
  }
}
