package com.arwka.persistentapp.application.service;

import com.arwka.persistentapp.application.Repository;
import com.arwka.persistentapp.domain.model.Category;
import com.arwka.persistentapp.domain.model.Order;
import com.arwka.persistentapp.domain.model.Product;
import com.arwka.persistentapp.domain.StateEnum;
import com.arwka.persistentapp.infrastructure.persistent.postgres.entity.CategoryEntity;
import java.util.HashMap;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CategoryServiceTest {

  @Autowired
  Repository repository;

  @Autowired
  CategoryService categoryService;
  @Autowired
  ProductsService productsService;
  @Autowired
  OrdersService ordersService;

  @AfterEach
  void deleting() {
    repository.cleanDb();
  }

  @Test
  @DisplayName("create Category should save correct Category")
  void createTest() {

    Category category = categoryService.create(Category.of("test", 1001));

    Assertions.assertEquals(
        CategoryEntity.of(category).getCode(),
        repository.findCategoryByCode(1001).getCode()
    );
  }

  @Test
  @DisplayName("getMostPopularCategory should return excepted category")
  void getMostPopularCategoryTest() {

    Category category1 = categoryService.create(Category.of("popular", 2000));
    categoryService.create(Category.of("notpopular", 1500));

    HashMap<Product, Long> orderProducts = new HashMap<>();

    orderProducts.put(productsService.create(Product.of("simple", category1)), 7L);

    ordersService.create(Order.of(100, StateEnum.PROCESSING, orderProducts));
    ordersService.create(Order.of(200, StateEnum.NEW, orderProducts));

    Assertions.assertEquals(category1, categoryService.getMostPopularCategory());
  }

}
