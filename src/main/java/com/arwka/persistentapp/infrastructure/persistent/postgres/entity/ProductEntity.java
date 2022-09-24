package com.arwka.persistentapp.infrastructure.persistent.postgres.entity;

import com.arwka.persistentapp.domain.model.Category;
import com.arwka.persistentapp.domain.model.Product;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

// Lombok
@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
// ---
@Entity
@Table(name = "product")
public class ProductEntity {

  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @NotEmpty(message = "Name should be not empty.")
  @Size(min = 1, max = 50, message = "Name should be between 1-50 chars.")
  @Column(name = "name")
  private String name;

  @Column(name = "description")
  private String description;

  @Column(name = "category_code")
  private long categoryCode;

  @Min(value = 0, message = "Quantity cannot less than 0")
  @Column(name = "quantity")
  private long quantity;

  // with zero quantity
  public ProductEntity(String name, String description, long categoryCode) {
    this.name = name;
    this.description = description;
    this.categoryCode = categoryCode;
    this.quantity = 0;
  }

  // with custom quantity
  public ProductEntity(String name, String description, long categoryCode, long quantity) {
    this.name = name;
    this.description = description;
    this.categoryCode = categoryCode;
    this.quantity = quantity;
  }

  /**
   * Get ProductEntity from domain Product.
   *
   * @param product - domain product
   * @return ProductEntity
   */
  public static ProductEntity of(Product product) {
    return new ProductEntity(product.getName(), product.getDescription(), product.getCategory().getCode());
  }

  /**
   * Get Product from this.
   *
   * @return Product
   */
  public Product toProduct() {
    return Product.of(
        name,
        Category.of("undefined", categoryCode),
        description
    );
  }

}
