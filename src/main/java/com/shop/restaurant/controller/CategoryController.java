package com.shop.restaurant.controller;

import com.shop.restaurant.model.dto.CategoryReadModel;
import com.shop.restaurant.model.dto.CategoryWriteModel;
import com.shop.restaurant.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController {
  private CategoryService categoryService;

  @Autowired
  public CategoryController(CategoryService categoryService) {
    this.categoryService = categoryService;
  }

  @GetMapping
  ResponseEntity<List<CategoryReadModel>> findAll(){
    List<CategoryReadModel> result = categoryService.findAll();
    return ResponseEntity.ok(result);
  }

  @PostMapping
  ResponseEntity<CategoryReadModel> create(@RequestBody CategoryWriteModel source){
    CategoryReadModel created = categoryService.save(source);
    return ResponseEntity.created(URI.create("/"+created.getId())).body(created);
  }
}
