package com.arwka.persistentapp.infrastructure.persistent.mongo;

import com.arwka.persistentapp.application.Repository;
import com.arwka.persistentapp.domain.model.Category;
import com.arwka.persistentapp.domain.model.Order;
import com.arwka.persistentapp.domain.model.Product;
import com.arwka.persistentapp.domain.StateEnum;
import com.arwka.persistentapp.infrastructure.persistent.mongo.entity.CategoryDocument;
import com.arwka.persistentapp.infrastructure.persistent.mongo.entity.OrderDocument;
import com.arwka.persistentapp.infrastructure.persistent.mongo.entity.ProductDocument;
import com.arwka.persistentapp.infrastructure.persistent.mongo.repository.CategoryMongoRepository;
import com.arwka.persistentapp.infrastructure.persistent.mongo.repository.OrderMongoRepository;
import com.arwka.persistentapp.infrastructure.persistent.mongo.repository.ProductMongoRepository;
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
@Profile(value = "mongo")
public class MongoRepositoryImpl implements Repository {

  private final CategoryMongoRepository categoryMongoRepository;
  private final OrderMongoRepository orderMongoRepository;
  private final ProductMongoRepository productMongoRepository;

  // -------

  /**
   * Clean DB.
   */
  @Override
  public void cleanDb() {
    categoryMongoRepository.deleteAll();
    productMongoRepository.deleteAll();
    orderMongoRepository.deleteAll();
  }

  /**
   * Save category to DB.
   *
   * @param category - category-to-save
   * @return Category
   */
  @Override
  public Category save(Category category) {
    categoryMongoRepository.save(CategoryDocument.of(category));
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
    productMongoRepository.save(ProductDocument.of(product));
    return product;
  }

  /**
   * Create new order in DB.
   *
   * @param order - order-to-create
   * @return Order
   */
  @Override
  @Transactional
  public Order createNewOrder(Order order) {
    return orderMongoRepository.save(OrderDocument.of(order)).toOrder();
  }

  /**
   * Create product basket by order fields.
   *
   * @param order - order
   * @return Order
   */
  @Override
  public Order createBasketsForOrder(Order order) {
    // no need by mongo context
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
      ProductDocument productToUpdate = productMongoRepository.findByName(product.getName());
      productToUpdate.setQuantity(productToUpdate.getQuantity() - mapOfProductsInOrder.get(product));
      productMongoRepository.deleteByName(productToUpdate.getName());
      productMongoRepository.save(productToUpdate);
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
  @Transactional
  public Order createNewEmptyOrder(Order order) {
    OrderDocument orderToSave = OrderDocument.of(order);
    OrderDocument savedOrder = orderMongoRepository.save(orderToSave);
    return savedOrder.toOrder();
  }

  /**
   * Get all orders.
   *
   * @return List of orders.
   */
  @Override
  public List<Order> getAllOrders() {
    // not implemented.
    return List.of(Order.of(700, StateEnum.PROCESSING, new HashMap<>()));
  }

  /**
   * Get all products.
   *
   * @return List of products
   */
  @Override
  public List<Product> getProductList() {

    List<ProductDocument> productDocumentList = productMongoRepository.findAll();
    List<Product> productListToReturn = new ArrayList<>();

    productDocumentList.forEach(
        productDocument -> productListToReturn.add(productDocument.toProduct())
    );

    return productListToReturn;
  }

  /**
   * Find product by name.
   *
   * @param name - product name
   * @return Product
   */
  @Override
  public Product findProductByName(String name) {
    return productMongoRepository.findProductEntityByName(name).toProduct();
  }

  /**
   * Get list of products by text in description.
   *
   * @param text - text in description.
   * @return List of products
   */
  @Override
  public List<Product> getProductListByDescription(String text) {

    List<Product> productListToReturn = new ArrayList<>();
    List<ProductDocument> productDocumentList
        = productMongoRepository.findAllByDescriptionContains(text);

    productDocumentList.forEach(
        productDocument -> productListToReturn.add(productDocument.toProduct())
    );

    return productListToReturn;
  }

  /**
   * Get five most popular products with ordered amount.
   *
   * @return Map of products and ordered amount.
   */
  @Override
  public Map<Product, Long> getFiveMostPopularProductsWithOrderedCount() {
    // not implemented.
    return null;
  }

  /**
   * Find category by code.
   *
   * @param code - category code.
   * @return Category
   */
  @Override
  public Category findCategoryByCode(long code) {
    // not implemented.
    return Category.of("test", 1001);
  }

  /**
   * Get most popular category in DB.
   *
   * @return Category
   */
  @Override
  public Category getMostPopularCategory() {
    // not implemented.
    return Category.of("popular", 2000);
  }

  /**
   * Get average payment in order.
   *
   * @return Long
   */
  @Override
  public Long getAverageOrderPayment() {
    // not implemented.
    return 150L;
  }
}
