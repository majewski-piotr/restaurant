package com.shop.restaurant.service;

import com.shop.restaurant.model.dto.BoughtPositionWriteModel;
import com.shop.restaurant.model.BoughtPosition;
import com.shop.restaurant.model.dto.BoughtPositionReadModel;
import com.shop.restaurant.model.repository.BoughtPositionRepository;
import com.shop.restaurant.model.repository.MenuPositionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BoughtPositionService {
  private BoughtPositionRepository boughtPositionRepository;
  private MenuPositionRepository menuPositionRepository;

  @Autowired
  public BoughtPositionService(BoughtPositionRepository boughtPositionRepository, MenuPositionRepository menuPositionRepository) {
    this.boughtPositionRepository = boughtPositionRepository;
    this.menuPositionRepository = menuPositionRepository;
  }

  private BoughtPosition create(BoughtPositionWriteModel source){
    BoughtPosition created = new BoughtPosition();
    created.setQuantity(source.getQuantity());
    System.out.println(source.getMenuPositionId());
    created.setMenuPosition(menuPositionRepository.findById(source.getMenuPositionId()).orElseThrow(
        ()-> new IllegalArgumentException("no such menu position")
    ));
    return created;
  }

  public BoughtPositionReadModel save(BoughtPositionWriteModel source){
    BoughtPosition saved = boughtPositionRepository.saveAndFlush(create(source));
    return new BoughtPositionReadModel(saved);
  }

  public List<BoughtPositionReadModel> findAll(){
    List<BoughtPosition> result= boughtPositionRepository.findAll();
    return result.stream().map(BoughtPositionReadModel::new).collect(Collectors.toList());
  }

  public BoughtPositionReadModel findById(int id){
    return new BoughtPositionReadModel(boughtPositionRepository.findById(id).orElseThrow(
        ()-> new IllegalArgumentException("no such bought position!")
    ));
  }


}
