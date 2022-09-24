package com.arwka.persistentapp.application.service;

import com.arwka.persistentapp.application.Repository;
import com.arwka.persistentapp.domain.model.Product;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ProductsService {

  private final Repository repository;

  // ------------

  /**
   * Save to DB new product.
   *
   * @param product - product-to-save
   * @return Product (saved)
   */
  public Product create(Product product) {
    return repository.save(product);
  }

  /**
   * Get all products.
   *
   * @return List of products
   */
  public List<Product> getAllProducts() {
    return repository.getProductList();
  }

  /**
   * Get all products with description contains ...
   *
   * @param description - description containing
   * @return List of products
   */
  public List<Product> getProductsByDescription(String description) {
    return repository.getProductListByDescription(description);
  }

  /**
   * Get HashMap five most popular products with amount.
   *
   * @return HashMap of products with amount
   */
  public Map<Product, Long> getFiveMostPopularProductsMap() {
    return repository.getFiveMostPopularProductsWithOrderedCount();
  }

  /**
   * Get product by name.
   *
   * @param name - name of product
   * @return Product with name
   */
  public Product getProductByName(String name) {
    return repository.findProductByName(name);
  }

}