package com.shop.restaurant.model;

public class BoughtPosition {
  private String name;
  private int positionId;
  private int cost;
  private int quantity;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getCost() {
    return cost;
  }

  public void setCost(int cost) {
    this.cost = cost;
  }

  public int getPositionId() {
    return positionId;
  }

  public void setPositionId(int positionId) {
    this.positionId = positionId;
  }

  public int getQuantity() {
    return quantity;
  }

  public void setQuantity(int quantity) {
    this.quantity = quantity;
  }
}
