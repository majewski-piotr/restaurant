package com.shop.restaurant.model;
import com.shop.restaurant.persistence.Order;

import java.util.ArrayList;
import java.util.List;

public class OrderReadModel {
  private int id;
  private List<String> boughtPositionsNames;
  private List<Integer> boughtPositionsIds;
  private List<Integer> boughtPositionsQuantities;
  private List<Integer> boughtPositionsPrices;
  private String comment;
  private int cost;
  private boolean commited;

  public OrderReadModel(Order source) {
    this.id = source.getId();
    this.comment = source.getComment();
    this.boughtPositionsNames = new ArrayList<>();
    this.boughtPositionsIds = new ArrayList<>();
    this.boughtPositionsPrices = new ArrayList<>();
    this.boughtPositionsQuantities = new ArrayList<>();
    source.getBoughtPositions().forEach(boughtPosition -> {
      this.boughtPositionsNames.add(boughtPosition.getMenuPosition().getName());
      this.boughtPositionsIds.add(boughtPosition.getMenuPosition().getId());
      this.boughtPositionsQuantities.add(boughtPosition.getQuantity());
      this.boughtPositionsPrices.add(boughtPosition.getMenuPosition().getCost());
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

  public List<Integer> getBoughtPositionsIds() {
    return boughtPositionsIds;
  }

  public void setBoughtPositionsIds(List<Integer> boughtPositionsIds) {
    this.boughtPositionsIds = boughtPositionsIds;
  }

  public List<Integer> getBoughtPositionsPrices() {
    return boughtPositionsPrices;
  }

  public void setBoughtPositionsPrices(List<Integer> boughtPositionsPrices) {
    this.boughtPositionsPrices = boughtPositionsPrices;
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
