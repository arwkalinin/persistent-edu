package com.arwka.persistentapp.infrastructure.persistent.mongo.entity;

import com.arwka.persistentapp.domain.model.Category;
import com.arwka.persistentapp.domain.model.Product;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
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
public class ProductDocument {

  @NotEmpty(message = "Name should be not empty.")
  @Size(min = 1, max = 50, message = "Name should be between 1-50 chars.")
  private String name;

  private String description;

  private Category category;

  @Min(value = 0, message = "Quantity cannot less than 0")
  private long quantity;

  public ProductDocument(String name, String description, Category category) {
    this.name = name;
    this.description = description;
    this.category = category;
    this.quantity = 0;
  }

  public static ProductDocument of(Product product) {
    return new ProductDocument(
        product.getName(), product.getDescription(), product.getCategory());
  }

  public Product toProduct() {
    return Product.of(name, category, description);
  }

}
