package com.shop.restaurant.persistence;

import javax.persistence.*;

@Entity
@Table(name="boughtPositions")
public class BoughtPositionEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;
  @OneToOne
  private PositionEntity positionEntity;
  private int quantity;
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "orders_id")
  private OrderEntity orderEntity;

  public int getId() {
    return id;
  }

  public PositionEntity getMenuPosition() {
    return positionEntity;
  }

  public void setMenuPosition(PositionEntity positionEntity) {
    this.positionEntity = positionEntity;
  }

  public int getQuantity() {
    return quantity;
  }

  public void setQuantity(int quantity) {
    this.quantity = quantity;
  }

  public OrderEntity getOrder() {
    return orderEntity;
  }

  public void setOrder(OrderEntity orderEntity) {
    this.orderEntity = orderEntity;
  }
}
