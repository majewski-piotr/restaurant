package com.shop.restaurant.model.dto;

import com.shop.restaurant.model.BoughtPosition;

public class BoughtPositionReadModel {
  private int id;
  private int menuPositionId;
  private int quantity;

  public BoughtPositionReadModel(BoughtPosition source) {
    this.id = source.getId();
    this.menuPositionId = source.getMenuPosition().getId();
    this.quantity = source.getQuantity();
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public int getMenuPositionId() {
    return menuPositionId;
  }

  public void setMenuPositionId(int menuPositionId) {
    this.menuPositionId = menuPositionId;
  }

  public int getQuantity() {
    return quantity;
  }

  public void setQuantity(int quantity) {
    this.quantity = quantity;
  }
}
