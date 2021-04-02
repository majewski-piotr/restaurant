package com.shop.restaurant.service;

import com.shop.restaurant.model.Category;
import com.shop.restaurant.model.Menu;
import com.shop.restaurant.model.Position;
import com.shop.restaurant.persistence.CategoryEntity;
import com.shop.restaurant.persistence.MenuPosition;
import com.shop.restaurant.utils.CategoryConverter;
import com.shop.restaurant.utils.PositionConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.shop.restaurant.utils.PositionConverter.createModel;
import static com.shop.restaurant.utils.CategoryConverter.*;

@Service
public class MenuService {

  @PersistenceContext
  EntityManager entityManager;

  private CategoryService categoryService;
  private MenuPositionService menuPositionService;

  @Autowired
  public MenuService(CategoryService categoryService, MenuPositionService menuPositionService) {
    this.categoryService = categoryService;
    this.menuPositionService = menuPositionService;
  }

  public Menu createMenu(){
    Menu menu = new Menu();
    List<Category> categories = new ArrayList<>();
    List<CategoryEntity> categoryEntities= categoryService.findAll();
    categoryEntities.forEach(
        entity ->{
          List<Position> positions = categoryService.findByCategoryId(entity.getId()).stream().map(
              PositionConverter::createModel).collect(Collectors.toList());
          Category category = CategoryConverter.createModel(entity,positions);
          categories.add(category);
        }
        );
    menu.setMenu(categories);
    return menu;
  }
}
