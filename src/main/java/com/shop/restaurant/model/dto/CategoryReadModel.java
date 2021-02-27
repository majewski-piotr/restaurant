package com.shop.restaurant.model.dto;

import com.shop.restaurant.model.Category;

public class CategoryReadModel {
  private int id;
  private String name;
  boolean fixedCost;
  int fixedCostValue;

  public CategoryReadModel(Category source) {
    this.id = source.getId();
    this.name = source.getName();
    this.fixedCost = source.isFixedCost();
    if(source.isFixedCost()){
      this.fixedCostValue = source.getFixedCostValue();
    }
  }

  public boolean isFixedCost() {
    return fixedCost;
  }

  public void setFixedCost(boolean fixedCost) {
    this.fixedCost = fixedCost;
  }

  public int getFixedCostValue() {
    return fixedCostValue;
  }

  public void setFixedCostValue(int fixedCostValue) {
    this.fixedCostValue = fixedCostValue;
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
