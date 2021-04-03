package com.shop.restaurant.persistence;

import javax.persistence.*;

@Entity
@Table(name = "menuPositions")
public class PositionEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;
  private int cost;
  private String name;
  @ManyToOne(fetch= FetchType.LAZY)
  CategoryEntity categoryEntity;

  public PositionEntity(){
  }

  public PositionEntity(int cost, String name, CategoryEntity categoryEntity) {
    this.cost = cost;
    this.name = name;
    this.categoryEntity = categoryEntity;
  }

  public PositionEntity(String name, CategoryEntity categoryEntity) {
    this.name = name;
    this.categoryEntity = categoryEntity;
    this.cost = categoryEntity.getFixedCostValue();
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

  public CategoryEntity getCategory() {
    return categoryEntity;
  }

  public void setCategory(CategoryEntity categoryEntity) {
    this.categoryEntity = categoryEntity;
  }
}
