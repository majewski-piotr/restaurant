package com.shop.restaurant.model;

import java.util.List;

public class Category {

  private int id;
  private String name;
  boolean fixedCost;
  int fixedCostValue;
  private List<Position> positions;

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

  public List<Position> getPositions() {
    return positions;
  }

  public void setPositions(List<Position> positions) {
    this.positions = positions;
  }
}
