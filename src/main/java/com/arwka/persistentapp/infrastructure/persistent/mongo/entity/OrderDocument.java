package com.arwka.persistentapp.infrastructure.persistent.mongo.entity;

import com.arwka.persistentapp.domain.StateEnum;
import com.arwka.persistentapp.domain.model.Order;
import com.arwka.persistentapp.domain.model.Product;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.Size;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.Document;

// Lombok
@NoArgsConstructor
@EqualsAndHashCode
@Getter
@Setter
@ToString
// ---
@Document
public class OrderDocument {

  private long payment;

  @Size(min = 14, max = 14, message = "UID should have 14 symbols")
  private String uid;

  private String paymentDate;

  @Column(name = "state")
  @Enumerated(EnumType.STRING)
  private StateEnum state;

  private List<ProductDocument> listOfProductsInOrder;

  public OrderDocument(
      long payment, String uid, String paymentDate, StateEnum state,
      List<ProductDocument> listOfProductsInOrder) {

    this.payment = payment;
    this.uid = uid;
    this.paymentDate = paymentDate;
    this.state = state;
    this.listOfProductsInOrder = listOfProductsInOrder;
  }

  /**
   * Get OrderDocument from Order domain
   *
   * @param order - domain order
   * @return OrderDocument
   */
  public static OrderDocument of(Order order) {

    List<ProductDocument> productsInOrder = new ArrayList<>();
    Map<Product, Long> domainProducts = order.getProductsBasket();

    domainProducts.keySet().forEach(product -> {
      ProductDocument productToInsert = ProductDocument.of(product);
      productToInsert.setQuantity(domainProducts.get(product));
      productsInOrder.add(productToInsert);
    });

    return new OrderDocument(
        order.getPayment(), order.getUid(), order.getPaymentDate().toString(),
        order.getState(), productsInOrder
    );
  }

  public Order toOrder() {
    return Order.of(payment, uid, ZonedDateTime.parse(paymentDate), state);
  }

}
