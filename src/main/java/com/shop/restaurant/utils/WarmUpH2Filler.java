package com.shop.restaurant.utils;

import com.shop.restaurant.persistence.CategoryEntity;
import com.shop.restaurant.persistence.PositionEntity;
import com.shop.restaurant.service.CategoryService;
import com.shop.restaurant.service.MenuPositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
class WarmUpH2Filler implements ApplicationListener<ContextRefreshedEvent> {
  private CategoryService categoryService;
  private MenuPositionService menuPositionService;

  @Autowired
  public WarmUpH2Filler(CategoryService categoryService, MenuPositionService menuPositionService) {
    this.categoryService = categoryService;
    this.menuPositionService = menuPositionService;
  }


  @Override
  public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {

    //categories

    CategoryEntity created1 = new CategoryEntity();
    created1.setName("Pizza");
    created1.setFixedCost(false);
    categoryService.saveCategory(created1);

    CategoryEntity created2 = new CategoryEntity();
    created2.setName("Dodatki do pizzy");
    created2.setFixedCost(true);
    created2.setFixedCostValue(2);
    categoryService.saveCategory(created2);

    CategoryEntity created3 = new CategoryEntity();
    created3.setName("Dania główne");
    created3.setFixedCost(false);
    categoryService.saveCategory(created3);

    CategoryEntity created4 = new CategoryEntity();
    created4.setName("Dodatki do dań głównych");
    created4.setFixedCost(false);
    categoryService.saveCategory(created4);

    CategoryEntity created5 = new CategoryEntity();
    created5.setName("Zupy");
    created5.setFixedCost(false);
    categoryService.saveCategory(created5);

    CategoryEntity created6 = new CategoryEntity();
    created6.setName("Napoje");
    created6.setFixedCost(true);
    created6.setFixedCostValue(5);
    categoryService.saveCategory(created6);



    //pizzas

    PositionEntity created7 = new PositionEntity();
    created7.setName("Margheritta");
    created7.setCost(20);
    created7.setCategory(categoryService.findAll().get(0));
    menuPositionService.savePosition(created7);

    PositionEntity created8 = new PositionEntity();
    created8.setName("Vegetariana");
    created8.setCost(22);
    created8.setCategory(categoryService.findAll().get(0));
    menuPositionService.savePosition(created8);

    PositionEntity created9 = new PositionEntity();
    created9.setName("Tosca");
    created9.setCost(25);
    created9.setCategory(categoryService.findAll().get(0));
    menuPositionService.savePosition(created9);

    PositionEntity created10 = new PositionEntity();
    created10.setName("Venecia");
    created10.setCost(25);
    created10.setCategory(categoryService.findAll().get(0));
    menuPositionService.savePosition(created10);

    //sides
    PositionEntity created11 = new PositionEntity();
    created11.setName("Podwójny ser");
    created11.setCategory(categoryService.findAll().get(1));
    menuPositionService.savePosition(created11);

    PositionEntity created12 = new PositionEntity();
    created12.setName("Salami");
    created12.setCategory(categoryService.findAll().get(1));
    menuPositionService.savePosition(created12);

    PositionEntity created13 = new PositionEntity();
    created13.setName("Szynka");
    created13.setCategory(categoryService.findAll().get(1));
    menuPositionService.savePosition(created13);

    PositionEntity created14 = new PositionEntity();
    created14.setName("Pieczarki");
    created14.setCategory(categoryService.findAll().get(1));
    menuPositionService.savePosition(created14);

    //mains
    PositionEntity created15 = new PositionEntity();
    created15.setName("Schabowy z frytkami/ryżem/ziemniakami");
    created15.setCost(30);
    created15.setCategory(categoryService.findAll().get(2));
    menuPositionService.savePosition(created15);

    PositionEntity created16 = new PositionEntity();
    created16.setName("Ryba z frytkami");
    created16.setCost(28);
    created16.setCategory(categoryService.findAll().get(2));
    menuPositionService.savePosition(created16);

    PositionEntity created17 = new PositionEntity();
    created17.setName("Placek po węgiersku");
    created17.setCost(27);
    created17.setCategory(categoryService.findAll().get(2));
    menuPositionService.savePosition(created17);

    //sides
    PositionEntity created23 = new PositionEntity();
    created23.setName("Bar sałatkowy");
    created23.setCost(5);
    created23.setCategory(categoryService.findAll().get(3));
    menuPositionService.savePosition(created23);

    PositionEntity created18 = new PositionEntity();
    created18.setName("Zestaw sosów");
    created18.setCost(6);
    created18.setCategory(categoryService.findAll().get(3));
    menuPositionService.savePosition(created18);

    //soups
    PositionEntity created24 = new PositionEntity();
    created24.setName("Pomidorowa");
    created24.setCost(12);
    created24.setCategory(categoryService.findAll().get(4));
    menuPositionService.savePosition(created24);

    PositionEntity created19 = new PositionEntity();
    created19.setName("Rosół");
    created19.setCost(10);
    created19.setCategory(categoryService.findAll().get(4));
    menuPositionService.savePosition(created19);

    //drinks
    PositionEntity created20 = new PositionEntity();
    created20.setName("Kawa");
    created20.setCategory(categoryService.findAll().get(5));
    menuPositionService.savePosition(created20);

    PositionEntity created21 = new PositionEntity();
    created21.setName("Herbata");
    created21.setCategory(categoryService.findAll().get(5));
    menuPositionService.savePosition(created21);

    PositionEntity created22 = new PositionEntity();
    created22.setName("Cola");
    created22.setCategory(categoryService.findAll().get(5));
    menuPositionService.savePosition(created22);
  }
}
