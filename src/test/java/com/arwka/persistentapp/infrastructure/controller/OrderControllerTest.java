package com.arwka.persistentapp.infrastructure.controller;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.arwka.persistentapp.application.Repository;
import com.arwka.persistentapp.application.service.CategoryService;
import com.arwka.persistentapp.application.service.OrdersService;
import com.arwka.persistentapp.application.service.ProductsService;
import com.arwka.persistentapp.domain.model.Category;
import com.arwka.persistentapp.domain.model.Product;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class OrderControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private OrdersService ordersService;

  @Autowired
  private CategoryService categoryService;

  @Autowired
  private ProductsService productsService;

  @Autowired
  private Repository repository;

  @AfterEach
  void deleting() {
    repository.cleanDb();
  }

  @Test
  @DisplayName("postOrdersFromCsv should save orders in DB and return correct orders")
  void postOrdersFromCsvTest() throws Exception {

    mockMvc.perform(post("/load"))
        .andExpect(status().isCreated());

    Assertions.assertEquals(150, ordersService.getAverageOrderPayment());
  }

  @Test
  void postOrderTest() throws Exception {
    String jsonToPost = """
        {
          "payment" : 150,
          "uid":"1234567890test",
          "paymentDate":"2022-08-26T07:56:19.736429Z[GMT]",
          "state":"NEW",
          "productList":
          [
          {
              "description":"descr",
              "name":"pineapple",
              "categoryCode":100,
              "quantity":3
          }
          ]
        }
        """;

    Category category = categoryService.create(Category.of("test", 100));
    productsService.create(Product.of("pineapple", category));

    mockMvc.perform(post("/order")
            .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
            .content(jsonToPost))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.payment", is(150)))
        .andExpect(jsonPath("$.uid", is("1234567890test")));
  }


}
