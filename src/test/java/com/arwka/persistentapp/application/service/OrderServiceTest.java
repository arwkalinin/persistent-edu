package com.arwka.persistentapp.application.service;

import com.arwka.persistentapp.application.Repository;
import com.arwka.persistentapp.domain.model.Category;
import com.arwka.persistentapp.domain.model.Order;
import com.arwka.persistentapp.domain.model.Product;
import com.arwka.persistentapp.domain.StateEnum;
import java.util.HashMap;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class OrderServiceTest {

  @Autowired
  Repository repository;

  @Autowired
  OrdersService ordersService;
  @Autowired
  CategoryService categoryService;
  @Autowired
  ProductsService productService;

  @AfterEach
  void deleting() {
    repository.cleanDb();
  }

  @Test
  @DisplayName("create Order should save created Order")
  void createTest() {

    Category category = categoryService.create(Category.of("undefined", 1007));

    HashMap<Product, Long> orderProducts = new HashMap<>();

    orderProducts.put(
        productService.create(Product.of("simple", category)),
        7L
    );

    Order order = ordersService.create(Order.of(700, StateEnum.PROCESSING, orderProducts));

    Assertions.assertEquals(
        order.getPayment(),
        repository.getAllOrders().get(0).getPayment()
    );
    Assertions.assertEquals(
        order.getState(),
        repository.getAllOrders().get(0).getState()
    );
    Assertions.assertEquals(
        order.getUid(),
        repository.getAllOrders().get(0).getUid()
    );
  }

  @Test
  @DisplayName("getAverageOrderPayment should return 150")
  void getAverageOrderPayment() {

    Category category = categoryService.create(Category.of("undefined", 1007));

    HashMap<Product, Long> orderProducts = new HashMap<>();

    orderProducts.put(productService.create(Product.of("simple", category)), 7L);

    ordersService.create(Order.of(100, StateEnum.PROCESSING, orderProducts));
    ordersService.create(Order.of(200, StateEnum.NEW, orderProducts));

    Assertions.assertEquals(150, ordersService.getAverageOrderPayment());
  }

}
