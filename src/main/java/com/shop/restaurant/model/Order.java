package com.shop.restaurant.model;

import java.time.LocalDateTime;
import java.util.List;

public class Order {
  private int id;
  private String comment;
  private LocalDateTime commitedTime;
  private List<BoughtPosition> boughtPositions;
  private int cost;

  public int getId() {
    return id;
  }

  public int getCost() {
    return cost;
  }

  public void setCost(int cost) {
    this.cost = cost;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getComment() {
    return comment;
  }

  public void setComment(String comment) {
    this.comment = comment;
  }

  public LocalDateTime getCommitedTime() {
    return commitedTime;
  }

  public void setCommitedTime(LocalDateTime commitedTime) {
    this.commitedTime = commitedTime;
  }

  public List<BoughtPosition> getBoughtPositions() {
    return boughtPositions;
  }

  public void setBoughtPositions(List<BoughtPosition> boughtPositions) {
    this.boughtPositions = boughtPositions;
  }
}
