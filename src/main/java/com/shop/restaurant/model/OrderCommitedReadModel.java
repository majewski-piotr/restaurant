package com.shop.restaurant.model;

import com.shop.restaurant.persistence.Order;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderCommitedReadModel {
  private int id;
  private List<String> boughtPositionsNames;
  private List<Integer> boughtPositionCosts;
  private List<Integer> quantities;
  private String comment;
  private int cost;
  private String commitedTime;


  public OrderCommitedReadModel(Order source) {
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
    this.commitedTime =  source.getCommitedTime().plusHours(1).format(DateTimeFormatter.ofPattern("HH:mm"));
  }

  public List<Integer> getBoughtPositionCosts() {
    return boughtPositionCosts;
  }

  public void setBoughtPositionCosts(List<Integer> boughtPositionCosts) {
    this.boughtPositionCosts = boughtPositionCosts;
  }

  public int getId() {
    return id;
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

  public int getCost() {
    return cost;
  }

  public void setCost(int cost) {
    this.cost = cost;
  }

  public String getCommitedTime() {
    return commitedTime;
  }

  public void setCommitedTime(String commitedTime) {
    this.commitedTime = commitedTime;
  }

  public List<String> getBoughtPositionsNames() {
    return boughtPositionsNames;
  }

  public void setBoughtPositionsNames(List<String> boughtPositionsNames) {
    this.boughtPositionsNames = boughtPositionsNames;
  }

  public List<Integer> getQuantities() {
    return quantities;
  }

  public void setQuantities(List<Integer> quantities) {
    this.quantities = quantities;
  }

  @Override
  public String toString() {
    String czasDostawy = commitedTime;
    String message =  "Dziękujemy za zamówienie!\n\nSzacowana godzina dostawy: "
        +czasDostawy+"\n\nTwoje zamówienie:\n";

    for(int i=0;i<this.quantities.size();i++){
    message = message.concat(
        this.boughtPositionsNames.get(i) + ":\t"+this.quantities.get(i)+"\tx\t"+this.boughtPositionCosts.get(i)+"zł\n");
    };
    message = message.concat("\nKoszt całkowity:\t"+this.cost+"zł\n");
    message = message.concat("\nUwagi do zamówienia:\n"+comment);
    return message;
  }

  public Map< String, Object > toFreemarkerModel(){

    List<String> rows = new ArrayList<>();
    for(int i = 0; i<this.boughtPositionsNames.size();i++){
      String rawString = this.boughtPositionsNames.get(i)+": "
          +this.quantities.get(i)+" x "
          +this.boughtPositionCosts.get(i)+"zł";
      rows.add(rawString);
    }
    Map < String, Object > model = new HashMap< String, Object >();
    model.put("rows", rows);
    model.put("totalCost", this.cost);
    model.put("time", this.commitedTime);
    return model;
  }

}
