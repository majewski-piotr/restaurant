package com.shop.restaurant.service;

import com.shop.restaurant.model.Category;
import com.shop.restaurant.model.dto.CategoryReadModel;
import com.shop.restaurant.model.repository.CategoryRepository;
import com.shop.restaurant.model.dto.CategoryWriteModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryService {
  private CategoryRepository categoryRepository;

  @Autowired
  public CategoryService(CategoryRepository categoryRepository) {
    this.categoryRepository = categoryRepository;
  }

  private Category create(CategoryWriteModel source){
    Category created = new Category();
    created.setName(source.getName());
    if(source.isFixedCost()){
      created.setFixedCost(source.isFixedCost());
      created.setFixedCostValue(source.getFixedCostValue());
    }
    return created;
  }

  public CategoryReadModel save(CategoryWriteModel source){
    Category saved = categoryRepository.save(create(source));
    return new CategoryReadModel(saved);
  }

  public List<CategoryReadModel> findAll(){
    List<Category> result = categoryRepository.findAll();
    return result.stream().map(CategoryReadModel::new).collect(Collectors.toList());
  }
}
