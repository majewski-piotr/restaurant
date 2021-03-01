package com.shop.restaurant.model.dto;

public class BoughtPositionWriteModel {

  private int menuPositionId;
  private int quantity;



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
