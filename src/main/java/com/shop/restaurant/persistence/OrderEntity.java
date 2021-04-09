package com.shop.restaurant.persistence;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name="orders")
public class OrderEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;
  @OneToMany(fetch = FetchType.LAZY, mappedBy = "order",cascade=CascadeType.ALL)
  private List<BoughtPositionEntity> boughtPositionEntities;
  private String comment;
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

  public List<BoughtPositionEntity> getBoughtPositions() {
    return boughtPositionEntities;
  }

  public void setBoughtPositions(List<BoughtPositionEntity> boughtPositionEntities) {
    this.boughtPositionEntities = boughtPositionEntities;
  }

  public String getComment() {
    return comment;
  }

  public void setComment(String comment) {
    this.comment = comment;
  }
}
