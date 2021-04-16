package com.shop.restaurant.utils;

import com.shop.restaurant.model.Category;
import com.shop.restaurant.model.Position;
import com.shop.restaurant.persistence.CategoryEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CategoryConverter {
  public static Category createModel(CategoryEntity source){
    Category created = new Category();
    created.setId(source.getId());
    created.setName(source.getName());
    created.setFixedCost(source.isFixedCost());
    if(created.isFixedCost()){
      created.setFixedCostValue(source.getFixedCostValue());
    }
    created.setPositions(new ArrayList<>());
    return created;
  }

  public static Category createModel(CategoryEntity source, List<Position> positions){
    Category created = new Category();
    created.setId(source.getId());
    created.setName(source.getName());
    created.setFixedCost(source.isFixedCost());
    created.setPositions(positions);
    if(created.isFixedCost()){
      created.setFixedCostValue(source.getFixedCostValue());
      created.setPositions(created.getPositions().stream().map(
          position -> {
            position.setCost(created.getFixedCostValue());
            return position;
          }).collect(Collectors.toList()));
    }
    return created;
  }
}
