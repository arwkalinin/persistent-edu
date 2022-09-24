package com.arwka.persistentapp.application.service;

import com.arwka.persistentapp.application.Repository;
import com.arwka.persistentapp.domain.model.Category;
import com.arwka.persistentapp.domain.model.Order;
import com.arwka.persistentapp.domain.model.Product;
import com.arwka.persistentapp.domain.StateEnum;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ProductServiceTest {

  @Autowired
  Repository repository;

  @Autowired
  OrdersService orderService;
  @Autowired
  ProductsService productsService;
  @Autowired
  CategoryService categoryService;

  @AfterEach
  void deleting() {
    repository.cleanDb();
  }

  @Test
  @DisplayName("create Product should save created Product")
  void createTest() {
    Category newCategory = categoryService.create(Category.of("test", 1001));
    Product newProduct
        = productsService.create(Product.of("test", newCategory, "no descr"));

    Assertions.assertEquals(newCategory, newProduct.getCategory());
  }

  @Test
  @DisplayName("getAllProducts should return all saved Product")
  void getAllProducts() {
    Category newCategory = categoryService.create(Category.of("test", 1001));
    Product newProduct
        = productsService.create(Product.of("test", newCategory, "no descr"));

    List<Product> products = productsService.getAllProducts();

    Assertions.assertEquals(products.get(0).getName(), newProduct.getName());
    Assertions.assertEquals(products.get(0).getDescription(), newProduct.getDescription());
    Assertions.assertEquals(1, products.size());
  }

  @Test
  @DisplayName("getProductsByDescription with description var should return excepted Products")
  void getProductsByDescription() {

    Category newCategory = categoryService.create(Category.of("test", 1001));
    Product newProduct = productsService.create(Product.of("test", newCategory));

    List<Product> products1 = productsService.getProductsByDescription("no description");
    List<Product> products2 = productsService.getProductsByDescription("escr");
    List<Product> products3 = productsService.getProductsByDescription("r");
    List<Product> products4 = productsService.getProductsByDescription("123");

    Assertions.assertEquals(newProduct.getDescription(), products1.get(0).getDescription());
    Assertions.assertEquals(newProduct.getName(), products1.get(0).getName());
    Assertions.assertEquals(products1, products2);
    Assertions.assertEquals(products1, products3);
    Assertions.assertNotEquals(products1, products4);
  }

  @Test
  @DisplayName("getFiveMostPopularProductsMap should return correct list")
  void getFiveMostPopularProductsMap() {

    Category newCategory = categoryService.create(Category.of("undefined", 1001));

    Product product1 = productsService.create(Product.of("test", newCategory, "no descr"));
    Product product2 = productsService.create(Product.of("dada", newCategory, "no descr"));

    HashMap<Product, Long> orderProducts = new HashMap<>();

    orderProducts.put(product1, 15L);
    orderProducts.put(product2, 7L);

    orderService.create(Order.of(100, StateEnum.NEW, orderProducts));

    Map<Product, Long> popular = productsService.getFiveMostPopularProductsMap();

    Assertions.assertEquals(2, popular.keySet().size());
    Assertions.assertEquals(15L, popular.get(product1));
    Assertions.assertEquals(7L, popular.get(product2));
  }

}
