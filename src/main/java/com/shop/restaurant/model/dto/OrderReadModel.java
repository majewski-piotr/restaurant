package com.shop.restaurant.model.dto;
import com.shop.restaurant.model.Order;

import java.util.ArrayList;
import java.util.List;

public class OrderReadModel {
  private int id;
  private List<String> boughtPositionsNames;
  private List<Integer> boughtPositionsQuantities;
  private String comment;
  private int cost;
  private boolean commited;

  public OrderReadModel(Order source) {
    this.id = source.getId();
    this.comment = source.getComment();
    this.boughtPositionsNames = new ArrayList<>();
    this.boughtPositionsQuantities = new ArrayList<>();
    source.getBoughtPositions().forEach(boughtPosition -> {
      this.boughtPositionsNames.add(boughtPosition.getMenuPosition().getName());
      this.boughtPositionsQuantities.add(boughtPosition.getQuantity());
      this.cost += (boughtPosition.getMenuPosition().getCost() * boughtPosition.getQuantity());
    });
    this.commited = source.isCommited();
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public List<String> getBoughtPositionsNames() {
    return boughtPositionsNames;
  }

  public void setBoughtPositionsNames(List<String> boughtPositionsNames) {
    this.boughtPositionsNames = boughtPositionsNames;
  }

  public List<Integer> getBoughtPositionsQuantities() {
    return boughtPositionsQuantities;
  }

  public void setBoughtPositionsQuantities(List<Integer> boughtPositionsQuantities) {
    this.boughtPositionsQuantities = boughtPositionsQuantities;
  }

  public String getComment() {
    return comment;
  }

  public void setComment(String comment) {
    this.comment = comment;
  }

  public int getCost() {
    return cost;
  }

  public void setCost(int cost) {
    this.cost = cost;
  }

  public boolean isCommited() {
    return commited;
  }

  public void setCommited(boolean commited) {
    this.commited = commited;
  }
}
