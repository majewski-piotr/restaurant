package com.shop.restaurant.utils;

import com.shop.restaurant.model.BoughtPosition;
import com.shop.restaurant.persistence.BoughtPositionEntity;

public class BoughtPositionConverter {
  public static BoughtPosition createModel(BoughtPositionEntity source){
    BoughtPosition created = new BoughtPosition();
    created.setQuantity(source.getQuantity());
    created.setPositionId(source.getMenuPosition().getId());
    created.setCost(source.getMenuPosition().getCost());
    created.setName(source.getMenuPosition().getName());
    return created;
  }
}
