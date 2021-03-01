package com.shop.restaurant.model;

import javax.persistence.*;

@Entity
@Table(name="boughtPositions")
public class BoughtPosition {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;
  @OneToOne
  private MenuPosition menuPosition;
  private int quantity;
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "orders_id")
  private Order order;

  public int getId() {
    return id;
  }

  public MenuPosition getMenuPosition() {
    return menuPosition;
  }

  public void setMenuPosition(MenuPosition menuPosition) {
    this.menuPosition = menuPosition;
  }

  public int getQuantity() {
    return quantity;
  }

  public void setQuantity(int quantity) {
    this.quantity = quantity;
  }

  public Order getOrder() {
    return order;
  }

  public void setOrder(Order order) {
    this.order = order;
  }
}
