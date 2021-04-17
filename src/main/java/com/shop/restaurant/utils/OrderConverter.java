package com.shop.restaurant.utils;

import com.shop.restaurant.model.BoughtPosition;
import com.shop.restaurant.model.Order;
import com.shop.restaurant.persistence.BoughtPositionEntity;
import com.shop.restaurant.persistence.CategoryEntity;
import com.shop.restaurant.persistence.OrderEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.shop.restaurant.utils.BoughtPositionConverter.createModel;

public class OrderConverter {
  public static Order createModel(OrderEntity source){
    Order created = new Order();
    created.setId(source.getId());
    created.setCommitedTime(source.getCommitedTime());
    created.setComment(source.getComment());
    created.setBoughtPositions(
        source.getBoughtPositions().stream()
            .map(boughtPositionEntity -> {
              created.setCost(
                  created.getCost() +
                      boughtPositionEntity.getQuantity()*boughtPositionEntity.getMenuPosition().getCost()
              );

              return BoughtPositionConverter.createModel(boughtPositionEntity);
            })
            .collect(Collectors.toList())
    );
    return created;
  }
  public static Map<String, Object>createFreemarkerModel(OrderEntity entitySource){
    Order source = createModel(entitySource);
    List<String> rows = new ArrayList<>();
    for(int i = 0; i<source.getBoughtPositions().size();i++){
      BoughtPosition currentBoughtPostitionEntity = source.getBoughtPositions().get(i);
      String line = String.format("%s: %d x %dzł",
          currentBoughtPostitionEntity.getName(),
          currentBoughtPostitionEntity.getQuantity(),
          currentBoughtPostitionEntity.getCost()
          );
      rows.add(line);
    }

    Map < String, Object > model = new HashMap< String, Object >();
    model.put("rows", rows);
    model.put("totalCost", source.getCost());
    model.put("time", source.getCommitedTime());
    return model;
  }
}
