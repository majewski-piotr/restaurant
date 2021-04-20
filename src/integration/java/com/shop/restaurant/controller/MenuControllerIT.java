package com.shop.restaurant.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shop.restaurant.model.Category;
import com.shop.restaurant.model.Menu;
import com.shop.restaurant.service.MenuService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("integration")
public class MenuControllerIT {

  @Autowired
  private MockMvc mockMvc;
  @Autowired
  private WebApplicationContext webApplicationContext;
  @Autowired
  MenuService menuService;
  @Autowired
  ObjectMapper objectMapper;

  @BeforeEach
  public void setUp() {
    this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
  }

  private static final String MENU_ENDPOINT = "/menu";

  @Test
  void shouldSuccesfullyReturnMenu() throws Exception {
    MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(MENU_ENDPOINT)
        .contentType(MediaType.APPLICATION_JSON))
        .andDo(MockMvcResultHandlers.print())
        .andReturn();

    String menuResponse = mvcResult.getResponse().getContentAsString();
    Menu correctMenu = menuService.createMenu();
    Menu response = objectMapper.readValue(menuResponse, Menu.class);

    for(int i = 0; i<correctMenu.getCategories().size();i++){

      assertThat(response.getCategories().get(i).getFixedCostValue())
          .isEqualTo(correctMenu.getCategories().get(i).getFixedCostValue());
      assertThat(response.getCategories().get(i).getId())
          .isEqualTo(correctMenu.getCategories().get(i).getId());

      for(int j = 0; j<correctMenu.getCategories().get(i).getPositions().size();j++){
        assertThat(response.getCategories().get(i).getPositions().get(j).getId())
            .isEqualTo(correctMenu.getCategories().get(i).getPositions().get(j).getId());
        assertThat(response.getCategories().get(i).getPositions().get(j).getCost())
            .isEqualTo(correctMenu.getCategories().get(i).getPositions().get(j).getCost());
      }
    }

  }
}
