package com.shop.restaurant.exception;

public class BoughtPositionNotFoundException extends ApplicationException{
  public BoughtPositionNotFoundException(){super(ErrorDefinitions.BOUGHTPOSITION_NOT_FOUND);}
}
