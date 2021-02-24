package com.shop.restaurant.service;

import com.shop.restaurant.model.dto.MenuPositionReadModel;
import com.shop.restaurant.model.dto.MenuPositionWriteModel;
import com.shop.restaurant.model.MenuPosition;
import com.shop.restaurant.model.repository.CategoryRepository;
import com.shop.restaurant.model.repository.MenuPositionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MenuPositionService {
  private MenuPositionRepository menuPositionRepository;
  private CategoryRepository categoryRepository;

  public MenuPositionService(MenuPositionRepository menuPositionRepository, CategoryRepository categoryRepository) {
    this.menuPositionRepository = menuPositionRepository;
    this.categoryRepository = categoryRepository;
  }

  private MenuPosition create(MenuPositionWriteModel source){
    MenuPosition created = new MenuPosition();
    created.setName(source.getName());
    created.setCategory(categoryRepository.findById(source.getCategoryId()).orElseThrow(
        ()-> new IllegalArgumentException("no such category")
    ));
    if(created.getCategory().isFixedCost()){
      created.setCost(created.getCategory().getFixedCostValue());
    }else{
      created.setCost(source.getCost());
    }
    return created;
  }

  public MenuPositionReadModel save(MenuPositionWriteModel source){
    MenuPosition saved = menuPositionRepository.save(create(source));
    return new MenuPositionReadModel(saved);
  }

  public List<MenuPositionReadModel> findAll(){
    List<MenuPosition> result = menuPositionRepository.findAll();
    return result.stream().map(MenuPositionReadModel::new).collect(Collectors.toList());
  }
}
