package com.shop.restaurant.exception;

public class PositionNotFoundException extends ApplicationException{
  public PositionNotFoundException(){super(ErrorDefinitions.POSITION_NOT_FOUND);}
}
