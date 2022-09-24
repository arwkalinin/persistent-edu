package com.arwka.persistentapp.domain.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@EqualsAndHashCode
@ToString
@Getter
public final class Category {

  private final String name;
  private final long code;

  // -------

  public static Category of(String name, long code) {
    return new Category(name, code);
  }

  private Category(String name, long code) {
    this.name = name;
    this.code = code;
  }

}
