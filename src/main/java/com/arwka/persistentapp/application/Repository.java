package com.arwka.persistentapp.application;

import com.arwka.persistentapp.domain.model.Category;
import com.arwka.persistentapp.domain.model.Order;
import com.arwka.persistentapp.domain.model.Product;
import java.util.List;
import java.util.Map;

public interface Repository {

  void cleanDb();

  // -------

  Category save(Category category);

  Product save(Product product);

  Order createNewOrder(Order order);

  Order createNewEmptyOrder(Order order);

  Order createBasketsForOrder(Order order);

  Order decreaseProducts(Order order);

  // -------

  Category findCategoryByCode(long code);

  List<Order> getAllOrders();

  List<Product> getProductList();

  Product findProductByName(String name);

  List<Product> getProductListByDescription(String text);

  Map<Product, Long> getFiveMostPopularProductsWithOrderedCount();

  Category getMostPopularCategory();

  Long getAverageOrderPayment();

}
