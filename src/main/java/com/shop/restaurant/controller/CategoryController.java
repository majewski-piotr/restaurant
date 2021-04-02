package com.shop.restaurant.controller;

import com.shop.restaurant.model.CategoryReadModel;
import com.shop.restaurant.model.CategoryWriteModel;
import com.shop.restaurant.model.MenuPositionReadModel;
import com.shop.restaurant.service.CategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController {
  private CategoryService categoryService;
  private Logger logger = LoggerFactory.getLogger(CategoryController.class);

  @Autowired
  public CategoryController(CategoryService categoryService) {
    this.categoryService = categoryService;
  }

  @GetMapping
  ResponseEntity<List<CategoryReadModel>> findAll(){
    logger.info("[CategoryController] findAll()");
    List<CategoryReadModel> result = categoryService.findAll();
    return ResponseEntity.ok(result);
  }

  @GetMapping("/{id}/menupositions")
  ResponseEntity<List<MenuPositionReadModel>> findByCategoryId(@PathVariable  int id){
    logger.info("[CategoryController] findByCategory()");
    List<MenuPositionReadModel> result = categoryService.findByCategoryId(id);
    return ResponseEntity.ok(result);
  }

  @PostMapping
  ResponseEntity<CategoryReadModel> create(@RequestBody CategoryWriteModel source){
    CategoryReadModel created = categoryService.save(source);
    return ResponseEntity.created(URI.create("/"+created.getId())).body(created);
  }
}
