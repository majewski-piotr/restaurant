package com.shop.restaurant.persistence;

import javax.persistence.*;

@Entity
@Table(name = "menuPositions")
public class MenuPosition {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;
  private int cost;
  private String name;
  @ManyToOne(fetch= FetchType.LAZY)
  Category category;

  public MenuPosition(){
  }

  public MenuPosition(int cost, String name, Category category) {
    this.cost = cost;
    this.name = name;
    this.category = category;
  }

  public MenuPosition(String name, Category category) {
    this.name = name;
    this.category = category;
    this.cost = category.getFixedCostValue();
  }

  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getCost() {
    return cost;
  }

  public void setCost(int cost) {
    this.cost = cost;
  }

  public Category getCategory() {
    return category;
  }

  public void setCategory(Category category) {
    this.category = category;
  }
}
