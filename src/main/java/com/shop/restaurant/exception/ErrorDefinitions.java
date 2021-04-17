package com.shop.restaurant.exception;

public enum ErrorDefinitions {
  BOUGHTPOSITION_NOT_FOUND(1000,"Wrong id","No BoughtPosition for this id"),
  ORDER_NOT_FOUND(1001,"Wrong id", "No Order for this id"),
  POSITION_NOT_FOUND(1002,"Wrong id", "No position for this id"),
  CATEGORY_NOT_FOUND(1003, "Wrong id", "No category for this id");


  private final int code;
  private final String message;
  private final String description;

  ErrorDefinitions(int code, String message, String description) {
    this.code = code;
    this.message = message;
    this.description = description;
  }

  public int getCode() {
    return code;
  }

  public String getMessage() {
    return message;
  }

  public String getDescription() {
    return description;
  }
}
