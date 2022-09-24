package com.arwka.persistentapp.infrastructure.persistent.postgres.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
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
@Entity()
@Table(name = "basket")
public class BasketEntity {

  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @NotNull
  @Column(name = "order_uid")
  private String orderUid;

  @NotNull
  @Column(name = "product_name")
  private String productName;

  @Column(name = "count_of_product")
  private long countOfProduct;

  public BasketEntity(String orderUid, String productName, long countOfProduct) {
    this.orderUid = orderUid;
    this.productName = productName;
    this.countOfProduct = countOfProduct;
  }

}
