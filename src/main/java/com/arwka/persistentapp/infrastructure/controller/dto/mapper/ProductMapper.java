package com.arwka.persistentapp.infrastructure.controller.dto.mapper;

import com.arwka.persistentapp.domain.model.Category;
import com.arwka.persistentapp.domain.model.Product;
import com.arwka.persistentapp.infrastructure.controller.dto.ProductRequest;

public class ProductMapper {

  public static Product mapToProduct(ProductRequest productRequest, Category category) {
    return productRequest.getDescription().isEmpty()
        ? Product.of(productRequest.getName(), category)
        : Product.of(productRequest.getName(), category, productRequest.getDescription());
  }

}
