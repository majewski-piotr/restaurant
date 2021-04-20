package com.shop.restaurant.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shop.restaurant.model.BoughtPosition;
import com.shop.restaurant.model.Menu;
import com.shop.restaurant.model.Order;
import com.shop.restaurant.service.MenuPositionService;
import com.shop.restaurant.service.OrderService;
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

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("integration")
public class OrderControllerIT {
  @Autowired
  private MockMvc mockMvc;
  @Autowired
  private WebApplicationContext webApplicationContext;
  @Autowired
  private MenuPositionService menuPositionService;
  @Autowired
  private ObjectMapper objectMapper;
  @Autowired
  private OrderService orderService;

  @BeforeEach
  public void setUp() {
    this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
  }

  private static final String ORDER_ENDPOINT = "/orders";

  @Test
  void shouldUpdateCost() throws Exception {
    //given
    BoughtPosition boughtPosition1 = new BoughtPosition();
    boughtPosition1.setPositionId(menuPositionService.findAll().get(1).getId());
    boughtPosition1.setCost(menuPositionService.findAll().get(1).getCost());
    boughtPosition1.setName(menuPositionService.findAll().get(1).getName());
    boughtPosition1.setQuantity(3);
    BoughtPosition boughtPosition2 = new BoughtPosition();
    boughtPosition2.setPositionId(menuPositionService.findAll().get(1).getId());
    boughtPosition2.setCost(menuPositionService.findAll().get(1).getCost());
    boughtPosition2.setName(menuPositionService.findAll().get(1).getName());
    boughtPosition2.setQuantity(3);

    List<BoughtPosition> boughtPositionList = new ArrayList<>();
    boughtPositionList.add(boughtPosition1);
    boughtPositionList.add(boughtPosition2);

    Order order = new Order();
    order.setBoughtPositions(boughtPositionList);
    order.setId(2);
    order.setCommitedTime(LocalDateTime.now());
    order.setComment("comment");
    order.setCost(0);

    int trueCost = 0;

    for(BoughtPosition boughtPosition : order.getBoughtPositions()){
      trueCost += boughtPosition.getCost()*boughtPosition.getQuantity();
    }

    //when

    MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.patch(ORDER_ENDPOINT)
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(order)))
        .andDo(MockMvcResultHandlers.print())
        .andReturn();

    String orderResponse = mvcResult.getResponse().getContentAsString();
    Order response = objectMapper.readValue(orderResponse, Order.class);

    assertThat(response.getCost()).isEqualTo(trueCost);
  }

  @Test
  void shouldCommitOrder() throws Exception {
    //given
    BoughtPosition boughtPosition1 = new BoughtPosition();
    boughtPosition1.setPositionId(menuPositionService.findAll().get(1).getId());
    boughtPosition1.setCost(menuPositionService.findAll().get(1).getCost());
    boughtPosition1.setName(menuPositionService.findAll().get(1).getName());
    boughtPosition1.setQuantity(3);
    BoughtPosition boughtPosition2 = new BoughtPosition();
    boughtPosition2.setPositionId(menuPositionService.findAll().get(1).getId());
    boughtPosition2.setCost(menuPositionService.findAll().get(1).getCost());
    boughtPosition2.setName(menuPositionService.findAll().get(1).getName());
    boughtPosition2.setQuantity(3);

    List<BoughtPosition> boughtPositionList = new ArrayList<>();
    boughtPositionList.add(boughtPosition1);
    boughtPositionList.add(boughtPosition2);

    Order order = new Order();
    order.setBoughtPositions(boughtPositionList);
    order.setCommitedTime(LocalDateTime.now().minusSeconds(3));
    order.setComment("comment");
    order.setCost(0);

    int trueCost = 0;

    for(BoughtPosition boughtPosition : order.getBoughtPositions()){
      trueCost += boughtPosition.getCost()*boughtPosition.getQuantity();
    }

    //when

    MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post(ORDER_ENDPOINT+"/commit")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(order)))
        .andDo(MockMvcResultHandlers.print())
        .andReturn();

    String orderResponse = mvcResult.getResponse().getContentAsString();
    Order response = objectMapper.readValue(orderResponse, Order.class);

    assertThat(response.getCost()).isEqualTo(trueCost);
    assertThat(orderService.findAll().get(0).getId()).isEqualTo(response.getId());
    assertThat(orderService.findAll().get(0).getCost()).isEqualTo(trueCost);
    assertThat(orderService.findAll().get(0).getCommitedTime().getSecond()).isNotEqualTo(order.getCommitedTime().getSecond());
    assertThat(orderService.findAll().get(0).getCommitedTime().getSecond()).isEqualTo(response.getCommitedTime().getSecond());
  }


}
