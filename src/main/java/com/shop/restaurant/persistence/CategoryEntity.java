package com.shop.restaurant.persistence;

import javax.persistence.*;

@Entity
@Table(name = "categories")
public class CategoryEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;
  private String name;
  boolean fixedCost;
  int fixedCostValue;

  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public boolean isFixedCost() {
    return fixedCost;
  }

  public void setFixedCost(boolean fixedCost) {
    this.fixedCost = fixedCost;
  }

  public int getFixedCostValue() {
    return fixedCostValue;
  }

  public void setFixedCostValue(int fixedCostValue) {
    this.fixedCostValue = fixedCostValue;
  }
}
