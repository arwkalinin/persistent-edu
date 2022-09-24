package com.arwka.persistentapp.infrastructure.persistent.postgres;

import com.arwka.persistentapp.application.Repository;
import com.arwka.persistentapp.domain.model.Category;
import com.arwka.persistentapp.domain.model.Order;
import com.arwka.persistentapp.domain.model.Product;
import com.arwka.persistentapp.infrastructure.persistent.postgres.entity.BasketEntity;
import com.arwka.persistentapp.infrastructure.persistent.postgres.entity.CategoryEntity;
import com.arwka.persistentapp.infrastructure.persistent.postgres.entity.OrderEntity;
import com.arwka.persistentapp.infrastructure.persistent.postgres.entity.ProductEntity;
import com.arwka.persistentapp.infrastructure.persistent.postgres.repository.BasketRepository;
import com.arwka.persistentapp.infrastructure.persistent.postgres.repository.CategoryRepository;
import com.arwka.persistentapp.infrastructure.persistent.postgres.repository.OrderRepository;
import com.arwka.persistentapp.infrastructure.persistent.postgres.repository.ProductRepository;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
@Profile(value = "!mongo")
public class PostgresRepositoryImpl implements Repository {

  private final CategoryRepository categoryRepository;
  private final ProductRepository productRepository;
  private final OrderRepository orderRepository;
  private final BasketRepository basketRepository;

  // -------

  /**
   * Clean DB.
   */
  @Override
  public void cleanDb() {
    basketRepository.deleteAll();
    productRepository.deleteAll();
    orderRepository.deleteAll();
    categoryRepository.deleteAll();
  }

  // ---

  /**
   * Save category to DB.
   *
   * @param category - category-to-save
   * @return Category
   */
  @Override
  public Category save(Category category) {
    categoryRepository.save(CategoryEntity.of(category));
    return category;
  }

  /**
   * Save product in DB.
   *
   * @param product - product-to-save
   * @return Product
   */
  @Override
  public Product save(Product product) {
    productRepository.save(ProductEntity.of(product));
    return product;
  }

  /**
   * Create new order in DB.
   *
   * @param order - order-to-create
   * @return Order
   */
  @Override
  public Order createNewOrder(Order order) {
    return orderRepository.save(OrderEntity.of(order)).toOrder();
  }

  /**
   * Create product basket by order fields.
   *
   * @param order - order
   * @return Order
   */
  @Override
  @Transactional
  public Order createBasketsForOrder(Order order) {

    Map<Product, Long> mapOfProductsInOrder = order.getProductsBasket();

    mapOfProductsInOrder.keySet().forEach(product -> basketRepository.save(new BasketEntity(
        order.getUid(), product.getName(), mapOfProductsInOrder.get(product)))
    );

    return order;
  }

  /**
   * Decrease amount of products in DB by order product list.
   *
   * @param order - order
   * @return Order
   */
  @Override
  @Transactional
  public Order decreaseProducts(Order order) {

    Map<Product, Long> mapOfProductsInOrder = order.getProductsBasket();

    mapOfProductsInOrder.keySet().forEach(product -> {
      long currentProductQuantity = productRepository.findByName(product.getName()).getQuantity();
      productRepository.updateQuantity(
          product.getName(), currentProductQuantity - mapOfProductsInOrder.get(product));
    });

    return order;
  }

  /**
   * Create order without a product basket.
   *
   * @param order - заказ для создания
   * @return Order (created)
   */
  @Override
  public Order createNewEmptyOrder(Order order) {
    OrderEntity orderToSave = OrderEntity.of(order);
    OrderEntity savedOrder = orderRepository.save(orderToSave);
    return savedOrder.toOrder();
  }

  /**
   * Get all orders.
   *
   * @return List of orders.
   */
  @Override
  public List<Order> getAllOrders() {
    List<Order> ordersToReturn = new ArrayList<>();
    List<OrderEntity> orderEntities = orderRepository.findAll();

    orderEntities.forEach(orderEntity -> ordersToReturn.add(orderEntity.toOrder()));

    return ordersToReturn;
  }

  // ---

  /**
   * Get all products.
   *
   * @return List of products
   */
  @Override
  public List<Product> getProductList() {
    List<ProductEntity> listOfProductEntity = productRepository.findAll();

    List<Product> listOfProduct = new ArrayList<>(listOfProductEntity.size());
    listOfProductEntity.forEach(productEntity -> listOfProduct.add(productEntity.toProduct()));

    return listOfProduct;
  }

  /**
   * Find product by name.
   *
   * @param name - product name
   * @return Product
   */
  @Override
  public Product findProductByName(String name) {
    return productRepository.findProductEntityByName(name).toProduct();
  }

  /**
   * Get list of products by text in description.
   *
   * @param text - text in description.
   * @return List of products
   */
  @Override
  public List<Product> getProductListByDescription(String text) {
    List<ProductEntity> listOfProductEntity
        = productRepository.findByDescriptionContaining(text);

    List<Product> listOfProduct = new ArrayList<>(listOfProductEntity.size());
    listOfProductEntity.forEach(productEntity -> listOfProduct.add(productEntity.toProduct()));

    return listOfProduct;
  }

  /**
   * Get five most popular products with ordered amount.
   *
   * @return Map of products and ordered amount.
   */
  @Override
  public Map<Product, Long> getFiveMostPopularProductsWithOrderedCount() {
    List<ProductEntity> listOfProductEntity = productRepository.findFiveMostOrderedProducts();
    HashMap<Product, Long> productOrdersMap = new HashMap<>(listOfProductEntity.size(), 1);

    listOfProductEntity.forEach(
        productEntity -> productOrdersMap.put(
            productEntity.toProduct(),
            basketRepository.findAllByProductName(productEntity.getName()).get(0)
                .getCountOfProduct())
    );

    return productOrdersMap;
  }

  // ---

  /**
   * Get most popular category in DB.
   *
   * @return Category
   */
  @Override
  public Category getMostPopularCategory() {
    CategoryEntity categoryEntity = categoryRepository.findMostPopularCategory();
    if (null == categoryEntity) {
      return Category.of("not data", 404);
    } else {
      return categoryRepository.findMostPopularCategory()
          .toCategory();
    }
  }

  /**
   * Find category by code.
   *
   * @param code - category code.
   * @return Category
   */
  @Override
  public Category findCategoryByCode(long code) {
    return categoryRepository.findByCode(code).toCategory();
  }

  /**
   * Get average payment in order.
   *
   * @return Long
   */
  @Override
  public Long getAverageOrderPayment() {
    return orderRepository.getAveragePayment();
  }

}
