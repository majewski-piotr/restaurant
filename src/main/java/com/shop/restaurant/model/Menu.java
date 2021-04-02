package com.shop.restaurant.model;

import jdk.dynalink.linker.LinkerServices;

import java.util.List;

public class Menu {
 private List<Category> menu;

  public List<Category> getMenu() {
    return menu;
  }

  public void setMenu(List<Category> menu) {
    this.menu = menu;
  }
}


