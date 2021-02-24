package com.shop.restaurant.model.dto;

import com.shop.restaurant.model.Category;

public class CategoryReadModel {
  private int id;
  private String name;

  public CategoryReadModel(Category source) {
    this.id = source.getId();
    this.name = source.getName();
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
