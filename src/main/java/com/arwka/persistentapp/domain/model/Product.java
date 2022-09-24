package com.arwka.persistentapp.domain.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@EqualsAndHashCode
@ToString
@Getter
public final class Product {

  private final String description;
  private final String name;
  private final Category category;

  // -------

  public static Product of(String name, Category category) {
    return new Product(name, category);
  }

  public static Product of(String name, Category category, String description) {
    return new Product(name, category, description);
  }

  // -------

  private Product(String name, Category category) {
    this.name = name;
    this.category = category;
    this.description = "no description";
  }

  private Product(String name, Category category, String description) {
    this.name = name;
    this.category = category;
    this.description = description;
  }

}
