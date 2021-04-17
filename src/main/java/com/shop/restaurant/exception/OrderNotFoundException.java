package com.shop.restaurant.exception;

public class OrderNotFoundException extends ApplicationException{
  public OrderNotFoundException(){super(ErrorDefinitions.ORDER_NOT_FOUND);}
}
