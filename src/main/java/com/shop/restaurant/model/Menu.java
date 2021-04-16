package com.shop.restaurant.model;

import jdk.dynalink.linker.LinkerServices;

import java.util.List;

public class Menu {
 private List<Category> categories;

  public List<Category> getCategories() {
    return categories;
  }

  public void setCategories(List<Category> categories) {
    this.categories = categories;
  }
}


