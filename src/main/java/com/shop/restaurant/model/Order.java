package com.shop.restaurant.model;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name="orders")
public class Order {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;
  @OneToMany(fetch = FetchType.LAZY, mappedBy = "order")
  private List<BoughtPosition> boughtPositions;
  private String comment;
  private boolean commited;
  private LocalDateTime commitedTime;

  public LocalDateTime getCommitedTime() {
    return commitedTime;
  }

  public void setCommitedTime(LocalDateTime commitedTime) {
    this.commitedTime = commitedTime;
  }

  public int getId() {
    return id;
  }

  public List<BoughtPosition> getBoughtPositions() {
    return boughtPositions;
  }

  public void setBoughtPositions(List<BoughtPosition> boughtPositions) {
    this.boughtPositions = boughtPositions;
  }

  public String getComment() {
    return comment;
  }

  public void setComment(String comment) {
    this.comment = comment;
  }

  public boolean isCommited() {
    return commited;
  }

  public void setCommited(boolean commited) {
    this.commited = commited;
  }
}
