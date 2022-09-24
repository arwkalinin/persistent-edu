package com.arwka.persistentapp.infrastructure.controller.dto;

import com.arwka.persistentapp.domain.model.Order;
import com.arwka.persistentapp.domain.model.Product;
import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequest {

  private long payment;
  private String uid;
  private String paymentDate;
  private String state;
  private List<ProductRequest> productList;

  /**
   * Get new domain Order with products map.
   *
   * @param products - Map of products with amount in order
   * @return Order
   */
  public Order toOrderWithProductMap(Map<Product, Long> products) {
    return Order.of(payment, uid, paymentDate, state, products);
  }
}
