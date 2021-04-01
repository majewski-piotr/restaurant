package com.shop.restaurant.model;

import com.shop.restaurant.persistence.BoughtPosition;

public class BoughtPositionReadModel {
  private int orderId;
  private int id;
  private int menuPositionId;
  private int quantity;

  public BoughtPositionReadModel(BoughtPosition source) {
    this.orderId = source.getOrder().getId();
    this.id = source.getId();
    this.menuPositionId = source.getMenuPosition().getId();
    this.quantity = source.getQuantity();
  }

  public int getOrderId() {
    return orderId;
  }

  public void setOrderId(int orderId) {
    this.orderId = orderId;
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
