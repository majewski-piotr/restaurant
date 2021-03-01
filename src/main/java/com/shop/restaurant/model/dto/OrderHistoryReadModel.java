package com.shop.restaurant.model.dto;

import com.shop.restaurant.model.Order;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderHistoryReadModel {
  private int id;
  private List<String> boughtPositionsNames;
  private List<Integer> boughtPositionCosts;
  private List<Integer> quantities;
  private String comment;
  private int cost;
  private LocalDateTime commitedTime;


  public OrderHistoryReadModel(Order source) {
    this.id = source.getId();
    this.comment = source.getComment();
    this.boughtPositionsNames = new ArrayList<>();
    this.boughtPositionCosts = new ArrayList<>();
    this.quantities = new ArrayList<>();
    source.getBoughtPositions().forEach(boughtPosition -> {
      this.boughtPositionsNames.add(boughtPosition.getMenuPosition().getName());
      this.boughtPositionCosts.add(boughtPosition.getMenuPosition().getCost());
      this.quantities.add(boughtPosition.getQuantity());
      this.cost += (boughtPosition.getMenuPosition().getCost() * boughtPosition.getQuantity());
    });
    try{
      this.commitedTime =  source.getCommitedTime().withNano(0);
    } catch (NullPointerException e){
      this.commitedTime = null;
    }
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

  public List<Integer> getBoughtPositionCosts() {
    return boughtPositionCosts;
  }

  public void setBoughtPositionCosts(List<Integer> boughtPositionCosts) {
    this.boughtPositionCosts = boughtPositionCosts;
  }

  public List<Integer> getQuantities() {
    return quantities;
  }

  public void setQuantities(List<Integer> quantities) {
    this.quantities = quantities;
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

  public LocalDateTime getCommitedTime() {
    return commitedTime;
  }

  public void setCommitedTime(LocalDateTime commitedTime) {
    this.commitedTime = commitedTime;
  }
}
