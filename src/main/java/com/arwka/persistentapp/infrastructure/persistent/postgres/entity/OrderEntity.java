package com.arwka.persistentapp.infrastructure.persistent.postgres.entity;

import com.arwka.persistentapp.domain.model.Order;
import com.arwka.persistentapp.domain.StateEnum;
import java.time.ZonedDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

// Lombok
@NoArgsConstructor
@EqualsAndHashCode
@Getter
@Setter
@ToString
// ---
@Entity
@Table(schema = "public", name = "order")
public class OrderEntity {

  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @Column(name = "payment")
  private long payment;

  @Column(name = "uid")
  @Size(min = 14, max = 14, message = "UID should have 14 symbols")
  private String uid;

  @Column(name = "payment_date")
  private ZonedDateTime paymentDate;

  @Column(name = "state")
  @Enumerated(EnumType.STRING)
  private StateEnum state;

  public OrderEntity(long payment, String uid, StateEnum state) {
    this.payment = payment;
    this.uid = uid;
    this.state = state;
  }

  /**
   * Get OrderEntity from domain Order.
   *
   * @param order - domain order
   * @return OrderEntity
   */
  public static OrderEntity of(Order order) {
    OrderEntity orderEntityToReturn
        = new OrderEntity(order.getPayment(), order.getUid(), order.getState());

    orderEntityToReturn.setPaymentDate(order.getPaymentDate());

    return orderEntityToReturn;
  }

  /**
   * Get Order from this.
   *
   * @return Order
   */
  public Order toOrder() {
    return Order.of(payment, uid, paymentDate, state);
  }

}
