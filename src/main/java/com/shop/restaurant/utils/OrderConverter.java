package com.shop.restaurant.utils;

import com.shop.restaurant.model.Order;
import com.shop.restaurant.persistence.OrderEntity;

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
}
