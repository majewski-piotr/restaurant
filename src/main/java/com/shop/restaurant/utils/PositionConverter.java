package com.shop.restaurant.utils;

import com.shop.restaurant.model.Position;
import com.shop.restaurant.persistence.PositionEntity;

public class PositionConverter {

  public static Position createModel(PositionEntity source){
    Position created = new Position();
    created.setId(source.getId());
    created.setName(source.getName());
    created.setCost(source.getCost());

    return created;
  }
}
