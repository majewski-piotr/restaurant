package com.shop.restaurant.controller;

import com.shop.restaurant.model.dto.MenuPositionReadModel;
import com.shop.restaurant.model.dto.MenuPositionWriteModel;
import com.shop.restaurant.service.MenuPositionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/menupositions")
public class MenuPositionController {
  private MenuPositionService menuPositionService;

  public MenuPositionController(MenuPositionService menuPositionService) {
    this.menuPositionService = menuPositionService;
  }

  @GetMapping
  ResponseEntity<List<MenuPositionReadModel>> findAll(){
    List<MenuPositionReadModel> result = menuPositionService.findAll();
    return ResponseEntity.ok(result);
  }

  @PostMapping
  ResponseEntity<MenuPositionReadModel> create(@RequestBody MenuPositionWriteModel source){
    MenuPositionReadModel created = menuPositionService.save(source);
    return ResponseEntity.created(URI.create("/"+created.getId())).body(created);
  }
}
