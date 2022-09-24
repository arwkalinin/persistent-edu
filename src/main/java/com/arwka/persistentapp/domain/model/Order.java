package com.arwka.persistentapp.domain.model;

import com.arwka.persistentapp.domain.StateEnum;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@EqualsAndHashCode
@ToString
@Getter
public final class Order {

  private final int uidLength = 14;

  // -------

  private final long payment;
  private final String uid;
  private final ZonedDateTime paymentDate;
  private final StateEnum state;
  private final Map<Product, Long> productsBasket;

  // -------

  public static Order of(long payment, StateEnum state, Map<Product, Long> productsBasket) {
    return new Order(payment, state, productsBasket);
  }

  public static Order of(long payment, String uid, ZonedDateTime paymentDate, StateEnum state) {
    return new Order(payment, uid, paymentDate, state);
  }

  public static Order of(long payment, String uid, String paymentDate, String state) {
    return new Order(payment, uid, ZonedDateTime.parse(paymentDate), StateEnum.valueOf(state));
  }

  public static Order of(long payment, String uid, ZonedDateTime paymentDate,
                         StateEnum state, Map<Product, Long> productsBasket) {

    return new Order(payment, uid, paymentDate, state, productsBasket);
  }

  public static Order of(long payment, String uid, String paymentDate,
                         String state, Map<Product, Long> productsBasket) {

    return new Order(payment, uid, ZonedDateTime.parse(paymentDate),
                     StateEnum.valueOf(state), productsBasket);
  }

  // -------

  private Order(long payment, StateEnum state, Map<Product, Long> productsBasket) {
    this.payment = payment;
    this.uid = String.valueOf(UUID.randomUUID()).substring(0, uidLength);
    this.paymentDate = ZonedDateTime.now();
    this.state = state;
    this.productsBasket = productsBasket;
  }

  private Order(long payment, String uid, ZonedDateTime paymentDate, StateEnum state) {
    this.payment = payment;
    this.uid = uid;
    this.paymentDate = paymentDate;
    this.state = state;
    this.productsBasket = new HashMap<>();
  }

  private Order(long payment, String uid, ZonedDateTime paymentDate,
                StateEnum state, Map<Product, Long> productsBasket) {
    this.payment = payment;
    this.uid = uid;
    this.paymentDate = paymentDate;
    this.state = state;
    this.productsBasket = productsBasket;
  }

}
