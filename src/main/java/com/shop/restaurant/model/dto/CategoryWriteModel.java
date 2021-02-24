package com.shop.restaurant.model.dto;

public class CategoryWriteModel {
  private String name;
  boolean fixedCost;
  int fixedCostValue;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
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
}
