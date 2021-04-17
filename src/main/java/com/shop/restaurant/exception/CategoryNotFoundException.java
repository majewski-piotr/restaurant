package com.shop.restaurant.exception;

public class CategoryNotFoundException extends ApplicationException{
  public CategoryNotFoundException(){super(ErrorDefinitions.CATEGORY_NOT_FOUND);}
}
