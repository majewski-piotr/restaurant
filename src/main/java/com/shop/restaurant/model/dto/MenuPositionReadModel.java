package com.shop.restaurant.model.dto;

import com.shop.restaurant.model.MenuPosition;

public class MenuPositionReadModel {
  private int id;
  private int cost;
  private String name;

  public MenuPositionReadModel(MenuPosition source) {
    this.id = source.getId();
    this.name = source.getName();
    if(source.getCategory().isFixedCost()){
      this.cost = source.getCategory().getFixedCostValue();
    } else {
      this.cost = source.getCost();
    }
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public int getCost() {
    return cost;
  }

  public void setCost(int cost) {
    this.cost = cost;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
