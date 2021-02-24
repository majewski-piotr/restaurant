package com.shop.restaurant.controller;

import com.shop.restaurant.model.dto.BoughtPositionWriteModel;
import com.shop.restaurant.service.BoughtPositionService;
import com.shop.restaurant.model.dto.BoughtPositionReadModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/boughtpositions")
public class BoughtPositionController {
  private BoughtPositionService boughtPositionService;

  public BoughtPositionController(BoughtPositionService boughtPositionService) {
    this.boughtPositionService = boughtPositionService;
  }

  @GetMapping
  ResponseEntity<List<BoughtPositionReadModel>> findAll(){
    List<BoughtPositionReadModel> result = boughtPositionService.findAll();
    return ResponseEntity.ok(result);
  }

  @PostMapping
  ResponseEntity<BoughtPositionReadModel> create(@RequestBody BoughtPositionWriteModel source){
    BoughtPositionReadModel created = boughtPositionService.save(source);
    return ResponseEntity.created(URI.create("/"+created.getId())).body(created);
  }
}
