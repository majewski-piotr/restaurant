package com.shop.restaurant.controller;

import com.shop.restaurant.model.Menu;
import com.shop.restaurant.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/menu")
public class MenuController {

  private MenuService menuService;

  @Autowired
  public MenuController(MenuService menuService) {
    this.menuService = menuService;
  }

  @GetMapping
  ResponseEntity<Menu> getMenu(){
    Menu result = menuService.createMenu();
    return ResponseEntity.ok(result);
  }
}
